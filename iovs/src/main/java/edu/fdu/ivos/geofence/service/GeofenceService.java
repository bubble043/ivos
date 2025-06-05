package edu.fdu.ivos.geofence.service;

import edu.fdu.ivos.geofence.pojo.dto.GeofenceParam;
import edu.fdu.ivos.geofence.pojo.dto.GeofenceQuery;
import edu.fdu.ivos.geofence.pojo.vo.GeofenceVO;

import java.util.List;

public interface GeofenceService {
    List<GeofenceVO> selectGeofence(GeofenceQuery geofenceQuery);

    void saveGeofence(GeofenceParam geofenceParam);

    void deleteGeofence(Long id);
}
