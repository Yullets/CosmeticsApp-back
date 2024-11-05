package com.cosmetics.app.service;

import com.cosmetics.app.model.CartDto;

public interface CartService {

    CartDto getOrCreateCart();
}
