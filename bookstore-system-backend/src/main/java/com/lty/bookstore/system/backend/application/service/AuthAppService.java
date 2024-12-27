package com.lty.bookstore.system.backend.application.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.lty.bookstore.system.backend.BookStoreProperties;
import com.lty.bookstore.system.backend.exception.AuthenticationFailedException;
import com.lty.bookstore.system.backend.impl.domain.AccessToken;
import com.lty.bookstore.system.backend.impl.domain.entity.System;
import com.lty.bookstore.system.backend.impl.repository.ApiCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;

@Service
public class AuthAppService {

    public static final String CLAIM_SCOPE = "scope";
    public static final String SCOPE_SYSTEM = "SYSTEM";

    @Autowired
    private BookStoreProperties bookStoreProperties;
    @Autowired
    private ApiCredentialRepository apiCredentialRepository;

    @Transactional(readOnly = true)
    public AccessToken auth2Token(String clientId, String clientSecret) {
        System apiCredential = apiCredentialRepository.findByClientId(clientId).orElseThrow(AuthenticationFailedException::new);
        if (!apiCredential.getClientSecret().equals(clientSecret)) {
            throw new AuthenticationFailedException();
        }
        final String pmsId = apiCredential.getSystemId();
        Algorithm algorithm = Algorithm.HMAC256(bookStoreProperties.getOpenApi().getSecret());
        return AccessToken.builder()
                .accessToken(JWT.create()
                        .withSubject(pmsId)
                        .withExpiresAt(Date.from(Instant.now().plusSeconds(bookStoreProperties.getOpenApi().getTokenExpiresIn())))
                        .withClaim(SCOPE_SYSTEM, pmsId)
                        .withClaim(CLAIM_SCOPE, SCOPE_SYSTEM)
                        .sign(algorithm)
                )
                .expiresIn(bookStoreProperties.getOpenApi().getTokenExpiresIn())
                .build();

    }

}
