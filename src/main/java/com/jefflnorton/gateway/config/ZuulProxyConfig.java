package com.jefflnorton.gateway.config;

import com.jefflnorton.gateway.filter.AuthenticationPreFilter;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableZuulProxy
@Configuration
public class ZuulProxyConfig {

    @Bean
    AuthenticationPreFilter authenticationPreFilter() {
        return new AuthenticationPreFilter();
    }
}
