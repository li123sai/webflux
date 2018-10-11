package com.lee.webflux;

import com.lee.webflux.event.entity.MyEvent;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * @ClassName Tests
 * @Author LS
 * @Description 类描述:
 * @Date 2018/10/11 11:12
 * @Version 1.0
 **/
public class Tests {

    /**
     * 声明速度为每秒一个MyEvent元素的数据流，不加take的话表示无限个元素的数据流；
     声明请求体的数据格式为application/stream+json；
     body方法设置请求体的数据
     */
    @Test
    public void webClientLoadEventsTest(){
        //创建一个Flux流
        Flux<MyEvent> eventFlux=Flux.interval(Duration.ofSeconds(1))
                .map(s -> new MyEvent(System.currentTimeMillis(),s+"条消息发送:"+Math.random()))
                .take(5);
        WebClient webClient=WebClient.create("http://localhost:8080");
        webClient.post().uri("events")
                .contentType(MediaType.APPLICATION_STREAM_JSON)
                .body(eventFlux,MyEvent.class)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }


    /**
     *通过webclient访问无线流,
     * 访问之后就挂起,当用户往数据库中放入
     * 新数据时自动获取新的数据
     * (这里获取到myevent对象.可以操作)
     */
    @Test
    public void getEventsTest(){
        WebClient webClient=WebClient.create("http://localhost:8080");
        webClient.get().uri("events")
                .accept(MediaType.APPLICATION_STREAM_JSON)
                .retrieve()
                .bodyToFlux(MyEvent.class)
                .log()
                .blockLast();

    }
}
