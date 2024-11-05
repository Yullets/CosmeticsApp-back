package com.cosmetics.app.service.mapper;

import com.cosmetics.app.entity.OrderItem;
import com.cosmetics.app.model.OrderItemDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    OrderItemDto toDto(OrderItem orderItem);

    List<OrderItemDto> toListDto(List<OrderItem> orderItems);
}
