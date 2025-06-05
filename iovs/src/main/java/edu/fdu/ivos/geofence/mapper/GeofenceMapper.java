package edu.fdu.ivos.geofence.mapper;

import edu.fdu.ivos.geofence.pojo.dto.GeofenceQuery;
import edu.fdu.ivos.geofence.pojo.entity.Geofence;
import edu.fdu.ivos.geofence.pojo.vo.GeofenceVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GeofenceMapper {
    List<GeofenceVO> selectGeofence(GeofenceQuery geofenceQuery);

    void update(Geofence geofence);

    void insert(Geofence geofence);

    void deleteById(Long id);
}
