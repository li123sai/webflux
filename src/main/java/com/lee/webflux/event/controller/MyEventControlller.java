package com.lee.webflux.event.controller;

import com.lee.webflux.event.dao.MyEventRepository;
import com.lee.webflux.event.entity.MyEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.awt.*;
import java.time.Duration;

/**
 * @ClassName MyEventControlller
 * @Author LS
 * @Description 类描述:
 * @Date 2018/10/10 16:23
 * @Version 1.0
 **/
@RestController
@RequestMapping("/events")
public class MyEventControlller {
    @Autowired
    private MyEventRepository eventRepository;

    /**
     * 指定传入的数据是application/stream+json，与getEvents方法的区别在于这个方法是consume这个数据流；
     insert返回的是保存成功的记录的Flux，但我们不需要，使用then方法表示“忽略数据元素，只返回一个完成信号”。
     * @param event
     * @return
     */
    @PostMapping(path="",consumes = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Mono<Void> loadEvents(@RequestBody Flux<MyEvent> event){
        return this.eventRepository.insert(event).then();
    }

    /**
     *
     * @return
     */
    @GetMapping(path="",produces= MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<MyEvent> getEvents(){
        return this.eventRepository.findBy().delayElements(Duration.ofSeconds(3));
    }
}
