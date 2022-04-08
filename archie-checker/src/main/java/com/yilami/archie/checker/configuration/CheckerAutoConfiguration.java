package com.yilami.archie.checker.configuration;

import com.yilami.archie.checker.aop.MessageHelper;
import com.yilami.archie.checker.aop.CheckerInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Weihua
 * @since 1.0.0
 */
@Configuration
@ConditionalOnProperty(value = "smurf.checker.enabled", matchIfMissing = true)
public class CheckerAutoConfiguration {

    @Bean
    public MessageHelper messageHelper(MessageSource messageSource){
        return new MessageHelper(messageSource);
    }

    @Bean
    public CheckerInterceptor validatorInterceptor(){
        return new CheckerInterceptor();
    }

}
