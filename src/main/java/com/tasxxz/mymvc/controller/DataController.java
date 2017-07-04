package com.tasxxz.mymvc.controller;

import com.tasxxz.mymvc.util.DataUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by linshudeng on 2016/11/11.
 */
@Controller
@RequestMapping("/data")
public class DataController {

    @RequestMapping("/upload")
    public String upload(@RequestParam(value = "file", required = false) MultipartFile file,
                         @RequestParam(value = "remark", required = false, defaultValue = "no remark") String remark,
                         Map model) {
        String path = null;
        try {
            if (file != null && !file.isEmpty()) {
                path = DataUtil.upload(file.getBytes(), DataUtil.getFileName(file.getOriginalFilename()), "upload");
                System.out.println(path);
            } else {
                System.out.println("no file");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("===>>remark:" + remark);
        try {
            System.out.println("===>>manual encode remark:" + new String(remark.getBytes("utf-8"), "GBK"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        model.put("message", remark + "[" + path + "]");
        return "message";
    }
}
