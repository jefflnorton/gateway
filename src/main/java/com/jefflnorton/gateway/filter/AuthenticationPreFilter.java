package com.jefflnorton.gateway.filter;

import com.jefflnorton.gateway.domain.LegacyToken;
import com.jefflnorton.gateway.service.LegacyTokenService;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpSession;
import java.util.Collections;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

@Slf4j
public class AuthenticationPreFilter extends ZuulFilter {

    private static final String LEGACY_TOKEN_KEY = "LEGACY_TOKEN";

    @Autowired
    private LegacyTokenService legacyTokenService;

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpSession session = ctx.getRequest().getSession(true);

        return !Collections.list(session.getAttributeNames()).contains(LEGACY_TOKEN_KEY);
    }

    @Override
    public Object run() throws ZuulException {
        ResponseEntity<LegacyToken> legacyTokenResponse = legacyTokenService.getLegacyToken();

        if (legacyTokenResponse.getStatusCode().is2xxSuccessful()) {
            LegacyToken legacyToken = legacyTokenResponse.getBody();

            log.debug("legacyToken: {}", legacyToken);

            HttpSession session = RequestContext.getCurrentContext().getRequest().getSession();
            session.setAttribute(LEGACY_TOKEN_KEY, legacyTokenResponse.getBody());
        } else {
            throw new ZuulException("ERROR: Unsuccessful response generating legacy token", legacyTokenResponse.getStatusCodeValue(), AuthenticationPreFilter.class.getName());
        }

        return null;
    }
}
