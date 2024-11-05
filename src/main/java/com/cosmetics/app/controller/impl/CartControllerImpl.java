package com.cosmetics.app.controller.impl;

import com.cosmetics.app.controller.CartController;
import com.cosmetics.app.model.CartDto;
import com.cosmetics.app.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartControllerImpl implements CartController {

    private final CartService cartService;

    @Override
    @GetMapping
    public ResponseEntity<CartDto> getOrCreateCart() {
        return ResponseEntity.ok(cartService.getOrCreateCart());
    }
}
