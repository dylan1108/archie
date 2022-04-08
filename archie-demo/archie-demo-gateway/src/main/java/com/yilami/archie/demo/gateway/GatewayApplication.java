package com.yilami.archie.demo.gateway;

import com.yilami.archie.starter.gateway.annotation.ArchieGateway;
import org.springframework.boot.SpringApplication;

/**
 * @author Weihua
 * @since 1.0.0
 */
@ArchieGateway
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
