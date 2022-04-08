package com.yilami.archie.demo.order;

import com.yilami.archie.starter.server.annotation.ArchieServer;
import org.springframework.boot.SpringApplication;

/**
 * @author Weihua
 * @since 1.0.0
 */
@ArchieServer
public class OrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
}
