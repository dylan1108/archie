package com.uhasoft.smurf.ratelimit.core;

import com.yilami.archie.common.constant.SmurfConstant;
import com.yilami.archie.common.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

import static com.uhasoft.smurf.ratelimit.core.constant.RateLimitConstant.DEFAULT_ORIGIN;

/**
 * @author Weihua
 * @since 1.0.0
 */
public class DefaultOriginParser implements OriginParser<HttpServletRequest> {

    @Override
    public String parse(HttpServletRequest request) {
        String origin = request.getHeader(SmurfConstant.LAST_HOP);
        return StringUtils.hasText(origin) ? origin : DEFAULT_ORIGIN;
    }
}
