package com.curbco.api.controller;

import com.curbco.api.dto.CheckoutRequest;
import com.curbco.api.dto.OrderResponse;
import com.curbco.api.model.User;
import com.curbco.api.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public OrderResponse create(@AuthenticationPrincipal User user,
                                @RequestBody CheckoutRequest request) {
        requireUser(user);
        return OrderResponse.from(orderService.createOrder(user, request));
    }

    @GetMapping("/{reference}")
    public OrderResponse get(@AuthenticationPrincipal User user,
                             @PathVariable String reference) {
        requireUser(user);
        return OrderResponse.from(orderService.getForUser(reference, user));
    }

    @GetMapping
    public List<OrderResponse> getAll(
            @AuthenticationPrincipal User user) {
        requireUser(user);
        return orderService.getAllForUser(user);
    }

    private void requireUser(User user) {
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }
}