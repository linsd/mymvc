package com.tasxxz.mymvc.controller;

import com.tasxxz.mymvc.util.DataUtil;
import com.tasxxz.mymvc.ws.MSGWebsocket;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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

    @RequestMapping("/send/ws/msg")
    @ResponseBody
    public String sendWebSocketMsg(@RequestParam("sn") String sn,
                                   @RequestParam(value = "msg", defaultValue = "hello") String msg,
                                   @RequestParam(value = "times", defaultValue = "3") Integer times) {
        System.out.println("sn:" + sn + ",msg:" + msg + ",times:" + times);
        for (MSGWebsocket msgWebsocket : MSGWebsocket.webSocketSet) {
            if (msgWebsocket.getSn().equals(sn)) {
                for (int i = 0; i < times; i++) {
                    WebsocketMsgTask task = new WebsocketMsgTask(msgWebsocket, "m_" + (i+1) + ":" + msg);
                    new Thread(task).start();
                }
            }
        }

        return "ok";
    }
}

class WebsocketMsgTask implements Runnable {

    private MSGWebsocket msgWebsocket;
    private String msg;

    public WebsocketMsgTask(MSGWebsocket msgWebsocket, String msg) {
        this.msgWebsocket = msgWebsocket;
        this.msg = msg;
    }

    @Override
    public void run() {
        try {
            System.out.println("发送消息:" + msg);
            msgWebsocket.sendMessage2(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}