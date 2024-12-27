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
public class UpdateBookRequest {
    private String bookId;
    private String bookName;
    private String category;
    private String author;
    private BigDecimal price;
}
