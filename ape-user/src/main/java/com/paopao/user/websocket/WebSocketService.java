package com.paopao.user.websocket;

import com.paopao.config.WebSocketConfig;
import com.paopao.config.WebSocketServerConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author paoPao
 * @Date 2023/1/31
 * @Description
 */
@Slf4j
@Component
@ServerEndpoint(value = "/service/socket",configurator = WebSocketServerConfig.class)
public class WebSocketService {

    private static AtomicInteger online = new
}
