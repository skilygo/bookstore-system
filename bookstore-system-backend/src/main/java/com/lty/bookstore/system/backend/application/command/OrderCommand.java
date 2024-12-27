package com.lty.bookstore.system.backend.application.command;

import com.lty.bookstore.system.backend.impl.domain.entity.Book;
import com.lty.bookstore.system.backend.impl.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCommand {
    private String orderId;
    private String userId;
    private String bookId;
    private Integer amount;
    private BigDecimal totalPrice;
}
