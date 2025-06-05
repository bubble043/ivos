package edu.fdu.ivos.geofence.controller;

import edu.fdu.ivos.base.response.JsonResult;
import edu.fdu.ivos.geofence.pojo.dto.GeofenceParam;
import edu.fdu.ivos.geofence.pojo.dto.GeofenceQuery;
import edu.fdu.ivos.geofence.pojo.vo.GeofenceVO;
import edu.fdu.ivos.geofence.service.GeofenceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/geofence")
public class GeofenceController {
    @Autowired
    GeofenceService geofenceService;

    @GetMapping("select")
    public JsonResult selectGeofence(GeofenceQuery geofenceQuery) {
        log.debug("Querying geofence data: geofenceQuery={}", geofenceQuery);
        List<GeofenceVO> list = geofenceService.selectGeofence(geofenceQuery);
        return JsonResult.ok(list);
    }

    @PostMapping("save")
    public JsonResult saveGeofence(GeofenceParam geofenceParam) {
        log.debug("Saving geofence data: geofenceParam={}", geofenceParam);
        geofenceService.saveGeofence(geofenceParam);
        return JsonResult.ok();
    }

    @PostMapping("/delete/{id}")
    public JsonResult deleteGeofence(@PathVariable Long id) {
        log.debug("Deleting geofence: id={}", id);
        geofenceService.deleteGeofence(id);
        return JsonResult.ok();
    }
}
