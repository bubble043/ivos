package edu.fdu.ivos.dict.service;

import edu.fdu.ivos.dict.pojo.dto.DictQuery;
import edu.fdu.ivos.dict.pojo.dto.DictSaveParam;
import edu.fdu.ivos.dict.pojo.vo.DictVO;

import java.util.List;

public interface DictService {
    List<DictVO> selectDict(DictQuery dictQuery);

    void saveDict(DictSaveParam dictSaveParam);

    void deleteDict(Long id);
}
