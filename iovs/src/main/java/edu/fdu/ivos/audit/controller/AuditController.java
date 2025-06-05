package edu.fdu.ivos.audit.controller;

import edu.fdu.ivos.audit.pojo.dto.AuditQuery;
import edu.fdu.ivos.audit.pojo.dto.AuditSaveParam;
import edu.fdu.ivos.audit.pojo.vo.AuditVO;
import edu.fdu.ivos.audit.service.AuditService;
import edu.fdu.ivos.base.response.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller for handling audit operations such as querying and updating audit records.
 */
@Slf4j
@RestController
@RequestMapping("/v1/audit")
public class AuditController {

    @Autowired
    AuditService auditService;

    /**
     * Endpoint for querying audit records.
     *
     * @param auditQuery the query parameters for filtering audit records
     * @return JsonResult containing a list of matched AuditVO records
     */
    @GetMapping("select")
    public JsonResult selectAudit(AuditQuery auditQuery){
        log.debug("Querying audit records: auditQuery={}", auditQuery);
        List<AuditVO> list = auditService.selectAudit(auditQuery);
        return JsonResult.ok(list);
    }

    /**
     * Endpoint for updating an audit record, typically to approve or reject a request.
     *
     * @param auditSaveParam contains the updated audit status and other necessary fields
     * @return JsonResult indicating operation success
     */
    @PostMapping("update")
    public JsonResult updateAudit(AuditSaveParam auditSaveParam){
        log.debug("Updating audit record (approve/reject): auditSaveParam={}", auditSaveParam);
        auditService.updateAudit(auditSaveParam);
        return JsonResult.ok();
    }
}
