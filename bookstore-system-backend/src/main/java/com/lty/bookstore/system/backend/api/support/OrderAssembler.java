package com.lty.bookstore.system.backend.api.support;

import com.lty.bookstore.system.backend.api.message.CreateOrderRequest;
import com.lty.bookstore.system.backend.api.message.GetOrderResponse;
import com.lty.bookstore.system.backend.api.message.UpdateOrderRequest;
import com.lty.bookstore.system.backend.application.command.OrderCommand;
import com.lty.bookstore.system.backend.impl.domain.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class OrderAssembler {
    public abstract OrderCommand toOrderCommand(CreateOrderRequest updateOrderRequest);

    public abstract OrderCommand toOrderCommand(UpdateOrderRequest updateOrderRequest);

    public abstract GetOrderResponse toOrderResponse(Order order);

    public abstract Order toOrder(OrderCommand orderCommand);
}
