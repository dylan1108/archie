package com.yilami.archie.ratelimit.guava.configuration;

import com.yilami.archie.ratelimit.guava.aop.RequestMappingAop;
import com.yilami.archie.ratelimit.guava.aop.ResourceAop;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Weihua
 * @since 1.0.0
 */
@Configuration
public class GuavaAutoConfiguration {

    @Bean
    public ResourceAop resourceAop(){
        return new ResourceAop();
    }

    @Bean
    public RequestMappingAop requestMappingAop(){
        return new RequestMappingAop();
    }
}
