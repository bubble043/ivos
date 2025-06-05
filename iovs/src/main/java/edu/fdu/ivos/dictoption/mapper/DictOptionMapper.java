package edu.fdu.ivos.dictoption.mapper;

import edu.fdu.ivos.dictoption.pojo.dto.DictOptionQuery;
import edu.fdu.ivos.dictoption.pojo.entity.DictOption;
import edu.fdu.ivos.dictoption.pojo.vo.DictOptionVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DictOptionMapper {
    List<DictOptionVO> selectDictOption(DictOptionQuery dictOptionQuery);

    void insert(DictOption dictOption);

    void update(DictOption dictOption);

    void deleteById(Long id);

    void deleteByDictId(Long id);
}
