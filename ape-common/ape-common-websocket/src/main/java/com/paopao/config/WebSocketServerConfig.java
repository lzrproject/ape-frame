package com.paopao.config;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @Author paoPao
 * @Date 2023/1/31
 * @Description 鉴权
 */
@Component
public class WebSocketServerConfig extends ServerEndpointConfig.Configurator {

    /**
     * 权限校验
     */
    @Override
    public boolean checkOrigin(String originHeaderValue) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        // 权限校验逻辑
        return true;
    }

    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        Map<String, List<String>> parameterMap = request.getParameterMap();
        List<String> erpList = parameterMap.get("erp");
        if (!CollectionUtils.isEmpty(erpList)){
            sec.getUserProperties().put("erp",erpList.get(0));
        }

    }
}
