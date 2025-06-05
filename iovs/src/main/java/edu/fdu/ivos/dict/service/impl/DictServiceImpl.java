package edu.fdu.ivos.dict.service.impl;

import edu.fdu.ivos.dict.mapper.DictMapper;
import edu.fdu.ivos.dict.pojo.dto.DictQuery;
import edu.fdu.ivos.dict.pojo.dto.DictSaveParam;
import edu.fdu.ivos.dict.pojo.entity.Dict;
import edu.fdu.ivos.dict.pojo.vo.DictVO;
import edu.fdu.ivos.dict.service.DictService;
import edu.fdu.ivos.dictoption.mapper.DictOptionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class DictServiceImpl implements DictService {
    @Autowired
    DictMapper dictMapper;

    @Autowired
    DictOptionMapper dictOptionMapper;

    @Override
    public List<DictVO> selectDict(DictQuery dictQuery) {
        List<DictVO> list = dictMapper.selectDict(dictQuery);
        return list;
    }

    @Override
    public void saveDict(DictSaveParam dictSaveParam) {
        log.debug("Saving dictionary: dictSaveParam={}", dictSaveParam);
        Dict dict = new Dict();
        BeanUtils.copyProperties(dictSaveParam, dict);
        if (dict.getId() == null) { // Insert new dictionary
            dict.setCreateTime(new Date());
            dictMapper.insert(dict);
        } else { // Update existing dictionary
            dict.setUpdateTime(new Date());
            dictMapper.update(dict);
        }
    }

    @Override
    public void deleteDict(Long id) {
        log.debug("Deleting dictionary: id={}", id);
        dictMapper.deleteById(id);
        // When a dictionary is deleted, all its associated dictionary options must also be deleted
        dictOptionMapper.deleteByDictId(id);
    }
}
