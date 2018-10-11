package com.lee.webflux.event.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @ClassName MyEvent
 * @Author LS
 * @Description 类描述:
 * @Date 2018/10/10 16:12
 * @Version 1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "event")
public class MyEvent {
    @Id
    private Long id;
    private String message;
}
