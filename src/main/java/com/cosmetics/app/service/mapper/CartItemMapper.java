package com.cosmetics.app.service.mapper;

import com.cosmetics.app.entity.CartItem;
import com.cosmetics.app.model.CartItemDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = ProductMapper.class)
public interface CartItemMapper {

    CartItemDto toDto(CartItem cartItem);

    List<CartItemDto> toListDto(List<CartItem> cartItems);
}
