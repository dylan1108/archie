package com.yilami.archie.client.configuration;

import com.yilami.archie.client.interceptor.FeignInterceptor;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Weihua
 * @since 1.0.0
 */
@Configuration
public class ClientAutoConfiguration {

    @Bean
    public RequestInterceptor feignInterceptor(){
        return new FeignInterceptor();
    }
}
