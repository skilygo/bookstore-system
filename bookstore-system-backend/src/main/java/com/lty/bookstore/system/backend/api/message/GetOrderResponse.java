package com.lty.bookstore.system.backend.api.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetOrderResponse {
    private String orderId;
    private Long userId;
    private Long bookId;
    private Integer amount;
    private BigDecimal totalPrice;
}
