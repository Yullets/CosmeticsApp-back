package com.cosmetics.app.controller.impl;

import com.cosmetics.app.controller.CartItemController;
import com.cosmetics.app.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart-item")
@RequiredArgsConstructor
public class CartItemControllerImpl implements CartItemController {

    private final CartItemService cartItemService;

    @Override
    @PostMapping()
    public ResponseEntity<Void> addItemToCart(@RequestParam Long productId, @RequestParam int quantity) {
        cartItemService.addItemToCart(productId, quantity);
        return ResponseEntity.ok().build();
    }

    @Override
    @DeleteMapping()
    public ResponseEntity<Void> removeItemFromCart(@RequestParam Long cartId) {
        cartItemService.removeItemFromCartByCartId(cartId);
        return ResponseEntity.ok().build();
    }

    @Override
    @PatchMapping()
    public ResponseEntity<Void> updateItemQuantity(@RequestParam Long productId, @RequestParam int quantity) {
        cartItemService.updateItemQuantity(productId, quantity);
        return ResponseEntity.ok().build();
    }
}
