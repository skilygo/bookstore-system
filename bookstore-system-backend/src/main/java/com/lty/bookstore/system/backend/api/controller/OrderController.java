package com.lty.bookstore.system.backend.api.controller;

import com.lty.bookstore.system.backend.api.message.CreateOrderRequest;
import com.lty.bookstore.system.backend.api.message.GetOrderResponse;
import com.lty.bookstore.system.backend.api.message.UpdateOrderRequest;
import com.lty.bookstore.system.backend.api.support.OrderAssembler;
import com.lty.bookstore.system.backend.application.service.OrderAppService;
import com.lty.bookstore.system.backend.base.Response;
import com.lty.bookstore.system.backend.impl.domain.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderAppService orderAppService;

    @Autowired
    private OrderAssembler orderAssembler;

    /**
     * create order
     */
    @PostMapping
    public Response<?> createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
        Order order = orderAppService.createOrder(orderAssembler.toOrderCommand(createOrderRequest));
        return Response.success(order.getOrderId());
    }

    /**
     * retrieve order
     */
    @GetMapping("/{orderId}")
    public Response<GetOrderResponse> getOrderStatus(@PathVariable Long orderId) {
        Order order = orderAppService.getOrderStatus(orderId);
        return Response.success(orderAssembler.toOrderResponse(order));
    }

    /**
     * update order
     */
    @PutMapping("/{orderId}")
    public Response<?> cancerOrder(@PathVariable Long orderId, @RequestBody UpdateOrderRequest updateOrderRequest) {
        orderAppService.cancelOrder(orderId, orderAssembler.toOrderCommand(updateOrderRequest));
        return Response.success();
    }
}
