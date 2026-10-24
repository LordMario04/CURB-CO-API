package com.curbco.api.service;

import com.curbco.api.dto.CheckoutRequest;
import com.curbco.api.model.Order;
import com.curbco.api.model.OrderItem;
import com.curbco.api.model.OrderStatus;
import com.curbco.api.model.Product;
import com.curbco.api.model.User;
import com.curbco.api.repository.OrderRepository;
import com.curbco.api.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import com.curbco.api.dto.OrderResponse;
import java.util.List;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public Order createOrder(User user, CheckoutRequest request) {
        if (request == null || request.items() == null || request.items().isEmpty()) {
            throw bad("El carrito está vacío");
        }
        if (request.items().size() > 50) {
            throw bad("Demasiados productos en el carrito");
        }

        Order order = new Order();
        order.setUser(user);
        order.setReference("CURB-" + UUID.randomUUID().toString()
                .replace("-", "").substring(0, 16).toUpperCase());

        long total = 0;
        for (CheckoutRequest.Item line : request.items()) {
            if (line.productId() == null || line.quantity() < 1 || line.quantity() > 20) {
                throw bad("Cantidad inválida");
            }

            Product product = productRepository.findById(line.productId())
                    .orElseThrow(() -> bad("El producto " + line.productId() + " no existe"));

            if (product.getPriceCents() == null) {
                throw bad("El producto " + product.getTitle() + " no tiene precio");
            }

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProductId(product.getId());
            item.setTitle(product.getTitle());
            item.setUnitPriceCents(product.getPriceCents());
            item.setQuantity(line.quantity());
            order.getItems().add(item);

            total += product.getPriceCents() * line.quantity();
        }

        order.setTotalCents(total);
        return orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public Order getForUser(String reference, User user) {
        Order order = orderRepository.findByReference(reference)
                .orElseThrow(this::notFound);
        // Si la orden es de otro usuario respondemos 404, para no revelar que existe
        if (!order.getUser().getId().equals(user.getId())) {
            throw notFound();
        }
        return order;
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getAllForUser(User user) {
        return orderRepository
                .findAllByUserIdOrderByCreatedAtDesc(user.getId())
                .stream()
                .map(OrderResponse::from)
                .toList();
    }

    @Transactional
    public Order applyPaymentResult(String reference, User user, boolean approved) {
        Order order = getForUser(reference, user);
        if (order.getStatus() != OrderStatus.PENDING) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La orden ya fue procesada");
        }
        order.setStatus(approved ? OrderStatus.PAID : OrderStatus.REJECTED);
        return order;
    }

    private ResponseStatusException bad(String message) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
    }

    private ResponseStatusException notFound() {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, "Orden no encontrada");
    }
}