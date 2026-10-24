package com.curbco.api.dto;

import com.curbco.api.model.Order;

import java.time.LocalDateTime;

public record OrderResponse(
        Long id,
        String reference,
        String status,
        Long totalCents,
        LocalDateTime createdAt
) {
    public static OrderResponse from(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getReference(),
                order.getStatus().name(),
                order.getTotalCents(),
                order.getCreatedAt()
        );
    }
}