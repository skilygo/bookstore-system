package com.lty.bookstore.system.backend.api.support;

import com.lty.bookstore.system.backend.api.message.CreateBookRequest;
import com.lty.bookstore.system.backend.api.message.GetBookReponse;
import com.lty.bookstore.system.backend.api.message.UpdateBookRequest;
import com.lty.bookstore.system.backend.application.command.BookCommand;
import com.lty.bookstore.system.backend.impl.domain.entity.Book;
import com.lty.bookstore.system.backend.impl.domain.entity.Inventory;
import com.lty.bookstore.system.backend.impl.repository.InventoryRepository;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class BookAssembler {

    @Autowired
    private InventoryRepository inventoryRepository;

    public abstract GetBookReponse toBookResponse(Book book);

    public GetBookReponse toBookResponseWithStock(Book book) {
        if (Objects.isNull(book)) {
            return null;
        }
        Inventory inventory = inventoryRepository.findByBook(book)
                .orElseThrow(() -> new IllegalStateException("Inventory is not found."));
        GetBookReponse result = toBookResponse(book);
        result.setStock(inventory.getStock());
        return result;
    }

    // 显式处理映射：手动迭代每个元素并映射
    public List<GetBookReponse> toListBookResponse(List<Book> books) {
        return books.stream()
                .map(this::toBookResponse)
                .collect(Collectors.toList());
    }

    public Page<GetBookReponse> toListBookResponse(Page<Book> books) {
        List<GetBookReponse> bookResponses = books.getContent().stream()
                .map(this::toBookResponse)
                .collect(Collectors.toList());
        return new PageImpl<>(bookResponses, books.getPageable(), books.getTotalElements());
    }

    public abstract BookCommand toBookCommand(CreateBookRequest book);

    public abstract BookCommand toBookCommand(UpdateBookRequest book);

    public abstract Book toBookEntity(Long bookId, BookCommand book);

    public abstract Book toBookEntity(BookCommand book);

    public Book toUpdateBook(Book bookEntity, BookCommand book) {
        bookEntity.setBookId(book.getBookId());
        bookEntity.setBookName(book.getBookName());
        bookEntity.setCategory(book.getCategory());
        bookEntity.setAuthor(book.getAuthor());
        bookEntity.setPrice(book.getPrice());
        return bookEntity;
    }
}
