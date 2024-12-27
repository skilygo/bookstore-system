package com.lty.bookstore.system.backend.application.service;

import com.lty.bookstore.system.backend.config.RedissonConfig;
import com.lty.bookstore.system.backend.impl.domain.entity.Book;
import com.lty.bookstore.system.backend.impl.domain.entity.Inventory;
import com.lty.bookstore.system.backend.impl.repository.BookRepository;
import com.lty.bookstore.system.backend.impl.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryAppService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private RedissonConfig.RedissonService redissonService;

    @Transactional
    public void updateStock(String bookId, int quantityChange) {
        redissonService.lock(getLockName(bookId), () -> {
            Book book = bookRepository.findByBookId(bookId).orElseThrow(() ->
                    new IllegalArgumentException("Book not found, unable to update stock!"));
            Inventory inventory = inventoryRepository.findByBook(book).orElseThrow(() ->
                    new IllegalArgumentException("Inventory information not found, unable to update stock!"));
            int newStock = inventory.getStock() + quantityChange;
            if (newStock < 0) {
                throw new IllegalArgumentException("The inventory is not enough, unable to update!");
            }
            inventory.setStock(newStock);
            inventoryRepository.save(inventory);
        });
    }

    private String getLockName(String bookId) {
        return "BOOKSTORE:INVENTORY_UPDATE_BOOK_STOCK:" + bookId;
    }

    public int getStock(String bookId) {
        Book book = bookRepository.findByBookId(bookId).orElseThrow(() ->
                new IllegalArgumentException("Book not found, unable to retrieve stock!"));
        Inventory inventory = inventoryRepository.findByBook(book).orElseThrow(() ->
                new IllegalArgumentException("Inventory is not found, unable to retrieve stock!"));
        return inventory.getStock();
    }
}
