package edu.fdu.ivos.application.controller;

import edu.fdu.ivos.application.pojo.dto.ApplicationQuery;
import edu.fdu.ivos.application.pojo.dto.ApplicationSaveParam;
import edu.fdu.ivos.application.pojo.vo.ApplicationVO;
import edu.fdu.ivos.application.service.ApplicationService;
import edu.fdu.ivos.base.response.JsonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/application")
public class ApplicationController {

    @Autowired
    ApplicationService applicationService;

    // Save a new vehicle application
    @PostMapping("save")
    public JsonResult saveApplication(ApplicationSaveParam applicationSaveParam) {
        log.debug("Saving application: applicationSaveParam={}", applicationSaveParam);
        applicationService.save(applicationSaveParam);
        return JsonResult.ok();
    }

    // Retrieve vehicle applications based on query parameters
    @GetMapping("select")
    public JsonResult select(ApplicationQuery applicationQuery) {
        log.debug("Querying applications: applicationQuery={}", applicationQuery);
        List<ApplicationVO> list = applicationService.selectApplication(applicationQuery);
        return JsonResult.ok(list);
    }

    // Cancel a submitted vehicle application
    @PostMapping("cancel/{id}")
    public JsonResult cancel(@PathVariable Long id) {
        log.debug("Cancelling application: id={}", id);
        applicationService.cancel(id);
        return JsonResult.ok();
    }

    // Assign a vehicle to an approved application
    @PostMapping("distribute/{applicationId}/{vehicleId}")
    public JsonResult distribute(
            @PathVariable Long applicationId, @PathVariable Long vehicleId) {
        log.debug("Distributing vehicle: applicationId={}, vehicleId={}", applicationId, vehicleId);
        applicationService.distribute(applicationId, vehicleId);
        return JsonResult.ok();
    }

    // Return a vehicle and mark the application as completed
    @PostMapping("back/{applicationId}/{vehicleId}")
    public JsonResult back(
            @PathVariable Long applicationId, @PathVariable Long vehicleId) {
        log.debug("Returning vehicle: applicationId={}, vehicleId={}", applicationId, vehicleId);
        applicationService.back(applicationId, vehicleId);
        return JsonResult.ok();
    }
}
