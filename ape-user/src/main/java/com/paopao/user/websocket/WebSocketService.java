package com.paopao.user.websocket;

import com.paopao.config.WebSocketConfig;
import com.paopao.config.WebSocketServerConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author paoPao
 * @Date 2023/1/31
 * @Description webSocket申请连接服务
 */
@Slf4j
@Component
@ServerEndpoint(value = "/service/socket", configurator = WebSocketServerConfig.class)
public class WebSocketService {

    /**
     * 记录当前连接数
     */
    private static AtomicInteger online = new AtomicInteger(0);

    /**
     * 存在所有的在线客户端
     */
    private static Map<String, WebSocketService> clients = new ConcurrentHashMap<>();

    /**
     * 某个客户端连接的session会话
     */
    private Session session;

    // 当前的会话key
    private String erp = "";

    /**
     * 新建会话
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        //获取用户信息
        try {
            Map<String, Object> userProperties = config.getUserProperties();
            this.erp = (String) userProperties.get("erp");
            this.session = session;
            if (clients.containsKey(this.erp)) {
                clients.get(this.erp).session.close();
                clients.remove(this.erp);
                online.decrementAndGet();
            }
            clients.put(this.erp, this);
            online.incrementAndGet();
            log.info("有新连接加入：{}，当前在线人数：{}", this.erp, online.get());
            sendMessage("连接成功", session);
        } catch (Exception e) {
            log.error("连接关闭错误，错误原因{}", e.getMessage(), e);
        }
    }

    /**
     * 关闭会话
     */
    @OnClose
    public void onClose() {
        try {
            if (clients.containsKey(this.erp)) {
                clients.get(this.erp).session.close();
                clients.remove(this.erp);
                online.decrementAndGet();
            }
            log.info("有一处连接关闭{}，当前连接人数：{}", this.session, online.get());
        } catch (IOException e) {
            log.error("连接关闭错误，错误原因{}", e.getMessage(), e);
        }
    }

    /**
     * 错误会话
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("Socket:{},发生错误,错误原因{}", erp, error.getMessage(), error);
        try {
            session.close();
        } catch (Exception e) {
            log.error("onError.Exception{}", e.getMessage(), e);
        }
    }

    /**
     * 收到客户端消息后调用的方法
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        log.info("服务端收到客户端【{}】的消息：{}", this.erp, message);
        //心跳机制
        if (message.equals("ping")) {
            this.sendMessage("pong", session);
        }
    }

    /**
     * 指定发送消息
     */
    public void sendMessage(String message, Session session) throws IOException {
        log.info("服务端给客户端[{}]发送消息{}", this.erp, message);
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            log.error("{}发送消息发生异常，异常原因{}", this.erp, message);
        }
    }

    /**
     * 群发发送消息
     */
    public void sendMessage(String message) throws IOException {
        for (Map.Entry<String, WebSocketService> entry : clients.entrySet()) {
            String erp = entry.getKey();
            WebSocketService socket = entry.getValue();
            try {
                Session session = socket.session;
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                log.error("{}发送消息发生异常，异常原因{}", this.erp, message);
            }
        }
    }
}
