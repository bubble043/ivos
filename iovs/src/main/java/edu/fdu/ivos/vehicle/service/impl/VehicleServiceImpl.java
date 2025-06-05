package edu.fdu.ivos.vehicle.service.impl;

import edu.fdu.ivos.vehicle.mapper.VehicleMapper;
import edu.fdu.ivos.vehicle.pojo.dto.VehicleQuery;
import edu.fdu.ivos.vehicle.pojo.dto.VehicleSaveParam;
import edu.fdu.ivos.vehicle.pojo.entity.Vehicle;
import edu.fdu.ivos.vehicle.pojo.vo.VehicleVO;
import edu.fdu.ivos.vehicle.service.VehicleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class VehicleServiceImpl implements VehicleService {
    @Autowired
    VehicleMapper vehicleMapper;

    @Override
    public List<VehicleVO> selectVehicle(VehicleQuery vehicleQuery) {
        log.debug("Querying vehicle data: vehicleQuery={}", vehicleQuery);
        List<VehicleVO> list = vehicleMapper.selectVehicle(vehicleQuery);
        return list;
    }

    @Override
    public void saveVehicle(VehicleSaveParam vehicleSaveParam) {
        log.debug("Saving vehicle data: vehicleSaveParam={}", vehicleSaveParam);
        Vehicle vehicle = new Vehicle();
        BeanUtils.copyProperties(vehicleSaveParam, vehicle);

        // Determine whether to insert or update
        if (vehicle.getId() == null) {
            // New vehicle
            vehicle.setCreateTime(new Date());
            vehicle.setStatus("1"); // Set to "1" meaning "idle"
            vehicleMapper.insert(vehicle);
        } else {
            // Update existing vehicle
            vehicle.setUpdateTime(new Date());
            vehicleMapper.update(vehicle);
        }
    }

    @Override
    public void deleteVehicle(Long id) {
        log.debug("Deleting vehicle: id={}", id);
        vehicleMapper.deleteVehicle(id);
    }

    @Override
    public void unbindVehicle(Long vehicleId) {
        log.debug("Unbinding vehicle from geofence: vehicleId={}", vehicleId);
        Vehicle vehicle = new Vehicle();
        vehicle.setId(vehicleId);
        vehicle.setGeofenceBindStatus("0"); // "0" means unbound; note it's a String type
        vehicle.setUpdateTime(new Date());
        vehicle.setGeofenceId(null); // Clear the geofence ID

        /*
         * The previous update SQL only updated the geofence ID if it was non-null.
         * Since this operation requires removing the binding (setting it to null),
         * a dedicated method was implemented in the mapper layer to support this.
         */
        vehicleMapper.updateNullValue(vehicle);
    }

    @Override
    public void bindVehicle(Long geofenceId, Long vehicleId) {
        log.debug("Binding vehicle to geofence: geofenceId={}, vehicleId={}", geofenceId, vehicleId);
        Vehicle vehicle = new Vehicle();
        vehicle.setId(vehicleId);
        vehicle.setGeofenceBindStatus("1"); // "1" means bound
        vehicle.setGeofenceId(geofenceId);
        vehicle.setUpdateTime(new Date());
        vehicleMapper.update(vehicle);
    }
}
