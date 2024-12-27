package com.lty.bookstore.system.backend.impl.repository;

import com.lty.bookstore.system.backend.impl.domain.entity.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long>, QuerydslPredicateExecutor<User>, JpaSpecificationExecutor<User> {

    User getUserByPhone(String userPhone);

    Optional<User> findByUserId(String userId);
}
