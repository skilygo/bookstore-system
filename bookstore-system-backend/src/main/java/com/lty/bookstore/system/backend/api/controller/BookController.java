package com.lty.bookstore.system.backend.api.controller;

import com.lty.bookstore.system.backend.api.message.CreateBookRequest;
import com.lty.bookstore.system.backend.api.message.GetBookReponse;
import com.lty.bookstore.system.backend.api.message.UpdateBookRequest;
import com.lty.bookstore.system.backend.api.support.BookAssembler;
import com.lty.bookstore.system.backend.application.service.BookAppService;
import com.lty.bookstore.system.backend.base.Response;
import com.lty.bookstore.system.backend.impl.domain.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookAppService bookAppService;

    @Autowired
    private BookAssembler bookAssembler;

    /**
     * create book
     */
    @PostMapping
    public Response<?> createBook(@RequestBody CreateBookRequest request) {
        Book book = bookAppService.createBook(bookAssembler.toBookCommand(request));
        return Response.success(book.getBookId());
    }

    /**
     * retrieve book
     */
    @GetMapping("/{bookId}")
    public Response<GetBookReponse> getBook(@PathVariable String bookId) {
        Book book = bookAppService.getBook(bookId);
        return Response.success(bookAssembler.toBookResponseWithStock(book));
    }

    /**
     * ppdate book
     */
    @PutMapping
    public Response<?> updateBook(@RequestBody UpdateBookRequest book) {
        bookAppService.updateBook(bookAssembler.toBookCommand(book));
        return Response.success();
    }

    /**
     * delete book
     */
    @DeleteMapping("/{bookId}")
    public Response<?> deleteBook(@PathVariable String bookId) {
        bookAppService.deleteBook(bookId);
        return Response.success();
    }

    /**
     * list books
     */
    @GetMapping
    public Response<Page<GetBookReponse>> listBooks(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size,
                                                    @RequestParam(required = false) List<String> bookIds) {
        Page<Book> books = bookAppService.listBooks(page, size, bookIds);
        return Response.success(bookAssembler.toListBookResponse(books));
    }
}
