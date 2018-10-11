package com.lee.webflux;

import com.lee.webflux.event.entity.MyEvent;
import com.lee.webflux.user.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/*
@RunWith(SpringRunner.class)
@SpringBootTest
*/
//不需要启动项目即可访问,因为每次启动项目数据便会重新加载myevent类
public class WebfluxApplicationTests {

	/**
	 * 创建WebClient对象并指定baseUrl；
	 HTTP GET；
	 异步地获取response信息；
	 将response body解析为字符串；
	 打印出来；
	 由于是异步的，我们将测试线程sleep 1秒确保拿到response，也可以像前边的例子一样用CountDownLatch。
	 * @throws InterruptedException
	 */
	@Test
	public void contextLoads()throws InterruptedException {
		WebClient webClient=WebClient.create("http://localhost:8080");
		Mono<String> resp=webClient.get()
				.uri("/time")
				.retrieve()
				.bodyToMono(String.class);
		resp.subscribe(System.out::println);
		TimeUnit.SECONDS.sleep(1);
	}

	/**
	 * 使用WebClientBuilder来构建WebClient对象；
	 配置请求Header：Content-Type: application/stream+json；
	 获取response信息，返回值为ClientResponse，retrive()可以看做是exchange()方法的“快捷版”；
	 使用flatMap来将ClientResponse映射为Flux；
	 只读地peek每个元素，然后打印出来，它并不是subscribe，所以不会触发流；
	 上个例子中sleep的方式有点low，blockLast方法，顾名思义，在收到最后一个元素前会阻塞，响应式业务场景中慎用。
	 * @throws InterruptedException
	 */
	@Test
	public void getFlux()throws InterruptedException{
		WebClient webClient=WebClient.builder().baseUrl("http://localhost:8080").build();
		webClient.get().uri("/user")
				.accept(MediaType.APPLICATION_STREAM_JSON)
				.exchange()
				.flatMapMany(response -> response.bodyToFlux(User.class))
				.doOnNext(System.out::println)
				.blockLast();
	}

	/**
	 * 配置请求Header：Content-Type: text/event-stream，即SSE；
	 这次用log()代替doOnNext(System.out::println)来查看每个元素；
	 由于/times是一个无限流，这里取前10个，会导致流被取消；
	 * @throws InterruptedException
	 */
	@Test
	public void  getTimes()throws InterruptedException{
		WebClient webClient= WebClient.create("http://localhost:8080");
		webClient.get().uri("/times")
				.accept(MediaType.TEXT_EVENT_STREAM)
				.retrieve()
				.bodyToFlux(String.class)
				.log()
				.take(10)
				.blockLast();
	}





}
