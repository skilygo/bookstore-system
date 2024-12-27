package com.lty.bookstore.system.backend.impl.domain.entity;


import com.lty.bookstore.system.backend.impl.domain.OrderStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldNameConstants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@FieldNameConstants
@Table(name = "orders", uniqueConstraints = {
})
public class Order extends AbstractEntity {
    @Column(name = "order_id", length = 128, nullable = false)
    private String orderId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "FK__order__user_id"))
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false, foreignKey = @ForeignKey(name = "FK__order__book_id"))
    private Book book;

    @Column(name = "amount", length = 128, nullable = false)
    private Integer amount;

    @Column(name = "total_price", length = 128, nullable = false)
    private BigDecimal totalPrice;

    @Column(name = "status", length = 128, nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
