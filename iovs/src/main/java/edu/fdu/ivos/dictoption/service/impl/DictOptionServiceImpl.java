package edu.fdu.ivos.dictoption.service.impl;

import edu.fdu.ivos.base.exception.ServiceException;
import edu.fdu.ivos.base.response.StatusCode;
import edu.fdu.ivos.dict.mapper.DictMapper;
import edu.fdu.ivos.dict.pojo.dto.DictQuery;
import edu.fdu.ivos.dict.pojo.vo.DictVO;
import edu.fdu.ivos.dictoption.mapper.DictOptionMapper;
import edu.fdu.ivos.dictoption.pojo.dto.DictOptionQuery;
import edu.fdu.ivos.dictoption.pojo.dto.DictOptionSaveParam;
import edu.fdu.ivos.dictoption.pojo.entity.DictOption;
import edu.fdu.ivos.dictoption.pojo.vo.DictOptionVO;
import edu.fdu.ivos.dictoption.service.DictOptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class DictOptionServiceImpl implements DictOptionService {
    @Autowired
    DictOptionMapper dictOptionMapper;

    @Autowired
    DictMapper dictMapper;

    @Override
    public List<DictOptionVO> selectDictOption(DictOptionQuery dictOptionQuery) {
        log.debug("Querying dictionary options: dictOptionQuery={}", dictOptionQuery);
        List<DictOptionVO> list = dictOptionMapper.selectDictOption(dictOptionQuery);
        return list;
    }

    @Override
    public void saveDictOption(DictOptionSaveParam dictOptionSaveParam) {
        log.debug("Saving dictionary option: dictOptionSaveParam={}", dictOptionSaveParam);
        DictOption dictOption = new DictOption();
        BeanUtils.copyProperties(dictOptionSaveParam, dictOption);
        if (dictOption.getId() == null) {
            dictOption.setCreateTime(new Date());
            dictOptionMapper.insert(dictOption);
        } else {
            dictOption.setUpdateTime(new Date());
            dictOptionMapper.update(dictOption);
        }
    }

    @Override
    public List<DictOptionVO> selectDictOptionByCode(String code) {
        log.debug("Querying dictionary options by code: code={}", code);
        // Step 1: Check if the dictionary with the given code exists
        DictQuery dictQuery = new DictQuery(); // Construct dictionary query object
        dictQuery.setCode(code); // Set code
        List<DictVO> dictVOList = dictMapper.selectDictByCode(dictQuery); // Query dictionary

        if (dictVOList != null && dictVOList.size() > 0) {
            // If dictionary exists
            DictVO dictVO = dictVOList.get(0); // Get the first matched dictionary
            DictOptionQuery dictOptionQuery = new DictOptionQuery(); // Create dictionary option query
            dictOptionQuery.setDictId(dictVO.getId()); // Set dictId

            // Return all dictionary options for the given dictionary ID
            List<DictOptionVO> dictOptionVOList = dictOptionMapper.selectDictOption(dictOptionQuery);
            return dictOptionVOList;
        } else {
            // If no dictionary is found for the code, throw exception
            throw new ServiceException(StatusCode.DATA_UNEXISTS);
        }
    }

    @Override
    public void deleteDictOption(Long id) {
        log.debug("Deleting dictionary option: id={}", id);
        dictOptionMapper.deleteById(id);
    }
}
