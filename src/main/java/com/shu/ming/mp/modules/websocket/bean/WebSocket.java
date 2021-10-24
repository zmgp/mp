package com.shu.ming.mp.modules.websocket.bean;

import lombok.Data;

import javax.websocket.Session;
import java.io.Serializable;

/**
 * @author JGod
 * @create 2021-10-14-14:53
 */
@Data
public class WebSocket implements Serializable {
    public static final int STATUS_AVAILABLE       = 0;
    public static final int STATUS_UNAVAILABLE     = 1;
    private String identifier;
    private Session session;
    private int status;
}
