package com.curbco.api.dto;

import java.util.List;

public record CheckoutRequest(List<Item> items) {
    public record Item(Long productId, int quantity) {}
}