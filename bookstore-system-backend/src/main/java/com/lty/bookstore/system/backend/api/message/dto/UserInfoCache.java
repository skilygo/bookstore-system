package com.lty.bookstore.system.backend.api.message.dto;

import lombok.Data;

@Data
public class UserInfoCache {
    private String token;
    private String userId;
    private String phone;
}
