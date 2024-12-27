package com.lty.bookstore.system.backend.impl.domain.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldNameConstants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@FieldNameConstants
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(name = "UX__user__user_id", columnNames = {"user_id"})
})
public class User extends AbstractEntity {
    @Column(name = "user_id", length = 128, nullable = false)
    private String userId;
    @Column(name = "user_name", nullable = false, length = 32)
    private String userName;
    @Column(name = "phone", nullable = false, length = 32)
    private String phone;

}
