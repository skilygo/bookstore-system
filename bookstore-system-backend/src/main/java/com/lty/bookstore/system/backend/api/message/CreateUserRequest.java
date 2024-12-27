package com.lty.bookstore.system.backend.api.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {
    private String userPhone;
    private String smsCaptcha;
}

