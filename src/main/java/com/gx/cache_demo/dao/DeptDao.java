package com.gx.cache_demo.dao;

import com.gx.cache_demo.entity.Dept;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author zzl
 * @Date 2020/4/23 10:24
 */
public interface DeptDao extends JpaRepository<Dept, Long> {

}
