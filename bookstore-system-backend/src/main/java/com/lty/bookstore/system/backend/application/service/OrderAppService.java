package com.lty.bookstore.system.backend.application.service;

import cn.hutool.core.util.IdUtil;
import com.lty.bookstore.system.backend.api.support.OrderAssembler;
import com.lty.bookstore.system.backend.application.command.OrderCommand;
import com.lty.bookstore.system.backend.impl.domain.OrderStatus;
import com.lty.bookstore.system.backend.impl.domain.entity.Book;
import com.lty.bookstore.system.backend.impl.domain.entity.Order;
import com.lty.bookstore.system.backend.impl.domain.entity.User;
import com.lty.bookstore.system.backend.impl.repository.BookRepository;
import com.lty.bookstore.system.backend.impl.repository.OrderRepository;
import com.lty.bookstore.system.backend.impl.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderAppService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderAssembler orderAssembler;

    @Autowired
    private InventoryAppService inventoryAppService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Order createOrder(OrderCommand orderCommand) {
        User user = userRepository.findByUserId(orderCommand.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User is not found."));
        Book book = bookRepository.findByBookId(orderCommand.getBookId())
                .orElseThrow(() -> new IllegalArgumentException("The book is not found."));
        if (inventoryAppService.getStock(orderCommand.getBookId()) < orderCommand.getAmount()) {
            throw new IllegalStateException("The inventory is not enough.");
        }
        orderCommand.setOrderId(IdUtil.getSnowflake().nextIdStr());
        Order order = orderAssembler.toOrder(orderCommand);
        order.setUser(user);
        order.setBook(book);
        order.setStatus(OrderStatus.Paid);
        orderRepository.save(order);
        inventoryAppService.updateStock(book.getBookId(), -(orderCommand.getAmount()));
        return order;
    }

    public Order getOrderStatus(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() ->
                new IllegalArgumentException("The order is not found"));
    }

    @Transactional
    public void cancelOrder(Long orderId, OrderCommand orderCommand) {
        Order order = orderRepository.findById(orderId).orElseThrow(() ->
                new IllegalArgumentException("The order is not found，unable to cancel order"));
        order.setStatus(OrderStatus.Cancelled);
        orderRepository.save(order);
        inventoryAppService.updateStock(order.getBook().getBookId(), orderCommand.getAmount());
    }
}
