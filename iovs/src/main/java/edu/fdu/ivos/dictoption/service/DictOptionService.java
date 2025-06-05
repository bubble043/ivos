package edu.fdu.ivos.dictoption.service;

import edu.fdu.ivos.dictoption.pojo.dto.DictOptionQuery;
import edu.fdu.ivos.dictoption.pojo.dto.DictOptionSaveParam;
import edu.fdu.ivos.dictoption.pojo.vo.DictOptionVO;

import java.util.List;

public interface DictOptionService {
    List<DictOptionVO> selectDictOption(DictOptionQuery dictOptionQuery);

    void saveDictOption(DictOptionSaveParam dictOptionSaveParam);

    List<DictOptionVO> selectDictOptionByCode(String code);

    void deleteDictOption(Long id);
}
