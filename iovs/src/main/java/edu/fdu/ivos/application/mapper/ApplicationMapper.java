package edu.fdu.ivos.application.mapper;

import edu.fdu.ivos.application.pojo.dto.ApplicationQuery;
import edu.fdu.ivos.application.pojo.entity.Application;
import edu.fdu.ivos.application.pojo.vo.ApplicationVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationMapper {
    void insert(Application application);

    List<ApplicationVO> selectApplication(ApplicationQuery applicationQuery);

    void update(Application application);

    void back(Application application);
}
