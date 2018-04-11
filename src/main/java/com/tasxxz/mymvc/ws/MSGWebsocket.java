package com.tasxxz.mymvc.ws;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by lsd on 2018/4/11.
 */
@Component
@ServerEndpoint("/websocket/{sn}")
public class MSGWebsocket {
//    private static final Logger LOGGER = LoggerFactory.getLogger(MSGWebsocket.class);

    private static int onlineCount = 0;

    public static CopyOnWriteArraySet<MSGWebsocket> webSocketSet = new CopyOnWriteArraySet<>();

    private Session session;

    private String sn;

    /**
     *
     * @param sn
     * @param session
     */
    @OnOpen
    public void onOpen (@PathParam("sn") String sn, Session session) throws Exception {
        this.session = session;
        this.sn = sn;
        if (webSocketSet.add(this)) {
            addOnlineCount();
            System.out.println("有新连接" + sn + "加入!当前在线人数为" + getOnlineCount());
//            LOGGER.info("有新连接[{}][group:{},uid:{}]加入!当前在线人数为{}", session.getId(), group, uid, getOnlineCount());
        } else {
//            LOGGER.info("连接[{}][group:{},uid:{}]已存在", session.getId(), group, uid);
            System.out.println("连接" + sn + "已存在");
        }

//        // 上线发送没接收的消息
//        for (UnSendMsg unSendMsg : MSG_LIST) {
//            if (group.equals(unSendMsg.getReceiveGroup()) && uid.equals(unSendMsg.getReceiverId())) {
//                sendMessage(unSendMsg.getContent());
//                MSG_LIST.remove(unSendMsg);
//            }
//        }
    }

    @OnClose
    public void onClose (){
        if (webSocketSet.remove(this)) {
            subOnlineCount();
            System.out.println("有一连接" + sn + "关闭!当前在线人数为" + getOnlineCount());
//            LOGGER.info("有一连接[{}][clientType:{},uid:{}]关闭!当前在线人数为{}", session.getId(), group, uid, getOnlineCount());
        } else {
            System.out.println("连接" + sn + "已关闭" + getOnlineCount());
//            LOGGER.info("连接[{}][clientType:{},uid:{}]已关闭", session.getId(), group, uid);
        }
    }

    @OnError
    public void onError(Throwable t) {
        t.printStackTrace();
//        LOGGER.error("连接出错", t);
    }

    /**
     *
     * @param message 消息
     * @param session
     * @throws IOException
     */
    @OnMessage
    public void onMessage (String message, Session session) throws IOException {
        System.out.println("自客户端" + sn + "的消息:" + message);
        sendMessage("已收到信息[" + message + "]--Server");
//        LOGGER.info("来自客户端[{}][clientType:{},uid:{}]的消息:{}", session.getId(), group, uid, message);
//        WebSocketMsg msg = JsonUtils.json2Object(message, WebSocketMsg.class);

//        boolean send = false;
//        for (MyWebSocket item : webSocketSet) {
//            if (item.group.equals(msg.getReceiveGroup()) && Objects.equals(msg.getReceiverId(), item.uid)) {
//                item.sendMessage(message);
//                send = true;
//            }
//        }

//        if (!send) {
//            UnSendMsg unSendMsg = new UnSendMsg();
//            unSendMsg.setContent(message);
//            unSendMsg.setReceiverId(msg.getReceiverId());
//            unSendMsg.setReceiveGroup(msg.getReceiveGroup());
//
//            if ("2".equals(msg.getLiveType())) {
//                Long et = msg.getExpireTime();
//                if (et == null) {
//                    et = 60000L; // 默认1分钟后过期
//                }
//                unSendMsg.setEndTime(new Date(System.currentTimeMillis() + et));
//            }
//            MSG_LIST.add(unSendMsg);
//        }

//        // 群发消息
//        for ( MyWebSocket item : webSocketSet ) {
//            if (item == this) {
//                continue;
//            }
//            item.sendMessage(message);
//        }
    }

    public void sendMessage (String message) throws IOException {
        System.out.println("消息发送 =>  " + sn + "接收消息:" + message);
//        LOGGER.info("消息发送 =>  [{}][group:{},uid:{}]接收消息:{}", session.getId(), group, uid, message);
        this.session.getBasicRemote().sendText(message);
    }

    public synchronized void sendMessage2(String message) throws IOException {
        System.out.println("消息发送 =>  " + sn + "接收消息:" + message);
//        LOGGER.info("消息发送 =>  [{}][group:{},uid:{}]接收消息:{}", session.getId(), group, uid, message);
        this.session.getBasicRemote().sendText(message);
    }

    public static synchronized  int getOnlineCount (){
        return MSGWebsocket.onlineCount;
    }

    public static synchronized void addOnlineCount (){
        MSGWebsocket.onlineCount++;
    }

    public static synchronized void subOnlineCount (){
        MSGWebsocket.onlineCount--;
    }

    public String getSn() {
        return sn;
    }

}
