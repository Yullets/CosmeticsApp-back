package com.cosmetics.app.controller;

import com.cosmetics.app.model.CartDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Корзина")
public interface CartController {

    ResponseEntity<CartDto> getOrCreateCart();
}
