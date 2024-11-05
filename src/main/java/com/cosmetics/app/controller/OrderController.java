package com.cosmetics.app.controller;

import com.cosmetics.app.model.OrderDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Заказ")
public interface OrderController {

    ResponseEntity<OrderDto> checkout();
}
