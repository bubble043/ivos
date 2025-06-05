package edu.fdu.ivos.vehicle.controller;

import edu.fdu.ivos.base.response.JsonResult;
import edu.fdu.ivos.vehicle.pojo.dto.VehicleQuery;
import edu.fdu.ivos.vehicle.pojo.dto.VehicleSaveParam;
import edu.fdu.ivos.vehicle.pojo.vo.VehicleVO;
import edu.fdu.ivos.vehicle.service.VehicleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/vehicle")
public class VehicleController {
    @Autowired
    VehicleService vehicleService;
    @GetMapping("select")
    public JsonResult selectVehicle(VehicleQuery vehicleQuery){
        log.debug("查询车辆信息,vehicleQuery={}",vehicleQuery);
        List<VehicleVO> list = vehicleService.selectVehicle(vehicleQuery);
        return JsonResult.ok(list);
    }

    @PostMapping("save")
    public JsonResult saveVehicle(VehicleSaveParam vehicleSaveParam){
        log.debug("保存车辆信息,vehicleSaveParam={}",vehicleSaveParam);
        vehicleService.saveVehicle(vehicleSaveParam);
        return JsonResult.ok();
    }


    @PostMapping("delete/{id}")
    public JsonResult deleteVehicle(@PathVariable Long id) {
        log.debug("删除车辆,id={}",id);
        vehicleService.deleteVehicle(id);
        return JsonResult.ok();
    }


    @PostMapping("unbind/{vehicleId}")
    public JsonResult unbindVehicle(@PathVariable Long vehicleId){
        log.debug("解绑车辆,vehicleId={}",vehicleId);
        vehicleService.unbindVehicle(vehicleId);
        return JsonResult.ok();
    }

    @PostMapping("/bind/{geofenceId}/{vehicleId}")
    public JsonResult bindVehicle(
            @PathVariable Long geofenceId,@PathVariable Long vehicleId){
       log.debug("绑定车辆,geofenceId={},vehicleId={}",geofenceId,vehicleId);
       vehicleService.bindVehicle(geofenceId,vehicleId);
       return JsonResult.ok();
    }
}
