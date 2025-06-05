package edu.fdu.ivos.audit.service;

import edu.fdu.ivos.application.pojo.entity.Application;
import edu.fdu.ivos.audit.pojo.dto.AuditQuery;
import edu.fdu.ivos.audit.pojo.dto.AuditSaveParam;
import edu.fdu.ivos.audit.pojo.vo.AuditVO;

import java.util.List;

public interface AuditService {
    void insertAudit(Application application);

    List<AuditVO> selectAuditByApplicationId(Long id);

    List<AuditVO> selectAudit(AuditQuery auditQuery);

    void updateAudit(AuditSaveParam auditSaveParam);
}
