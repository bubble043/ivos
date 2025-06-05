package edu.fdu.ivos.vehicle.mapper;

import edu.fdu.ivos.vehicle.pojo.dto.VehicleQuery;
import edu.fdu.ivos.vehicle.pojo.entity.Vehicle;
import edu.fdu.ivos.vehicle.pojo.vo.VehicleVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleMapper {
    List<VehicleVO> selectVehicle(VehicleQuery vehicleQuery);

    void insert(Vehicle vehicle);

    void update(Vehicle vehicle);

    void deleteVehicle(Long id);

    void updateNullValue(Vehicle vehicle);
}
