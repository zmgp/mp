package com.shu.ming.mp.modules.websocket.config;

/**
 * @author JGod
 * @create 2021-10-14-14:58
 */

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWTUtil;
import com.shu.ming.mp.commons.util.JWTUtils;
import com.shu.ming.mp.modules.websocket.bean.OnLinePeople;
import com.shu.ming.mp.modules.websocket.bean.WebSocket;
import com.shu.ming.mp.modules.websocket.service.WebService;
import com.shu.ming.mp.commons.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Map;

/**
 * NOTE: Nginx反向代理要支持WebSocket，需要配置几个header，否则连接的时候就报404
 proxy_http_version 1.1;
 proxy_set_header Upgrade $http_upgrade;
 proxy_set_header Connection "upgrade";
 proxy_read_timeout 3600s; //这个时间不长的话就容易断开连接
 * @author xiongshiyan at 2018/10/10 , contact me with email yanshixiong@126.com or phone 15208384257
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@ServerEndpoint(value ="/mp/connect/{identifier}")
@Slf4j
public class WebSocketEndpoint {
    /**
     * 路径标识：目前使用token来代表
     */
    private static final String IDENTIFIER = "identifier";

    private static final String OnLineHistory = "onLine:history:";
    /// 无法通过这种方式注入组件
    /*@Autowired
    private WebSocketManager websocketManager;*/

    RedisUtil redisUtil;

    public WebSocketEndpoint() {
        redisUtil = SpringUtil.getBean(RedisUtil.class);
    }

    @OnOpen
    public void onOpen(Session session, @PathParam(IDENTIFIER) String identifier) {
        identifier = resolveIdentifier(identifier);
        try {
            log.info("*** WebSocket opened from sessionId " + session.getId() + " , identifier = " + identifier);

            if(StrUtil.isBlank(identifier)){
                return;
            }
            WebSocket socket = new WebSocket();
            socket.setIdentifier(identifier);
            socket.setSession(session);
            socket.setStatus(WebSocket.STATUS_AVAILABLE);
            WebService websocketManager = getWebSocketManager();
            websocketManager.put(identifier , socket);

        } catch (Exception e) {
            log.error(e.getMessage() , e);
        }
    }

    @OnClose
    public void onClose(Session session , @PathParam(IDENTIFIER) String identifier) {
        identifier = resolveIdentifier(identifier);
        log.info("*** WebSocket closed from sessionId " + session.getId() + " , identifier = " + identifier);
        saveLogout(identifier);
        getWebSocketManager().remove(identifier);
    }

    @OnMessage
    public void onMessage(String message, Session session , @PathParam(IDENTIFIER) String identifier) {
        identifier = resolveIdentifier(identifier);
        log.info("接收到的数据为：" + message + " from sessionId " + session.getId() + " , identifier = " + identifier);

        // todo
        redisUtil.set("mp:reply:".concat(identifier).concat(LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli() + ""), message);
    }

    @OnError
    public void onError(Throwable t , @PathParam(IDENTIFIER) String identifier){
        identifier = resolveIdentifier(identifier);
        log.info("发生异常：, identifier = " + identifier);
        log.error(t.getMessage() , t);
    }

    private WebService getWebSocketManager() {
        return SpringUtil.getBean(WebService.WEBSOCKET_MANAGER_NAME , WebService.class);
    }

    /**
     * 对token类型得identifier进行解析
     * @param identifier
     * @return
     */
    private String resolveIdentifier(String identifier){
        return JWTUtils.resolveTokenName(identifier);
    }

    /**
     * 将离线人信息进行存储
     */
    public void saveLogout(String name) {
        Map<String, OnLinePeople> onLinePeopleMap = getWebSocketManager().onLinePeopleMap();
        OnLinePeople people = onLinePeopleMap.get(name);
        people.setDownTime(LocalDateTime.now());
        redisUtil.lRightPush(OnLineHistory.concat(name).concat(":").concat(DateUtil.formatDate(new Date())), JSONUtil.toJsonStr(people));
    }
}
