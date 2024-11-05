package com.cosmetics.app.service.mapper;

import com.cosmetics.app.entity.Cart;
import com.cosmetics.app.model.CartDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = CartItemMapper.class)
public interface CartMapper {

    CartDto toDto(Cart cart);

    Cart toEntity(CartDto cartDto);

    List<CartDto> toListDto(List<Cart> carts);
}
