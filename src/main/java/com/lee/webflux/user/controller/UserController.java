package com.lee.webflux.user.controller;

import com.lee.webflux.user.entity.User;
import com.lee.webflux.user.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * @ClassName UserController
 * @Author LS
 * @Description 类描述:如果有Spring Data开发经验的话，切换到Spring Data Reactive的难度并不高。
 * 跟Spring WebFlux类似：原来返回User的话，那现在就返回Mono<User>；原来返回List<User>的话，那现在就返回Flux<User>。
 * @Date 2018/10/10 9:58
 * @Version 1.0
 **/
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServiceImpl userService;

    @PostMapping("")
    public Mono<User> save(User user) {
        return this.userService.save(user);
    }

    @DeleteMapping("/{username}")
    public Mono<Long> deleteByUsername(@PathVariable String username) {
        return this.userService.deleteByUsername(username);
    }

    @GetMapping("/{username}")
    public Mono<User> findByUsername(@PathVariable String username) {
        return this.userService.findByUsername(username);
    }



    //@GetMapping("")//数据并非异步响应而是等 n*3 秒后,将全部数据显示.
    /**
     *我们也加一个MediaType，不过由于这里返回的是JSON，因此不能使用TEXT_EVENT_STREAM，
     * 而是使用APPLICATION_STREAM_JSON，即application/stream+json格式。
     * @return
     */
    @GetMapping(value = "",produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<User> findAll() {
        //return this.userService.findAll();
        //每条数据延迟3秒.验证是否是异步响应式
        return this.userService.findAll().delayElements(Duration.ofSeconds(3));
    }

}
