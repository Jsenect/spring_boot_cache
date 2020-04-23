package com.gx.cache_demo.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author zzl
 * @Date 2020/4/23 10:21
 */
@Entity
@Table(name = "sys_dept")
@Data
public class Dept implements Serializable {
    @Id
    @GeneratedValue
    private Long id;


    private String name;
}
