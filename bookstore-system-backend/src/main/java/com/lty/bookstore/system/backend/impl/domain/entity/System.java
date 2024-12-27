package com.lty.bookstore.system.backend.impl.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "pms", uniqueConstraints = {
        @UniqueConstraint(name = "UX__pms__system_id", columnNames = {"system_id"}),
        @UniqueConstraint(name = "UX__pms__client_id", columnNames = {"client_id"})
})
public class System extends AbstractEntity {

    @Column(name = "system_id", nullable = false, length = 64)
    private String systemId;

    @Column(name = "name", nullable = false, length = 128)
    private String name;

    @Column(name = "client_id", nullable = false, length = 64)
    private String clientId;

    @Column(name = "client_secret", nullable = false, length = 128)
    private String clientSecret;

}
