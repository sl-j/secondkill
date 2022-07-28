package com.lei.secondkill;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lei.secondkill.pojo")
public class SecondkillApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecondkillApplication.class, args);
    }

}
