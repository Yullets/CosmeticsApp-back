package com.cosmetics.app.service.mapper;

import com.cosmetics.app.entity.Order;
import com.cosmetics.app.model.OrderDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {OrderItemMapper.class})
public interface OrderMapper {

    OrderDto toDto(Order order);

    List<OrderDto> toListDto(List<Order> orders);
}
