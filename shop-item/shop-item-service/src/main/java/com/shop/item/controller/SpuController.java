package com.shop.item.controller;

import com.shop.common.pojo.PageResult;
import com.shop.item.pojo.Brand;
import com.shop.item.pojo.SpecGroup;
import com.shop.item.pojo.SpecParam;
import com.shop.item.pojo.Spu;
import com.shop.item.service.SpecificationService;
import com.shop.item.service.SpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("spu")
public class SpuController {

    @Autowired
    private SpuService spuService;

    @GetMapping("page")
    public ResponseEntity<PageResult<Spu>> page(@RequestParam(value = "key",required = false) String key,
                                                @RequestParam(value = "saleable",required = false) Boolean saleable,
                                                @RequestParam(value = "page",defaultValue = "1") Integer page,
                                                @RequestParam(value = "rows",defaultValue = "5") Integer rows){
        return ResponseEntity.ok(spuService.pageList(key,saleable,page,rows));
    }

}