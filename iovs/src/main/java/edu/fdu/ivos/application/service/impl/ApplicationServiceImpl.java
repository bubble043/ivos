package edu.fdu.ivos.application.service.impl;

import edu.fdu.ivos.application.mapper.ApplicationMapper;
import edu.fdu.ivos.application.pojo.dto.ApplicationQuery;
import edu.fdu.ivos.application.pojo.dto.ApplicationSaveParam;
import edu.fdu.ivos.application.pojo.entity.Application;
import edu.fdu.ivos.application.pojo.vo.ApplicationVO;
import edu.fdu.ivos.application.service.ApplicationService;
import edu.fdu.ivos.audit.mapper.AuditMapper;
import edu.fdu.ivos.audit.pojo.vo.AuditVO;
import edu.fdu.ivos.audit.service.AuditService;
import edu.fdu.ivos.base.enums.ApplicationStatusEnum;
import edu.fdu.ivos.user.mapper.UserMapper;
import edu.fdu.ivos.user.pojo.vo.UserVO;
import edu.fdu.ivos.vehicle.mapper.VehicleMapper;
import edu.fdu.ivos.vehicle.pojo.entity.Vehicle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;

import java.util.List;

@Transactional
@Slf4j
@Service
public class ApplicationServiceImpl implements ApplicationService {
    @Autowired
    ApplicationMapper applicationMapper;
    @Autowired
    AuditService auditService;
    @Autowired
    UserMapper userMapper;
    @Autowired
    AuditMapper auditMapper;
    @Autowired
    VehicleMapper vehicleMapper;

    @Override
    public void save(ApplicationSaveParam applicationSaveParam) {
        log.debug("Saving application form: applicationSaveParam={}", applicationSaveParam);
        Application application = new Application();
        BeanUtils.copyProperties(applicationSaveParam, application);
        // New application form, initial status is "PENDING"
        application.setStatus(ApplicationStatusEnum.PENDING.getCode());
        application.setCreateTime(new Date());

        /*
         * Issue: When creating audit records for the new application, the audit records didn't get the application ID.
         * Cause: ID is a special field that can't be obtained through normal SELECT conditions.
         * Solution: Add useGeneratedKeys="true" and keyProperty="id" to the insert SQL mapping.
         * Effect: JDBC will automatically generate the primary key and assign it to the `id` field of the application object.
         */
        applicationMapper.insert(application);

        // Also create corresponding audit records for the new application
        auditService.insertAudit(application);
    }

    @Override
    public List<ApplicationVO> selectApplication(ApplicationQuery applicationQuery) {
        log.debug("Querying application forms: applicationQuery={}", applicationQuery);
        List<ApplicationVO> list = applicationMapper.selectApplication(applicationQuery);
        // Assign approver info for each application record
        for (int i = 0; i < list.size(); i++) {
            ApplicationVO applicationVO = list.get(i);
            assignAuditUserList(applicationVO);
        }
        return list;
    }

    @Override
    public void cancel(Long id) {
        log.debug("Cancelling application: id={}", id);
        Application application = new Application();
        application.setId(id);
        application.setStatus(ApplicationStatusEnum.CANCEL.getCode());
        application.setUpdateTime(new Date());
        applicationMapper.update(application);

        // Also delete all audit records associated with this application
        auditMapper.deleteAuditByApplicationId(id);
    }

    @Override
    public void distribute(Long applicationId, Long vehicleId) {
        log.debug("Assigning vehicle: applicationId={}, vehicleId={}", applicationId, vehicleId);
        Application application = new Application();
        application.setId(applicationId);
        application.setVehicleId(vehicleId);
        application.setStatus(ApplicationStatusEnum.ALLOCATION.getCode());
        application.setUpdateTime(new Date());
        applicationMapper.update(application);

        // Update vehicle status to "occupied"
        Vehicle vehicle = new Vehicle();
        vehicle.setId(vehicleId);
        vehicle.setStatus("2"); // 2 = occupied
        vehicleMapper.update(vehicle);
    }

    @Override
    public void back(Long applicationId, Long vehicleId) {
        log.debug("Returning vehicle: applicationId={}, vehicleId={}", applicationId, vehicleId);
        Application application = new Application();
        application.setId(applicationId);

        /*
         * Option 1: Keep vehicleId in the application, just update the status to END and mark vehicle as available.
         * Option 2: Remove vehicleId from the application, update status to END and mark vehicle as available.
         */
        application.setVehicleId(null); // Option 2: set vehicleId to null
        application.setStatus(ApplicationStatusEnum.END.getCode());
        application.setUpdateTime(new Date());
        applicationMapper.back(application); // Use a custom SQL for nullifying vehicleId

        // Set vehicle status to "available"
        Vehicle vehicle = new Vehicle();
        vehicle.setId(vehicleId);
        vehicle.setStatus("1"); // 1 = available
        vehicleMapper.update(vehicle);
    }

    /**
     * Assign approver information (IDs and names) to the given ApplicationVO.
     *
     * @param applicationVO the application view object to enrich with approver data
     */
    private void assignAuditUserList(ApplicationVO applicationVO) {
        // 1. Prepare empty lists for approver names and IDs
        List<String> auditUsernameList = new ArrayList<>();
        List<Long> auditUserIdList = new ArrayList<>();
        // 2. Query audit records based on application ID
        List<AuditVO> auditVOList = auditService.selectAuditByApplicationId(applicationVO.getId());
        // 3. Traverse each audit record
        for (int i = 0; i < auditVOList.size(); i++) {
            AuditVO auditVO = auditVOList.get(i);
            Long id = auditVO.getAuditUserId();
            auditUserIdList.add(id);
            UserVO userVO = userMapper.selectById(id);
            auditUsernameList.add(userVO.getUsername());
        }
        // 4. Join approver names into a comma-separated string
        StringJoiner stringJoiner = new StringJoiner(",");
        for (String username : auditUsernameList) {
            stringJoiner.add(username);
        }
        // 5. Assign approver name string and ID list to ApplicationVO
        applicationVO.setAuditUsernameList(stringJoiner.toString());
        applicationVO.setAuditUserIdList(auditUserIdList);
    }
}
