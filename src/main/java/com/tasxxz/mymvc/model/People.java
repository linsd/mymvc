package com.tasxxz.mymvc.model;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import java.util.Date;

/**
 * Created by linshudeng on 2016/10/30.
 */
public class People {

    @NotBlank(message = "name不能为空")
    private String name;

    @Min(value = 17, message = "age不能小于17")
    private int age;

    private Date createTime = new Date();

    public People() {
    }

    public People(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
