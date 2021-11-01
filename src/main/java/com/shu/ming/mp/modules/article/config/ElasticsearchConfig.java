package com.shu.ming.mp.modules.article.config;

import com.shu.ming.mp.modules.article.service.impl.ArticleServiceImpl;
import com.shu.ming.mp.modules.article.util.ESUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author JGod
 * @create 2021-04-19-19:14
 */
@Configuration
@ConditionalOnProperty(value = "es.enable", havingValue = "true")
@Slf4j
public class ElasticsearchConfig {

    @Value("${elasticsearch.host}")
    private String host;

    @Value("${elasticsearch.port}")
    private int port;

    @Value("${elasticsearch.connTimeout}")
    private int connTimeout;

    @Value("${elasticsearch.socketTimeout}")
    private int socketTimeout;

    @Value("${elasticsearch.connectionRequestTimeout}")
    private int connectionRequestTimeout;

    @Bean
    public RestHighLevelClient restHighLevelClient(){
        log.info("开始配置 elasticSearch hostname : {}, port: {}", host, port);

        RestClientBuilder builder = RestClient.builder(new HttpHost(host, port))
                .setRequestConfigCallback(requestConfigBuilder -> requestConfigBuilder
                        .setConnectTimeout(connTimeout)
                        .setSocketTimeout(socketTimeout)
                        .setConnectionRequestTimeout(connectionRequestTimeout));
        return new RestHighLevelClient(builder);
    }

    @Bean
    public ESUtil esUtil(RestHighLevelClient restHighLevelClient){
        log.info("生成EsUtil 对象");
        return new ESUtil(restHighLevelClient);
    }
}
