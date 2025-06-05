package edu.fdu.ivos.geofence.service.impl;

import edu.fdu.ivos.base.exception.ServiceException;
import edu.fdu.ivos.base.response.StatusCode;
import edu.fdu.ivos.geofence.mapper.GeofenceMapper;
import edu.fdu.ivos.geofence.pojo.dto.GeofenceParam;
import edu.fdu.ivos.geofence.pojo.dto.GeofenceQuery;
import edu.fdu.ivos.geofence.pojo.entity.Geofence;
import edu.fdu.ivos.geofence.pojo.vo.GeofenceVO;
import edu.fdu.ivos.geofence.service.GeofenceService;
import edu.fdu.ivos.vehicle.mapper.VehicleMapper;
import edu.fdu.ivos.vehicle.pojo.dto.VehicleQuery;
import edu.fdu.ivos.vehicle.pojo.vo.VehicleVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class GeofenceServiceImpl implements GeofenceService {
    @Autowired
    GeofenceMapper geofenceMapper;
    @Autowired
    VehicleMapper vehicleMapper;

    @Override
    public List<GeofenceVO> selectGeofence(GeofenceQuery geofenceQuery) {
        log.debug("Querying geofence data: geofenceQuery={}", geofenceQuery);
        List<GeofenceVO> list = geofenceMapper.selectGeofence(geofenceQuery);

        /* For each geofenceVO, supplement with vehicle data: total bound vehicles, available vehicles */
        for (int i = 0; i < list.size(); i++) {
            // Get the current geofenceVO
            GeofenceVO geofenceVO = list.get(i);

            // Prepare a vehicle query to get vehicles bound to this geofence
            VehicleQuery vehicleQuery = new VehicleQuery();
            vehicleQuery.setGeofenceId(geofenceVO.getId());
            List<VehicleVO> vehicleVOList = vehicleMapper.selectVehicle(vehicleQuery);

            // Total number of bound vehicles
            int totalNum = vehicleVOList.size();

            // Count of available vehicles
            int availableNum = 0;

            // Loop through vehicles and count those with status "1" (idle)
            for (VehicleVO vehicleVO : vehicleVOList) {
                if (vehicleVO.getStatus().equals("1")) {
                    availableNum++;
                }
            }

            // Assign counts to geofenceVO
            geofenceVO.setAvailableNum(availableNum);
            geofenceVO.setTotalNum(totalNum);

            // Also assign the full vehicle list for detail viewing
            geofenceVO.setVehicleList(vehicleVOList);
        }

        return list;
    }

    @Override
    public void saveGeofence(GeofenceParam geofenceParam) {
        log.debug("Saving geofence data: geofenceParam={}", geofenceParam);
        Geofence geofence = new Geofence();
        BeanUtils.copyProperties(geofenceParam, geofence);

        if (geofence.getId() != null) { // Update
            geofence.setUpdateTime(new Date());
            geofenceMapper.update(geofence);
        } else { // Insert new
            geofence.setStatus("1"); // New geofences are enabled by default
            geofence.setCreateTime(new Date());
            geofenceMapper.insert(geofence);
        }
    }

    @Override
    public void deleteGeofence(Long id) {
        log.debug("Deleting geofence: id={}", id);
        // Business rule: If any vehicle is bound to this geofence, it cannot be deleted.

        // 1. Prepare a vehicle query to find all vehicles bound to this geofence
        VehicleQuery vehicleQuery = new VehicleQuery();
        vehicleQuery.setGeofenceId(id);
        List<VehicleVO> list = vehicleMapper.selectVehicle(vehicleQuery);

        // 2. Check if any vehicles are bound
        if (list.size() > 0 && list != null) {
            throw new ServiceException(StatusCode.OPERATION_FAILED);
        } else {
            // 3. Only delete if no vehicles are bound
            geofenceMapper.deleteById(id);
        }
    }
}
