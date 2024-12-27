package com.lty.bookstore.system.backend.api.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSmsCaptchaRequest {
    @NotEmpty
    private String userPhone;
}
