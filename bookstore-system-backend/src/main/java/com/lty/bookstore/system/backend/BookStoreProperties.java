package com.lty.bookstore.system.backend;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "bs.config")
@Data
public class BookStoreProperties {

    private OpenApiProperties openApi = new OpenApiProperties();

    @Data
    public static class OpenApiProperties {
        private Long tokenExpiresIn = 36000L;
        private String secret = "secret";
    }
}
