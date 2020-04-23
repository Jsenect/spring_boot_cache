package com.gx.cache_demo.service.impl;

import com.gx.cache_demo.dao.DeptDao;
import com.gx.cache_demo.entity.Dept;
import com.gx.cache_demo.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author zzl
 * @Date 2020/4/23 10:23
 */
@Service
@CacheConfig(cacheNames = "dept")
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptDao deptDao;


    /**
     * 开启缓存,并且根据id 来判断是否走缓存
     *
     * @param id
     * @return
     */
    @Cacheable(key = "#id")
    @Override
    public Optional<Dept> findById(Long id) {

        System.out.println("进来了" + id);
        return this.deptDao.findById(id);
    }

    @Cacheable(key = "'dpet_list'")
    @Override
    public List<Dept> list() {
        return this.deptDao.findAll();
    }

    /**
     * 清除缓存,条件是属性的id来清除,指定缓存的名字,对应的key为缓存的key 就是清除缓存的条件
     *
     * @param dept
     */
//    @CacheEvict(cacheNames = "dept", key = "#dept.id")
    @Caching(
            evict = {
                    @CacheEvict(cacheNames = "dept", key = "#dept.id"),
                    @CacheEvict(cacheNames = "dept", key = "#dept.id"),

            }

    )
    @Override
    public void update(Dept dept) {
        this.deptDao.save(dept);
    }

    @Override
    public void save(Dept dept) {
        this.deptDao.save(dept);
    }


}
