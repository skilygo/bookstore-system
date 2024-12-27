package com.lty.bookstore.system.backend.impl.repository;

import com.lty.bookstore.system.backend.impl.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<Order, Long>, QuerydslPredicateExecutor<Order>, JpaSpecificationExecutor<Order> {
    Optional<Order> findByOrderId(String orderId);
}
