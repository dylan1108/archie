package com.yilami.archie.gateway.scg.listen;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.exception.NacosException;
import com.yilami.archie.config.core.ConfigService;
import com.yilami.archie.gateway.scg.configuration.GatewayRouters;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
@Slf4j
@Component
public class GateWayNacosConfigListener implements ApplicationEventPublisherAware {
    @Autowired
    private RouteDefinitionWriter routedefinitionWriter;
    private ApplicationEventPublisher publisher;
    private static final Map<String, RouteDefinition> ROUTE_MAP = new ConcurrentHashMap<>();
    @Autowired
    private GatewayRouters gatewayRoutes;
    @Resource
    private RefreshScope refreshScope;
    @Value(value = "${spring.cloud.nacos.config.server-addr}")
    private String serverAddr;
    @Value(value = "${spring.cloud.nacos.config.group:DEFAULT_GROUP}")
    private String group;
    @Value(value = "${spring.cloud.nacos.config.namespace}")
    private String namespace;
    private String routeDataId = "gateway-routes.yml";
    @PostConstruct
    public void onMessage() throws NacosException {
        log.info("serverAddr={}", serverAddr);
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);
        properties.put(PropertyKeyConst.NAMESPACE, namespace);
        ConfigService configService = NacosFactory.createConfigService(properties);
        this.publisher(gatewayRoutes.getRoutes());
        log.info("gatewayProperties=" + JSONObject.toJSONString(gatewayRoutes));
        configService.addListener(routeDataId, group, new Listener() {
            @Override
            public Executor getExecutor() {
                return null;
            }
            @Override
            public void receiveConfigInfo(String config) {
                log.info("监听nacos配置: {}, 旧的配置: {}, 新的配置: {}", routeDataId, gatewayRoutes, config);
                refreshScope.refresh("gatewayRoutes");
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    log.error(ExceptionUtil.getMessage(e));
                }
                publisher(gatewayRoutes.getRoutes());
            }
        });
    }
    private boolean rePut(List<RouteDefinition> routeDefinitions) {
        if (MapUtils.isEmpty(ROUTE_MAP) && CollectionUtils.isEmpty(routeDefinitions)) {
            return true;
        }
        if (CollectionUtils.isEmpty(routeDefinitions)) {
            return true;
        }
        Set<String> strings = ROUTE_MAP.keySet();
        return strings.stream().sorted().collect(Collectors.joining())
                .equals(routeDefinitions.stream().map(v -> v.getId()).sorted().collect(Collectors.joining()));
    }
    /**
     * 增加路由
     *
     * @param def
     * @return
     */
    public Boolean addRoute(RouteDefinition def) {
        try {
            log.info("添加路由: {} ", def);
            routedefinitionWriter.save(Mono.just(def)).subscribe();
            ROUTE_MAP.put(def.getId(), def);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    /**
     * 删除路由
     *
     * @return
     */
    public Boolean clearRoute() {
        for (String id : ROUTE_MAP.keySet()) {
            routedefinitionWriter.delete(Mono.just(id)).subscribe();
        }
        ROUTE_MAP.clear();
        return false;
    }
    /**
     * 发布路由
     */
    private void publisher(String config) {
        this.clearRoute();
        try {
            log.info("重新更新动态路由");
            List<RouteDefinition> gateway = JSONObject.parseArray(config, RouteDefinition.class);
            for (RouteDefinition route : gateway) {
                this.addRoute(route);
            }
            publisher.publishEvent(new RefreshRoutesEvent(this.routedefinitionWriter));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 发布路由
     */
    private void publisher(List<RouteDefinition> routeDefinitions) {
        this.clearRoute();
        try {
            log.info("重新更新动态路由: ");
            for (RouteDefinition route : routeDefinitions) {
                this.addRoute(route);
            }
            publisher.publishEvent(new RefreshRoutesEvent(this.routedefinitionWriter));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher app) {
        publisher = app;
    }
}
