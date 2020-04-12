package com.shop.item.api;

import com.shop.item.pojo.SpecParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@RequestMapping("spec")
public interface SpecificationApi {


    @GetMapping("queryParams")
    List<SpecParam> queryParams(@RequestParam(value = "gid",required = false)Long gid,
                                                      @RequestParam(value = "cid",required = false)Long cid,
                                                      @RequestParam(value = "generic",required = false)Boolean generic,
                                                      @RequestParam(value = "searching",required = false)Boolean searching);

}
