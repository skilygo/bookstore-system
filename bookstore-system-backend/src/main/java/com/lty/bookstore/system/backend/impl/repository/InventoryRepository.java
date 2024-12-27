package com.lty.bookstore.system.backend.impl.repository;

import com.lty.bookstore.system.backend.impl.domain.entity.Book;
import com.lty.bookstore.system.backend.impl.domain.entity.Inventory;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends PagingAndSortingRepository<Inventory, Long>, QuerydslPredicateExecutor<Inventory>, JpaSpecificationExecutor<Inventory> {

    Optional<Inventory> findByBook(Book book);
}
