package com.gx.cache_demo.controller;

import com.gx.cache_demo.entity.Dept;
import com.gx.cache_demo.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * @author zzl
 * @Date 2020/4/23 10:20
 */
@RestController
public class DeptController {
    @Autowired
    private DeptService userService;

    @RequestMapping("/{id}")
    public Optional<Dept> findById(@PathVariable("id") Long id) {
        return userService.findById(id);
    }

    @RequestMapping("/list")
    public List<Dept> list() {
        return userService.list();
    }

    @RequestMapping("/save")
    public void insert(Dept dept) {
        this.userService.save(dept);
    }

    @RequestMapping("/update")
    public void update(Dept dept) {
        this.userService.update(dept);
    }

    @RequestMapping("/getNameById/{id}")
    public String findNameById( @PathVariable("id") Long id) {
        try {
            return userService.findNameById(id);
        } catch (Exception e) {
            return e.getMessage();

        }
    }

}
