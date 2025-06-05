package edu.fdu.ivos.vehicle.service;

import edu.fdu.ivos.vehicle.pojo.dto.VehicleQuery;
import edu.fdu.ivos.vehicle.pojo.dto.VehicleSaveParam;
import edu.fdu.ivos.vehicle.pojo.vo.VehicleVO;

import java.util.List;

public interface VehicleService {
    List<VehicleVO> selectVehicle(VehicleQuery vehicleQuery);

    void saveVehicle(VehicleSaveParam vehicleSaveParam);

    void deleteVehicle(Long id);

    void unbindVehicle(Long vehicleId);

    void bindVehicle(Long geofenceId, Long vehicleId);
}
