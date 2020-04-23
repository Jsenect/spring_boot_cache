package com.gx.cache_demo.service;

import com.gx.cache_demo.entity.Dept;

import java.util.List;
import java.util.Optional;

/**
 * @author zzl
 * @Date 2020/4/23 10:22
 */
public interface DeptService {

    Optional<Dept> findById(Long id);

    List<Dept> list();

    void update(Dept dept);

    void save(Dept dept);
}
