package com.cosmetics.app.service.impl;

import com.cosmetics.app.entity.Cart;
import com.cosmetics.app.entity.User;
import com.cosmetics.app.model.CartDto;
import com.cosmetics.app.repository.CartRepository;
import com.cosmetics.app.service.CartService;
import com.cosmetics.app.service.UserService;
import com.cosmetics.app.service.mapper.CartMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final UserService userService;
    private final CartMapper cartMapper;

    @Override
    public CartDto getOrCreateCart() {
        User user = userService.getCurrentUser();
        Cart cart = user.getCart();

        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cart = cartRepository.save(cart);
        }

        return cartMapper.toDto(cart);
    }
}
