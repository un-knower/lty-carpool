package com.lantaiyuan.carpool.charge;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author: Administrator$
 * @project: lty-carpool$
 * @date: 2017/12/27$ 14:03$
 * @description:用户进入
 */
@SpringBootApplication
@ComponentScan(value={"com.lantaiyuan.carpool.common.dao","com.lantaiyuan.carpool.common.domain"})
@Slf4j
public class ChargeApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChargeApplication.class, args);
    }
}
