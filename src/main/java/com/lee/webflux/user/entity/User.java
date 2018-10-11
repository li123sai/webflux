package com.lee.webflux.user.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * @ClassName User
 * @Author LS
 * @Description 类描述:
 * @Date 2018/10/9 17:18
 * @Version 1.0
 **/
@Data
@Document
public class User {
    @Id
    private String id;

    @Indexed
    private String username;

    private String name;
    private String phone;
    private Date birthday;
}
