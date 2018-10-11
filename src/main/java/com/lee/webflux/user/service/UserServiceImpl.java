package com.lee.webflux.user.service;

import com.lee.webflux.user.entity.User;
import com.lee.webflux.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @ClassName UserServiceImpl
 * @Author LS
 * @Description 类描述:
 * @Date 2018/10/9 17:36
 * @Version 1.0
 **/
@Service
public class UserServiceImpl {

    @Autowired
    private UserRepository userRepository;

    /**
     * 保存或更新。
     * 如果传入的user没有id属性，由于username是unique的，在重复的情况下有可能报错，
     * 这时找到以保存的user记录用传入的user更新它。
     */
    public Mono<User> save(User user){
        /**
         * onErrorResume进行错误处理；
         *找到username重复的记录；
         *拿到ID从而进行更新而不是创建；
         *由于函数式为User -> Publisher，所以用flatMap。
         */
        return userRepository.save(user).onErrorResume(
          e -> userRepository.findByUsername(user.getUsername()).flatMap(
                  originalUser -> {
                      user.setId(originalUser.getId());
                      return userRepository.save(user);
                  }
          )
        );
    }

    public Mono<Long> deleteByUsername(String username){
        return userRepository.deleteByUsername(username);
    }

    public Mono<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public Flux<User> findAll(){
        return userRepository.findAll();
    }
}
