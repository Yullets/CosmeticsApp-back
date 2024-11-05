package com.cosmetics.app.controller.impl;

import com.cosmetics.app.controller.OrderController;
import com.cosmetics.app.entity.Order;
import com.cosmetics.app.model.OrderDto;
import com.cosmetics.app.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderControllerImpl implements OrderController {

    private final OrderService orderService;

    @Override
    @PostMapping
    public ResponseEntity<OrderDto> checkout() {
        return ResponseEntity.ok(orderService.checkout());
    }
}
