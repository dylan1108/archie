package com.yilami.archie.demo.book;

import com.yilami.archie.starter.server.annotation.ArchieServer;
import org.springframework.boot.SpringApplication;

/**
 * @author Weihua
 * @since 1.0.0
 */
@ArchieServer
public class BookApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookApplication.class, args);
    }
}
