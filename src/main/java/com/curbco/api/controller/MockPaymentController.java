package com.curbco.api.controller;

import com.curbco.api.dto.OrderResponse;
import com.curbco.api.model.User;
import com.curbco.api.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * Pasarela SIMULADA para desarrollo. Reemplaza al webhook real de Wompi.
 * Solo existe si payments.mock.enabled=true. En producción debe estar apagada.
 */
@RestController
@RequestMapping("/api/payments/mock")
@RequiredArgsConstructor
@ConditionalOnProperty(name = "payments.mock.enabled", havingValue = "true")
public class MockPaymentController {

    private final OrderService orderService;

    @PostMapping("/{reference}")
    public OrderResponse pay(@AuthenticationPrincipal User user,
                             @PathVariable String reference,
                             @RequestParam boolean approved) {
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        return OrderResponse.from(orderService.applyPaymentResult(reference, user, approved));
    }
}