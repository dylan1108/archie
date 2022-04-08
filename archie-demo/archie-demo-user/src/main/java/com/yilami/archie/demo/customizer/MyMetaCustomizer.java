package com.yilami.archie.demo.customizer;

import com.yilami.archie.registry.core.MetaCustomizer;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

/**
 * @author Weihua
 * @since 1.0.0
 */
@Component
public class MyMetaCustomizer implements MetaCustomizer {
    @Override
    public Map<String, String> customize() {
        return Collections.singletonMap("cluster", "blue");
    }
}
