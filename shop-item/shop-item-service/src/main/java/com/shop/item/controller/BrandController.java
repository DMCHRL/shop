package com.shop.item.controller;

import com.shop.common.pojo.PageResult;
import com.shop.item.pojo.Brand;
import com.shop.item.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping("page")
    public ResponseEntity<PageResult<Brand>> page(@RequestParam(value = "key",required = false) String key,
                                                  @RequestParam(value = "page",defaultValue = "1") Integer page,
                                                  @RequestParam(value = "rows",defaultValue = "5") Integer rows,
                                                  @RequestParam(value = "sortBy",defaultValue = "id") String sortBy,
                                                  @RequestParam(value = "desc",defaultValue = "true") boolean desc){
        return ResponseEntity.ok(brandService.pageList(key,page,rows,sortBy,desc));
    }

    @PostMapping
    public ResponseEntity<Void> saveBrand(Brand brand,@RequestParam(value = "cids") List<Long> cid){
        brandService.saveBrand(brand,cid);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("cid/{cid}")
    public ResponseEntity<List<Brand>> page(@PathVariable(value = "cid",required = false) Long cid){
        return ResponseEntity.ok(brandService.findByCid(cid));
    }
}
