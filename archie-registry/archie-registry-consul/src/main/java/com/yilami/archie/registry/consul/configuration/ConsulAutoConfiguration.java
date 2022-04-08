package com.yilami.archie.registry.consul.configuration;

import com.ecwid.consul.v1.ConsulClient;
import com.yilami.archie.registry.core.RegistryClient;
import com.yilami.archie.registry.consul.ConsulInstance;
import com.yilami.archie.registry.consul.ConsulRegistryClient;
import com.yilami.archie.registry.consul.ConsulServerListFilter;
import com.yilami.archie.registry.consul.RegistrationCustomizer;
import com.yilami.archie.registry.consul.SmurfConsulClient;
import org.springframework.cloud.consul.discovery.ConsulDiscoveryProperties;
import org.springframework.cloud.consul.serviceregistry.ConsulRegistrationCustomizer;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Weihua
 * @since 1.0.0
 */
@Configuration
@RibbonClients(defaultConfiguration = ConsulRibbonAutoConfiguration.class)
public class ConsulAutoConfiguration {

    @Bean
    public RegistryClient<ConsulInstance> registryClient(ConsulClient client, ConsulDiscoveryProperties properties){
        return new ConsulRegistryClient(client, properties);
    }

    @Bean
    public ConsulRegistrationCustomizer consulRegistrationCustomizer(){
        return new RegistrationCustomizer();
    }

    @Bean
    public ConsulServerListFilter serverListFilter(){
        return new ConsulServerListFilter();
    }

    @Bean
    public ConsulClient consulClient(){
        return new SmurfConsulClient();
    }

}
