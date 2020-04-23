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
    //这个还是不行不能够清除对应的key dept_list的缓存
    @Caching(evict = {
            @CacheEvict(value = "dept", key = "'dept_list'"),
            @CacheEvict(value = "dept", key = "#dept.id")
    })
    @Override
    public void update(Dept dept) {
        this.deptDao.save(dept);
    }

    @Override
    public void save(Dept dept) {
        this.deptDao.save(dept);
    }

    /**
     * 返回值不同的时候使用同一个id作为缓存会有问题,所以这里不能 使用同一个缓存的标志
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    @Cacheable(key = "#id")
    public String findNameById(Long id) throws Exception {
        Optional<Dept> byId = this.deptDao.findById(id);
        Dept dept = byId.orElseThrow(() -> new Exception("该资源不存在"));
        return dept.getName();
    }


}
