package com.lty.bookstore.system.backend.impl.repository;

import com.lty.bookstore.system.backend.impl.domain.entity.System;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ApiCredentialRepository extends PagingAndSortingRepository<System, Long> {

    Optional<System> findByClientId(String clientId);

}
