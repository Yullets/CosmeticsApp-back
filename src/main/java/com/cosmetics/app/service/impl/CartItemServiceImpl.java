package com.cosmetics.app.service.impl;

import com.cosmetics.app.entity.Cart;
import com.cosmetics.app.entity.CartItem;
import com.cosmetics.app.entity.Product;
import com.cosmetics.app.exception.NotFoundException;
import com.cosmetics.app.model.CartDto;
import com.cosmetics.app.repository.CartItemRepository;
import com.cosmetics.app.repository.ProductRepository;
import com.cosmetics.app.service.CartItemService;
import com.cosmetics.app.service.CartService;
import com.cosmetics.app.service.mapper.CartMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final CartService cartService;
    private final ProductRepository productRepository;
    private final CartMapper cartMapper;

    @Override
    public void addItemToCart(Long productId, int quantity) {
        CartDto cartDto = cartService.getOrCreateCart();
        Cart cart = cartMapper.toEntity(cartDto);
        Product product = productRepository.findById(productId)
                .orElseThrow();

        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);

        if (cartItem == null) {
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cart.getCartItems().add(cartItem);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }

        cartItemRepository.save(cartItem);
    }

    @Override
    public void removeItemFromCart(Long productId) {
        CartDto cartDto = cartService.getOrCreateCart();
        Cart cart = cartMapper.toEntity(cartDto);
        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Товар с ID " + productId + " не найден в корзине."));
        cartItemRepository.delete(cartItem);
    }

    @Override
    public void updateItemQuantity(Long productId, int quantity) {
        if (quantity < 1) {
            throw new IllegalArgumentException("Quantity must be at least 1");
        }

        CartDto cartDto = cartService.getOrCreateCart();
        Cart cart = cartMapper.toEntity(cartDto);
        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Товар с ID " + productId + " не найден в корзине."));
        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);
    }
}
