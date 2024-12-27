package com.lty.bookstore.system.backend;

import com.lty.bookstore.system.backend.application.command.OrderCommand;
import com.lty.bookstore.system.backend.application.service.InventoryAppService;
import com.lty.bookstore.system.backend.application.service.OrderAppService;
import com.lty.bookstore.system.backend.impl.domain.OrderStatus;
import com.lty.bookstore.system.backend.impl.domain.entity.Book;
import com.lty.bookstore.system.backend.impl.domain.entity.Order;
import com.lty.bookstore.system.backend.impl.domain.entity.User;
import com.lty.bookstore.system.backend.impl.repository.BookRepository;
import com.lty.bookstore.system.backend.impl.repository.OrderRepository;
import com.lty.bookstore.system.backend.impl.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class OrderAppServiceTest {

    @Autowired
    private OrderAppService orderAppService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InventoryAppService inventoryAppService;

    private OrderCommand orderCommand;
    private User user;
    private Book book;
    private Order order;

    @BeforeEach
    void setUp() {
        // Prepare mock data
        orderCommand = OrderCommand.builder()
                .userId("1872309384399450112")
                .bookId("1872318257076248576")
                .amount(2)
                .totalPrice(BigDecimal.valueOf(40))
                .build();
    }

    @Test
    @Transactional
    void createOrder_ShouldCreateOrderSuccessfully() {
        // Mock the repositories and services
//        when(userRepository.findByUserId(orderCommand.getUserId())).thenReturn(Optional.of(user));
//        when(bookRepository.findByBookId(orderCommand.getBookId())).thenReturn(Optional.of(book));
//        when(inventoryAppService.getStock(orderCommand.getBookId())).thenReturn(100);  // enough stock

        // Call the method
        Order result = orderAppService.createOrder(orderCommand);

        // Verify the order was saved in the repository
        Optional<Order> savedOrder = orderRepository.findByOrderId(result.getOrderId());
        assertTrue(savedOrder.isPresent());
        assertEquals(OrderStatus.Pending, savedOrder.get().getStatus());
    }
}
