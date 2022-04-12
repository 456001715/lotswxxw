package com.lots.lots;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author lots
 */
@SpringBootApplication
@EnableTransactionManagement
public class LotsApplication {
    public static void main(String[] args) {
        SpringApplication.run(LotsApplication.class, args);
    }
}