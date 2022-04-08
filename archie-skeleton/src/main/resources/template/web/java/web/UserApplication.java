package ${basePackage}.web;

import com.yilami.archie.starter.server.annotation.ArchieServer;
import org.springframework.boot.SpringApplication;

/**
 * @author Weihua
 * @since 1.0.0
 */
@ArchieServer
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}
