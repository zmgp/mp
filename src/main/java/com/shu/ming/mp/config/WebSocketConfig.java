package com.shu.ming.mp.config;



import com.shu.ming.mp.modules.websocket.service.WebService;
import com.shu.ming.mp.modules.websocket.service.impl.WebSocketServiceImpl;
import com.shu.ming.mp.util.BloomFilterUtil;
import com.shu.ming.mp.util.RedisUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author JGod
 * @create 2021-10-13-13:37
 */
@Configuration
public class WebSocketConfig {

    /**
     * description: 这个配置类的作用是要注入ServerEndpointExporter，
     * 这个bean会自动注册使用了@ServerEndpoint注解声明的Websocket endpoint。
     * 如果是使用独立的servlet容器，而不是直接使用springboot的内置容器，
     * 就不要注入ServerEndpointExporter，因为它将由容器自己提供和管理。
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }

    @Bean(WebSocketServiceImpl.WEBSOCKET_MANAGER_NAME)
    public WebService webSocketManager(RedisUtil redisUtil ){
        return new WebSocketServiceImpl(redisUtil);
    }
}
