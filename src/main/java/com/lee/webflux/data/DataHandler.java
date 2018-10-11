package com.lee.webflux.data;

import com.sun.security.ntlm.Server;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

/**
 * @ClassName DataHandler
 * @Author LS
 * @Description 类描述:
 * @Date 2018/10/9 15:54
 * @Version 1.0
 **/
@Component
public class DataHandler {
    public Mono<ServerResponse> getTime(ServerRequest serverRequest){
        return  ServerResponse.ok().contentType(MediaType.TEXT_PLAIN).body(
                Mono.just("现在时间 "+new SimpleDateFormat("HH:mm:ss").format(new Date())),String.class
        );
    }

    public Mono<ServerResponse> getDate(ServerRequest serverRequest){
        return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN).body(
                        Mono.just("现在日期 "+new SimpleDateFormat("HH:mm:ss").format(new Date())),String.class
                );
    }

    //长连接响应
    public Mono<ServerResponse> sendTimePerSec(ServerRequest serverRequest){
        return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM).body(
                Flux.interval(Duration.ofSeconds(1))
                        .map(l -> new SimpleDateFormat("HH:mm:ss").format(new Date())),
                        String.class);
    }

}
