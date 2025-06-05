package edu.fdu.ivos.dict.mapper;

import edu.fdu.ivos.dict.pojo.dto.DictQuery;
import edu.fdu.ivos.dict.pojo.entity.Dict;
import edu.fdu.ivos.dict.pojo.vo.DictVO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DictMapper {
    List<DictVO> selectDict(DictQuery dictQuery);
    List<DictVO> selectDictByCode(DictQuery dictQuery);

    void insert(Dict dict);

    void update(Dict dict);

    void deleteById(Long id);
}
