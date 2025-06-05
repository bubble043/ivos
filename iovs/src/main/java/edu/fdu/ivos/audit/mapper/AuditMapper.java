package edu.fdu.ivos.audit.mapper;

import edu.fdu.ivos.audit.pojo.dto.AuditQuery;
import edu.fdu.ivos.audit.pojo.entity.Audit;
import edu.fdu.ivos.audit.pojo.vo.AuditVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditMapper {
    void insert(Audit audit);

    List<AuditVO> selectAuditByApplicationId(Long id);

    void deleteAuditByApplicationId(Long id);

    List<AuditVO> selectAudit(AuditQuery auditQuery);

    void update(Audit audit);

    Integer selectRestAuditCount(AuditQuery auditQuery);

    void deleteById(Long id);
}
