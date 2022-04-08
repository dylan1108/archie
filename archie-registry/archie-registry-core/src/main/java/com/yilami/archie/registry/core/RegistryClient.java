package com.yilami.archie.registry.core;

import com.yilami.archie.registry.core.model.SmurfInstance;

import java.util.List;

/**
 * @author Weihua
 * @since 1.0.0
 */
public interface RegistryClient<T extends SmurfInstance> {

    List<T> getHealthInstances(String serviceName);
}
