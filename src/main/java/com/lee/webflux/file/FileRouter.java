package com.lee.webflux.file;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ZeroCopyHttpOutputMessage;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * @ClassName FileRouter
 * @Author LS
 * @Description 类描述:
 * @Date 2018/10/9 11:31
 * @Version 1.0
 **/
@RestController
public class FileRouter {

    @PostMapping(value = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<String> requestBodyFlux(@RequestPart("file")FilePart filePart) throws  IOException{
        System.out.println(filePart.filename());
        //文件存放位置
        Path  tempFile = Files.createTempFile("xttblog", filePart.filename());
        AsynchronousFileChannel channel = AsynchronousFileChannel.open(tempFile, StandardOpenOption.WRITE);
            DataBufferUtils.write(filePart.content(),channel,0)
                    .doOnComplete(()->{
                        System.out.println("finish");
                    }).subscribe();
            System.out.println(tempFile.toString());
        return Mono.just(filePart.filename());
    }

    @GetMapping("/download")
    public Mono<Void> downloadByWriteWith(ServerHttpResponse response) throws IOException {
        ZeroCopyHttpOutputMessage zeroCopyResponse = (ZeroCopyHttpOutputMessage) response;
        response.getHeaders().set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=xttblog.png");
        response.getHeaders().setContentType(MediaType.IMAGE_PNG);

        Resource resource = new ClassPathResource("xttblog.png");
        File file = resource.getFile();
        return zeroCopyResponse.writeWith(file, 0, file.length());
    }
}
