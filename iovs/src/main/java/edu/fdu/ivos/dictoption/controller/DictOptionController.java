package edu.fdu.ivos.dictoption.controller;

import edu.fdu.ivos.base.response.JsonResult;
import edu.fdu.ivos.dictoption.pojo.dto.DictOptionQuery;
import edu.fdu.ivos.dictoption.pojo.dto.DictOptionSaveParam;
import edu.fdu.ivos.dictoption.pojo.vo.DictOptionVO;
import edu.fdu.ivos.dictoption.service.DictOptionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/dictoption")
public class DictOptionController {
    @Autowired
    DictOptionService dictOptionService;

    @GetMapping("select")
    public JsonResult selectDictOption(DictOptionQuery dictOptionQuery) {
        log.debug("Querying dictionary options: dictOptionQuery={}", dictOptionQuery);
        List<DictOptionVO> list =  dictOptionService.selectDictOption(dictOptionQuery);
        return JsonResult.ok(list);
    }

    @PostMapping("save")
    public JsonResult saveDictOption(DictOptionSaveParam dictOptionSaveParam) {
        log.debug("Saving dictionary option: dictOptionSaveParam={}", dictOptionSaveParam);
        dictOptionService.saveDictOption(dictOptionSaveParam);
        return JsonResult.ok();
    }

    @GetMapping("select/{code}")
    public JsonResult selectDictOptionByCode(@PathVariable String code){
        log.debug("Querying dictionary options by dictionary code: code={}", code);
        List<DictOptionVO> list = dictOptionService.selectDictOptionByCode(code);
        return JsonResult.ok(list);
    }

    @PostMapping("delete/{id}")
    public JsonResult deleteDictOption(@PathVariable Long id) {
        log.debug("Deleting dictionary option: id={}", id);
        dictOptionService.deleteDictOption(id);
        return JsonResult.ok();
    }
}
