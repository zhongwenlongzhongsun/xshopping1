package com.zwl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;
//
@MapperScan("com.zwl.mapper")
@SpringBootApplication
public class Xshopping1Application {

    public static void main(String[] args) {
        SpringApplication.run(Xshopping1Application.class, args);
    }

}
