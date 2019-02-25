package com.jefflnorton.gateway.service;

import com.jefflnorton.gateway.domain.LegacyToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Slf4j
@Service
public class LegacyTokenService {
    private static final String LEGACY_TOKEN_URI = "http://localhost:8080/legacy/token";

    private RestTemplate restTemplate;

    @Autowired
    public LegacyTokenService(RestTemplateBuilder builder) {
        this.restTemplate = builder
                .setConnectTimeout(Duration.ofSeconds(30))
                .setReadTimeout(Duration.ofSeconds(30))
                .build();
    }

    public ResponseEntity<LegacyToken> getLegacyToken() {
        //return restTemplate.getForEntity(LEGACY_TOKEN_URI, LegacyToken.class);
        return new ResponseEntity<LegacyToken>(new LegacyToken("asdf"), HttpStatus.OK);
    }
}
