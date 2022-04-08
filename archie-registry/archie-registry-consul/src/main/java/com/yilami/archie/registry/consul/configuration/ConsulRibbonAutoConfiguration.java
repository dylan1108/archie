package com.yilami.archie.registry.consul.configuration;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.DynamicServerListLoadBalancer;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.ServerList;
import com.netflix.loadbalancer.ServerListFilter;
import com.netflix.loadbalancer.ServerListUpdater;
import com.yilami.archie.registry.core.RegistryClient;
import com.yilami.archie.registry.core.SmurfServerList;
import com.yilami.archie.registry.consul.ConsulInstance;
import com.yilami.archie.registry.consul.ConsulServerList;
import org.springframework.context.annotation.Bean;

/**
 * @author Weihua
 * @since 1.0.0
 */
public class ConsulRibbonAutoConfiguration {

    @Bean
    public ServerList<ConsulInstance> ribbonServerList(IClientConfig config, RegistryClient<ConsulInstance> client) {
        SmurfServerList<ConsulInstance> serverList = new ConsulServerList(client);
        serverList.initWithNiwsConfig(config);
        return serverList;
    }

    @Bean
    public ILoadBalancer ribbonLoadBalancer(IClientConfig config, ConsulServerList serverList, ServerListFilter<ConsulInstance> serverListFilter,
                                            IRule rule, IPing ping, ServerListUpdater serverListUpdater){
        return new DynamicServerListLoadBalancer<>(config, rule, ping, serverList, serverListFilter, serverListUpdater);
    }

}
