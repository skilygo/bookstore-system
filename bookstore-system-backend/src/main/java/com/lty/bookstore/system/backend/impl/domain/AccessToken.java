package com.lty.bookstore.system.backend.impl.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
public class AccessToken implements Serializable {
    private static final long serialVersionUID = -1L;
    @NotNull
    private String accessToken;
    @NotNull
    private Long expiresIn;
}
