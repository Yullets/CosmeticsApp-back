package com.cosmetics.app.controller;

import com.cosmetics.app.entity.Cart;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Продукт в корзине")
public interface CartItemController {

    ResponseEntity<Void> addItemToCart(@RequestParam Long productId,
                                       @RequestParam int quantity);

    ResponseEntity<Void> removeItemFromCart(@RequestParam Long productId);

    ResponseEntity<Void> updateItemQuantity(@RequestParam Long productId,
                                            @RequestParam int quantity);
}
