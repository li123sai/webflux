package com.lee.webflux.user.repository;

import com.lee.webflux.user.entity.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

/**
 * @ClassName UserRepository
 * @Author LS
 * @Description 类描述:
 * @Date 2018/10/9 17:28
 * @Version 1.0
 **/
public interface UserRepository extends ReactiveCrudRepository<User,String>{
    Mono<User> findByUsername(String username);

    Mono<Long> deleteByUsername(String username);
}
