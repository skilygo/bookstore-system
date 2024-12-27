package com.lty.bookstore.system.backend.impl.domain.entity;

import lombok.Data;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;


@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@FieldNameConstants
@Data
public class AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    private Date createdDate;
    @LastModifiedDate
    @Column(name = "last_modified_date", nullable = false)
    private Date lastModifiedDate;
}
