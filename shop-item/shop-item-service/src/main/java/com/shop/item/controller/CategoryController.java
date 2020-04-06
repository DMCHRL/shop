package com.shop.item.controller;

import com.shop.item.pojo.Category;
import com.shop.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category")
//@CrossOrigin //支持跨域
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("list")
    public ResponseEntity<List<Category>> listByPid(@RequestParam Long pid){
        return ResponseEntity.ok(categoryService.getByPid(pid));
    }
}
