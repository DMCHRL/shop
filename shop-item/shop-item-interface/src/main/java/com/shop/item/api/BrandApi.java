package com.shop.item.api;

import com.shop.item.pojo.Brand;
import org.springframework.web.bind.annotation.*;



@RequestMapping("brand")
public interface BrandApi {

    @GetMapping("/{id}")
    Brand getById(@PathVariable(value = "id",required = false) Long id);
}
