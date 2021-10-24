package com.shu.ming.mp.modules.websocket.service;

import com.shu.ming.mp.modules.websocket.bean.WebSocket;

import java.util.Map;

/**
 * @author JGod
 * @create 2021-10-14-14:51
 */
public interface WebService {
    /**
     * 在容器中的名字
     */
    String WEBSOCKET_MANAGER_NAME  = "webService";

    /**
     * 根据标识获取websocket session
     * @param identifier 标识
     * @return WebSocket
     */
    WebSocket get(String identifier);

    /**
     * 放入一个 websocket session
     * @param identifier 标识
     * @param webSocket websocket
     */
    void put(String identifier , WebSocket webSocket);

    /**
     * 删除
     * @param identifier 标识
     */
    void remove(String identifier);

    /**
     * 获取当前机器上的保存的WebSocket
     * @return WebSocket Map
     */
    Map<String , WebSocket> localWebSocketMap();

    /**
     * 统计所有在线人数
     * @return 所有在线人数
     */
    default int size(){
        return localWebSocketMap().size();
    }

    /**
     * 给某人发送消息
     * @param identifier 标识
     * @param message 消息
     */
    void sendMessage(String identifier, String message);

    /**
     * 广播
     * @param message 消息
     */
    void broadcast(String message);

    /**
     * 修改当前的状态
     * @param identifier 标识
     * @param status 状态
     */
    void changeStatus(String identifier , int status);
}
