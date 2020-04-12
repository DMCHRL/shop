package com.shop.search.controller;

import com.shop.common.pojo.PageResult;
import com.shop.search.dto.GoodsSearchDTO;
import com.shop.search.pojo.GoodsSearch;
import com.shop.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SearchController {

    @Autowired
    private SearchService searchService;

    @PostMapping("page")
    public ResponseEntity<PageResult<GoodsSearch>> search(@RequestBody GoodsSearchDTO request){
        return ResponseEntity.ok(searchService.search(request));
    }
}
