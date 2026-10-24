package com.curbco.api.repository;

import com.curbco.api.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByReference(String reference);

    List<Order> findAllByUserIdOrderByCreatedAtDesc(Long userId);
}