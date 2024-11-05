package com.cosmetics.app.service.impl;

import com.cosmetics.app.entity.Cart;
import com.cosmetics.app.entity.CartItem;
import com.cosmetics.app.entity.Order;
import com.cosmetics.app.entity.OrderItem;
import com.cosmetics.app.entity.Product;
import com.cosmetics.app.entity.User;
import com.cosmetics.app.exception.NotFoundException;
import com.cosmetics.app.model.CartDto;
import com.cosmetics.app.model.OrderDto;
import com.cosmetics.app.repository.OrderItemRepository;
import com.cosmetics.app.repository.OrderRepository;
import com.cosmetics.app.service.CartService;
import com.cosmetics.app.service.OrderService;
import com.cosmetics.app.service.UserService;
import com.cosmetics.app.service.mapper.CartMapper;
import com.cosmetics.app.service.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final CartService cartService;
    private final UserService userService;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartMapper cartMapper;
    private final OrderMapper orderMapper;

    //TODO: exception
    @Override
    @Transactional
    public OrderDto checkout() {
        CartDto cartDto = cartService.getOrCreateCart();
        Cart cart = cartMapper.toEntity(cartDto);
        if (cart.getCartItems().isEmpty()) {
            throw new NotFoundException("Cart is empty");
        }

        User user = userService.getCurrentUser();
        Order order = new Order();
        order.setUser(user);
        order.setCreatedAt(LocalDateTime.now());

        List<OrderItem> orderItems = new ArrayList<>();
        int totalPrice = 0;

        for (CartItem cartItem : cart.getCartItems()) {
            Product product = cartItem.getProduct();
            int itemTotal = cartItem.getQuantity() * product.getPrice();
            totalPrice += itemTotal;

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPriceAtOrder(product.getPrice());

            orderItems.add(orderItem);
        }

        order.setTotalPrice(totalPrice);
        order.setOrderItems(orderItems);
        orderRepository.save(order);
        orderItemRepository.saveAll(orderItems);

        cart.getCartItems().clear();

        return orderMapper.toDto(order);
    }
}
