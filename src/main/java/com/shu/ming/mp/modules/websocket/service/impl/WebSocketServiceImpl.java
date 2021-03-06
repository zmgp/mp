package com.shu.ming.mp.modules.websocket.service.impl;

import com.shu.ming.mp.modules.websocket.bean.OnLinePeople;
import com.shu.ming.mp.modules.websocket.bean.WebSocket;
import com.shu.ming.mp.modules.websocket.service.WebService;
import com.shu.ming.mp.commons.util.BloomFilterUtil;
import com.shu.ming.mp.commons.util.RedisUtil;
import com.shu.ming.mp.commons.util.WebSocketUtil;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author JGod
 * @create 2021-10-14-14:51
 */
@Slf4j
public class WebSocketServiceImpl implements WebService {

    private final String PREFIX = "mp:chat:";

    private RedisUtil redisUtil;

    public WebSocketServiceImpl(RedisUtil redisUtil){
        this.redisUtil = redisUtil;
    }
    /**
     * 因为全局只有一个 WebSocketManager ，所以才敢定义为非static
     */
    private final Map<String, WebSocket> connections = new ConcurrentHashMap<>(128);
    private final Map<String, OnLinePeople> onLines = new ConcurrentHashMap<>(128);

    @Override
    public WebSocket get(String identifier) {
        return connections.get(identifier);
    }

    @Override
    public void put(String identifier, WebSocket webSocket) {
        log.info("用户 {} 上线了：", identifier);
        connections.put(identifier , webSocket);
        saveOnLinePeople(webSocket);

        Set<String> keys = redisUtil.keys(PREFIX.concat(identifier).concat(":").concat("*"));
        keys.stream().sorted().forEach(
                (key) -> {
                    WebSocketUtil.sendMessage(webSocket.getSession() , redisUtil.get(key));
                    redisUtil.expire(key, 1L, TimeUnit.MICROSECONDS);
                }
        );
    }

    @Override
    public void remove(String identifier) {
        connections.remove(identifier);
        onLines.remove(identifier);
    }


    @Override
    public Map<String, WebSocket> localWebSocketMap() {
        return connections;
    }

    @Override
    public List<OnLinePeople> onLinePeople() {
        return new ArrayList<>(onLines.values());
    }

    @Override
    public Map<String, OnLinePeople> onLinePeopleMap() {
        return onLines;
    }

    @Override
    public void sendMessage(String identifier, String message) {
        WebSocket webSocket = get(identifier);
        if(null == webSocket){
            saveRedis(identifier, message);
            return;
        }

        if(WebSocket.STATUS_AVAILABLE != webSocket.getStatus()){
            return;
        }

        WebSocketUtil.sendMessage(webSocket.getSession() , message);
    }

    @Override
    public void broadcast(String message) {
        Set<String> keys = redisUtil.keys(BloomFilterUtil.NAME_PREFIX.concat("*"));
        List<String> values = redisUtil.multiGet(keys);

        values.stream().filter(
                (val) -> !connections.containsKey(val)
        ).forEach(
                (val) -> saveRedis(val, message)
        );

        localWebSocketMap().values().forEach(
                webSocket -> WebSocketUtil.sendMessage(
                        webSocket.getSession() , message));
    }

    /**
     * 修改当前的状态
     * @param identifier 标识
     * @param status 状态
     */
    @Override
    public void changeStatus(String identifier , int status) {
        WebSocket socket = get(identifier);
        if(null == socket){return;}
        socket.setStatus(status);
    }

    private void saveRedis(String name, String msg){
        redisUtil.set(PREFIX.concat(name).concat(":").concat(LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli() + ""), msg);
    }

    private void saveOnLinePeople(WebSocket webSocket){
        if (!onLines.containsKey(webSocket.getIdentifier())){
            OnLinePeople people = new OnLinePeople();
            people.setName(webSocket.getIdentifier());
            people.setUpTime(LocalDateTime.now());
            onLines.put(webSocket.getIdentifier(), people);
        }else {
            onLines.get(webSocket.getIdentifier()).setUpTime(LocalDateTime.now());
        }
    }
}
