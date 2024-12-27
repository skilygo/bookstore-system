package com.lty.bookstore.system.backend.application.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookCommand {
    private String bookId;
    private String bookName;
    private String category;
    private String author;
    private BigDecimal price;
    private Integer stock;
}
