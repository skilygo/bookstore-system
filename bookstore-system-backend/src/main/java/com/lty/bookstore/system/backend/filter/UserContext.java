package com.lty.bookstore.system.backend.filter;

import lombok.Data;

@Data
public class UserContext {
    private String token;
    private String userId;
    private String phone;
}
