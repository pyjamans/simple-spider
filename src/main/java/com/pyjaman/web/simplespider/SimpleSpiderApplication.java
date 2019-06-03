package com.pyjaman.web.simplespider;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.pyjaman.web")
@MapperScan("com.pyjaman.web.simplespider.mapper")
public class SimpleSpiderApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleSpiderApplication.class, args);
    }

}
