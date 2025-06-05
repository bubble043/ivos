package edu.fdu.ivos.application.service;

import edu.fdu.ivos.application.pojo.dto.ApplicationQuery;
import edu.fdu.ivos.application.pojo.dto.ApplicationSaveParam;
import edu.fdu.ivos.application.pojo.vo.ApplicationVO;

import java.util.List;

public interface ApplicationService {
    void save(ApplicationSaveParam applicationSaveParam);
    List<ApplicationVO> selectApplication(ApplicationQuery applicationQuery);

    void cancel(Long id);

    void distribute(Long applicationId, Long vehicleId);

    void back(Long applicationId, Long vehicleId);
}
