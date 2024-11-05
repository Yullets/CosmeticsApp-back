package com.cosmetics.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private Long id;

    private int totalPrice;

    private LocalDateTime createdAt;

    private List<OrderItemDto> orderItems;
}
