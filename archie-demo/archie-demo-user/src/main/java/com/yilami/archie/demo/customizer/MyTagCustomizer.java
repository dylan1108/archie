package com.yilami.archie.demo.customizer;

import com.yilami.archie.registry.core.TagCustomizer;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * @author Weihua
 * @since 1.0.0
 */
@Component
public class MyTagCustomizer implements TagCustomizer {
    @Override
    public List<String> customize() {
        return Collections.singletonList("blue");
    }
}
