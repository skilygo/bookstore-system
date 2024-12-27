package com.lty.bookstore.system.backend.api.message;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class AuthRequest implements Serializable {
    private static final long serialVersionUID = 6391853139206038192L;
    private String clientSecret;
    private String clientId;
}
