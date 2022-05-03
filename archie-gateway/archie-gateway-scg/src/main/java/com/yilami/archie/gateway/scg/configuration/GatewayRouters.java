package com.yilami.archie.gateway.scg.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author yilami
 * @since 1.0.0
 */

@Slf4j
@RefreshScope
@Component
@ConfigurationProperties(prefix = "spring.cloud.gateway")
public class GatewayRouters {
    /**
     * 路由列表.
     */
    @NotNull
    @Valid
    private List<RouteDefinition> routes = new ArrayList<>();

    /**
     * 适用于每条路线的过滤器定义列表
     */
    private List<FilterDefinition> defaultFilters = new ArrayList<>();
    private List<MediaType> streamingMediaTypes = Arrays
            .asList(MediaType.TEXT_EVENT_STREAM,
                    MediaType.APPLICATION_STREAM_JSON);
    public List<RouteDefinition> getRoutes() {
        return routes;
    }
    public void setRoutes(List<RouteDefinition> routes) {
        this.routes = routes;
        if (routes != null && routes.size() > 0 && log.isDebugEnabled()) {
            log.debug("Routes supplied from Gateway Properties: " + routes);
        }
    }
    public List<FilterDefinition> getDefaultFilters() {
        return defaultFilters;
    }
    public void setDefaultFilters(List<FilterDefinition> defaultFilters) {
        this.defaultFilters = defaultFilters;
    }
    public List<MediaType> getStreamingMediaTypes() {
        return streamingMediaTypes;
    }
    public void setStreamingMediaTypes(List<MediaType> streamingMediaTypes) {
        this.streamingMediaTypes = streamingMediaTypes;
    }
    @Override
    public String toString() {
        return "GatewayProperties{" + "routes=" + routes + ", defaultFilters="
                + defaultFilters + ", streamingMediaTypes=" + streamingMediaTypes + "}";
    }
}
