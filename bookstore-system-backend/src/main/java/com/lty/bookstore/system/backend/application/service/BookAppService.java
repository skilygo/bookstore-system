package com.lty.bookstore.system.backend.application.service;

import cn.hutool.core.util.IdUtil;
import com.lty.bookstore.system.backend.api.support.BookAssembler;
import com.lty.bookstore.system.backend.application.command.BookCommand;
import com.lty.bookstore.system.backend.impl.domain.entity.Book;
import com.lty.bookstore.system.backend.impl.domain.entity.Inventory;
import com.lty.bookstore.system.backend.impl.repository.BookRepository;
import com.lty.bookstore.system.backend.impl.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookAppService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private BookAssembler bookAssembler;

    @Transactional
    public Book createBook(BookCommand book) {
        book.setBookId(IdUtil.getSnowflake().nextIdStr());
        Book saved = bookRepository.save(bookAssembler.toBookEntity(book));
        initializeInventory(saved, book.getStock());
        return saved;
    }

    public void initializeInventory(Book book, Integer initialStock) {
        Inventory inventory = new Inventory();
        inventory.setBook(book);
        inventory.setStock(initialStock);
        inventoryRepository.save(inventory);
    }

    public Book getBook(String bookId) {
        return bookRepository.findByBookId(bookId).orElse(null);
    }

    public void updateBook(BookCommand book) {
        Book bookEntity = bookRepository.findByBookId(book.getBookId())
                .orElseThrow(() -> new IllegalArgumentException("Book not found with bookId: " + book.getBookId()));
        bookRepository.save(bookAssembler.toUpdateBook(bookEntity, book));
    }

    public void deleteBook(String bookId) {
        Book bookEntity = bookRepository.findByBookId(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found with bookId: " + bookId));
        Inventory inventory = inventoryRepository.findByBook(bookEntity)
                .orElseThrow(() -> new IllegalArgumentException("Book not found with bookId: " + bookId));
        inventoryRepository.delete(inventory);
        bookRepository.delete(bookEntity);
    }

    public Page<Book> listBooks(int page, int size, List<String> bookIds) {
        return bookRepository.listBooks(page, size, bookIds);
    }
}
