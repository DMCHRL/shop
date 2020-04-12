package com.shop.item.api;

import com.shop.item.pojo.Category;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RequestMapping("category")
public interface CategoryApi {

    @GetMapping("id")
    Category getById(@RequestParam Long id);
}
