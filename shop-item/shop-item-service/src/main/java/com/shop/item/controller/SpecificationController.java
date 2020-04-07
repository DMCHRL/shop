package com.shop.item.controller;

import com.shop.item.pojo.SpecGroup;
import com.shop.item.pojo.SpecParam;
import com.shop.item.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("spec")
public class SpecificationController {

    @Autowired
    private SpecificationService specificationService;

    @GetMapping("groups/{cid}")
    public ResponseEntity<List<SpecGroup>> groupsByCid(@PathVariable("cid")Long cid){
       return ResponseEntity.ok(specificationService.groupsByCid(cid));
    }

    @PostMapping("group")
    public ResponseEntity<Void> saveGroup(@RequestBody()SpecGroup group){
        specificationService.saveGroup(group);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("group")
    public ResponseEntity<Void> updateGroup(@RequestBody()SpecGroup group){
        specificationService.saveGroup(group);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("group/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id")Long id){
        specificationService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("params")
    public ResponseEntity<List<SpecParam>> paramByGid(@RequestParam(value = "gid",required = false)Long gid,
                                                      @RequestParam(value = "cid",required = false)Long cid){
        return ResponseEntity.ok(specificationService.paramByGidOrCid(gid,cid));
    }


    @PostMapping("param")
    public ResponseEntity<Void> saveParam(@RequestBody SpecParam param){
        specificationService.saveParam(param);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("param")
    public ResponseEntity<Void> updateParam(@RequestBody SpecParam param){
        specificationService.saveParam(param);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("param/{id}")
    public ResponseEntity<Void> deleteParamById(@PathVariable("id")Long id){
        specificationService.deleteParamById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
