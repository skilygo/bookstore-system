package com.lty.bookstore.system.backend.impl.repository;

import com.lty.bookstore.system.backend.impl.domain.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends PagingAndSortingRepository<Book, Long>, QuerydslPredicateExecutor<Book>, JpaSpecificationExecutor<Book> {
    Optional<Book> findByBookId(String bookId);

    default Page<Book> listBooks(int page, int size, List<String> bookIds) {
        Pageable pageable = PageRequest.of(page, size);
        Specification<Book> spec = Specification.where(null);
        if (!CollectionUtils.isEmpty(bookIds)) {
            spec = spec.and((root, query, criteriaBuilder) -> root.get("bookId").in(bookIds));
        }
        return this.findAll(spec, pageable);
    }
}
