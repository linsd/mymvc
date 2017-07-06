package com.tasxxz.mymvc.controller;

import com.tasxxz.mymvc.model.People;
import com.tasxxz.mymvc.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by linshudeng on 2016/10/29.
 */
@Controller
@RequestMapping("/my")
public class MyController {

    @Autowired
    ConfigService configService;

    @RequestMapping("/hello")
    @ResponseBody
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        System.out.println("hostName:" + configService.getHostName());
        System.out.println("hostPort:" + configService.getHostPort());
        System.out.println("hostPort2:" + configService.getHostPort2());
        System.out.println("name:" + name);
        return "Hello " + name;
    }

    @RequestMapping("/people")
    @ResponseBody
    public People people(@RequestParam(value = "name", defaultValue = "World") String name,
                       @RequestParam(value = "age", defaultValue = "18") int age) {
        return new People(name, age);
    }

    @RequestMapping("/valid")
    @ResponseBody
    public Map valid(@Valid People people, BindingResult result) {
        if(result.hasErrors()) {
            //如果没有通过,跳转提示
            Map<String, String> map = getErrors(result);
            return map;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("name", people.getName());
        map.put("age", people.getAge());
        map.put("createTime", people.getCreateTime());
        return map;
    }

    @RequestMapping("/hl")
    public String hl() {
        return "hello2";
    }

    private Map<String, String> getErrors(BindingResult result) {
        Map<String, String> map = new HashMap<String, String>();
        List<FieldError> list = result.getFieldErrors();
        for (FieldError error : list) {
            System.out.println("error.getField():" + error.getField());
            System.out.println("error.getDefaultMessage():" + error.getDefaultMessage());

            map.put(error.getField(), error.getDefaultMessage());
        }
        return map;
    }
}
