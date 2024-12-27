package com.lty.bookstore.system.backend.impl.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@FieldNameConstants
@Table(name = "inventory", uniqueConstraints = {
        @UniqueConstraint(name = "UX__inventory__book_id", columnNames = {"book_id"})
})
public class Inventory extends AbstractEntity {

    @OneToOne
    @JoinColumn(name = "book_id", nullable = false, foreignKey = @ForeignKey(name = "FK__inventory__book_id"))
    private Book book;

    @Column(name = "stock", nullable = false)
    private Integer stock;
}
