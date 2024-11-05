package com.cosmetics.app.service;

public interface CartItemService {

    void addItemToCart(Long productId, int quantity);

    void removeItemFromCart(Long productId);

    void updateItemQuantity(Long productId, int quantity);
}
