package com.solstice.ecommerceshipment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class ECommerceShipmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(ECommerceShipmentApplication.class, args);
    }
}
