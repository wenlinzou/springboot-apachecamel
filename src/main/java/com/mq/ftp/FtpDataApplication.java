package com.mq.ftp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author wenlizou
 */
@SpringBootApplication
@MapperScan("com.mq.ftp.dao")
public class FtpDataApplication {
    public static void main(String[] args) {
        SpringApplication.run(FtpDataApplication.class, args);

    }
}
