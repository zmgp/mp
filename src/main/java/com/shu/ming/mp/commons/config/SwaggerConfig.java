package com.shu.ming.mp.commons.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author JGod
 * @create 2021-10-19-19:55
 */
@Configuration
@EnableSwagger2
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SwaggerConfig {

    @Bean
    public Docket createRestApi(){
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        tokenPar.name("token").description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .directModelSubstitute(LocalDateTime.class, Date.class)
                .directModelSubstitute(LocalDate.class, String.class)
                .directModelSubstitute(LocalTime.class, String.class)
                .directModelSubstitute(ZonedDateTime.class, String.class)
                .pathMapping("/")
                .select()
                .apis(Predicates.or(
                        RequestHandlerSelectors.basePackage("com.shu.ming.mp.modules.login.controller"),
                        RequestHandlerSelectors.basePackage("com.shu.ming.mp.modules.ratelimiter.controller"),
                        RequestHandlerSelectors.basePackage("com.shu.ming.mp.modules.websocket.controller"),
                        RequestHandlerSelectors.basePackage("com.shu.ming.mp.modules.article.controller"),
                        RequestHandlerSelectors.basePackage("com.shu.ming.mp.modules.email.controller")
                ))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars)
                .apiInfo(new ApiInfoBuilder()
                        .title("MP项目")
                        .description("MP项目的详细信息......")
                        .version("v1.0")
//                        .contact(new Contact("啊啊啊啊","blog.csdn.net","aaa@gmail.com"))
//                        .license("The Apache License")
//                        .licenseUrl("http://www.baidu.com")
                        .build());
    }
}
