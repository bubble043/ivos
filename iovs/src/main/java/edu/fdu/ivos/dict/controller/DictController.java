package edu.fdu.ivos.dict.controller;

import edu.fdu.ivos.base.response.JsonResult;
import edu.fdu.ivos.dict.pojo.dto.DictQuery;
import edu.fdu.ivos.dict.pojo.dto.DictSaveParam;
import edu.fdu.ivos.dict.pojo.vo.DictVO;
import edu.fdu.ivos.dict.service.DictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/dict")
public class DictController {
    @Autowired
    DictService dictService;

    @GetMapping("select")
    public JsonResult selectDict(DictQuery dictQuery) {
        log.debug("Querying dictionary list: dictQuery={}", dictQuery);
        List<DictVO> list = dictService.selectDict(dictQuery);
        return JsonResult.ok(list);
    }

    @PostMapping("save")
    public JsonResult saveDict(DictSaveParam dictSaveParam) {
        log.debug("Saving dictionary: dictSaveParam={}", dictSaveParam);
        dictService.saveDict(dictSaveParam);
        return JsonResult.ok();
    }

    @PostMapping("delete/{id}")
    public JsonResult deleteDict(@PathVariable Long id) {
        log.debug("Deleting dictionary: id={}", id);
        dictService.deleteDict(id);
        return JsonResult.ok();
    }
}
