package com.lee.webflux.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
//对使用较多的方法直接导成静态的包
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
/**
 * @ClassName DataRouter
 * @Author LS
 * @Description 类描述:
 * @Date 2018/10/9 16:09
 * @Version 1.0
 **/
@Configuration
public class DataRouter {

    @Autowired
    private DataHandler dataHandler;

    @Bean
    public RouterFunction<ServerResponse> timerRouter(){
        //导入静态的RequestPredicates
        return route(GET("/time"),req->dataHandler.getTime(req))
                .andRoute(GET("/date"),req->dataHandler.getDate(req))
                .andRoute(GET("/times"),dataHandler::sendTimePerSec);
    }
}
