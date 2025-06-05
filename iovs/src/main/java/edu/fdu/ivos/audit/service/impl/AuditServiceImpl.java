package edu.fdu.ivos.audit.service.impl;

import edu.fdu.ivos.application.mapper.ApplicationMapper;
import edu.fdu.ivos.application.pojo.entity.Application;
import edu.fdu.ivos.audit.mapper.AuditMapper;
import edu.fdu.ivos.audit.pojo.dto.AuditQuery;
import edu.fdu.ivos.audit.pojo.dto.AuditSaveParam;
import edu.fdu.ivos.audit.pojo.entity.Audit;
import edu.fdu.ivos.audit.pojo.vo.AuditVO;
import edu.fdu.ivos.audit.service.AuditService;
import edu.fdu.ivos.base.enums.ApplicationStatusEnum;
import edu.fdu.ivos.base.enums.AuditStatusEnum;
import edu.fdu.ivos.user.mapper.UserMapper;
import edu.fdu.ivos.user.pojo.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;

@Slf4j
@Transactional
@Service
public class AuditServiceImpl implements AuditService {
    @Autowired
    AuditMapper auditMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    ApplicationMapper applicationMapper;

    @Override
    public void insertAudit(Application application) {
        log.debug("Generate approval records for the application: application={}", application);
        // 1. Get the list of approver IDs [106,103]
        List<Long> userIdList = application.getAuditUserIdList();
        // 2. Iterate over the list and create one approval record per approver
        for (int i = 0; i < userIdList.size(); i++) {
            // 3. Set approval record details
            Audit audit = new Audit();
            audit.setApplicationId(application.getId()); // Application ID
            audit.setAuditUserId(userIdList.get(i));     // Approver ID
            audit.setAuditSort(i);                       // Order: direct supervisor = 0, upper supervisor = 1
            // 4. Set audit status
            if (i == 0) {
                audit.setAuditStatus(AuditStatusEnum.MY_PENDING.getCode()); // Pending my review
            } else {
                audit.setAuditStatus(AuditStatusEnum.PENDING.getCode()); // Pending others' review
            }
            // 5. Set creation time
            audit.setCreateTime(new Date());
            // 6. Insert audit record into DB
            auditMapper.insert(audit);
        }
    }

    @Override
    public List<AuditVO> selectAuditByApplicationId(Long id) {
        return auditMapper.selectAuditByApplicationId(id);
    }

    @Override
    public List<AuditVO> selectAudit(AuditQuery auditQuery) {
        log.debug("Querying audit records: auditQuery={}", auditQuery);
        List<AuditVO> auditVOList = auditMapper.selectAudit(auditQuery);
        for (int i = 0; i < auditVOList.size(); i++) {
            AuditVO auditVO = auditVOList.get(i);
            assignAuditUserList(auditVO);
        }
        return auditVOList;
    }

    @Override
    public void updateAudit(AuditSaveParam auditSaveParam) {
        log.debug("Updating audit record (approved/rejected): auditSaveParam={}", auditSaveParam);
        Audit audit = new Audit();
        BeanUtils.copyProperties(auditSaveParam, audit);
        audit.setUpdateTime(new Date());

        Application application = new Application();
        application.setId(audit.getApplicationId());
        application.setUpdateTime(new Date());

        if (audit.getAuditStatus().equals(AuditStatusEnum.AUDITED.getCode())) {
            // If approved
            auditMapper.update(audit);

            AuditQuery auditQuery = new AuditQuery();
            auditQuery.setApplicationId(audit.getApplicationId());

            Integer count = auditMapper.selectRestAuditCount(auditQuery);
            if (count > 0) {
                auditQuery.setAuditSort(audit.getAuditSort() + 1);
                List<AuditVO> auditVOList = auditMapper.selectAudit(auditQuery);
                if (auditVOList != null && auditVOList.size() > 0) {
                    AuditVO auditVO = auditVOList.get(0);
                    Audit audit2 = new Audit();
                    audit2.setId(auditVO.getId());
                    audit2.setAuditStatus(AuditStatusEnum.MY_PENDING.getCode());
                    auditMapper.update(audit2);
                }
                application.setStatus(ApplicationStatusEnum.AUDIT.getCode());
                applicationMapper.update(application);
            } else {
                application.setStatus(ApplicationStatusEnum.AUDITED.getCode());
                applicationMapper.update(application);
            }

        } else if (audit.getAuditStatus().equals(AuditStatusEnum.REJECT.getCode())) {
            // If rejected
            auditMapper.update(audit);

            AuditQuery auditQuery = new AuditQuery();
            auditQuery.setApplicationId(audit.getApplicationId());
            List<AuditVO> auditVOList = auditMapper.selectAudit(auditQuery);
            if (auditVOList != null && auditVOList.size() > 0) {
                for (int i = 0; i < auditVOList.size(); i++) {
                    AuditVO auditVO = auditVOList.get(i);
                    if (AuditStatusEnum.PENDING.getCode().equals(auditVO.getAuditStatus())) {
                        auditMapper.deleteById(auditVO.getId());
                    }
                }
            }
            application.setStatus(ApplicationStatusEnum.REJECT.getCode());
            application.setRejectReason(auditSaveParam.getRejectReason());
            applicationMapper.update(application);
        }
    }

    private void assignAuditUserList(AuditVO auditVO) {
        List<String> auditUsernameList = new ArrayList<>();
        List<Long> auditUserIdList = new ArrayList<>();
        List<AuditVO> auditVOList = auditMapper.selectAuditByApplicationId(auditVO.getApplicationId());

        for (int i = 0; i < auditVOList.size(); i++) {
            Long userId = auditVOList.get(i).getAuditUserId();
            auditUserIdList.add(userId);
            UserVO user = userMapper.selectById(userId);
            auditUsernameList.add(user.getUsername());
        }

        StringJoiner stringJoiner = new StringJoiner(",");
        for (String username : auditUsernameList) {
            stringJoiner.add(username);
        }

        auditVO.setAuditUserIdList(auditUserIdList);
        auditVO.setAuditUsernameList(stringJoiner.toString());
    }
}
