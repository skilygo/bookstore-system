package com.lty.bookstore.system.backend.impl.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldNameConstants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@FieldNameConstants
@Table(name = "user_book_interaction")
public class UserBookInteraction extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "FK__user_book_interaction__user_id"))
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false, foreignKey = @ForeignKey(name = "FK__user_book_interaction__book_id"))
    private Book book;

    @Column(name = "view_time", nullable = false)
    private Integer viewTime;
}
