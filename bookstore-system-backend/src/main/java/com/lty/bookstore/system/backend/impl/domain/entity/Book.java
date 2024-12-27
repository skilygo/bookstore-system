package com.lty.bookstore.system.backend.impl.domain.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldNameConstants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@FieldNameConstants
@Table(name = "book", uniqueConstraints = {
        @UniqueConstraint(name = "UX__book_id", columnNames = {"book_id"})
})
public class Book extends AbstractEntity {
    @Column(name = "book_id", length = 128, nullable = false)
    private String bookId;
    @Column(name = "book_name", length = 128, nullable = false)
    private String bookName;
    @Column(name = "category", length = 128, nullable = false)
    private String category;
    @Column(name = "author", length = 128, nullable = false)
    private String author;
    @Column(name = "price", nullable = false)
    private BigDecimal price;
}
