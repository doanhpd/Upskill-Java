package com.project.demo.service;

import com.project.demo.entity.Order;
import com.project.demo.entity.Product;
import com.project.demo.repository.OrderRepository;
import com.project.demo.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public OrderService(ProductRepository productRepository,
                        OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public void placeOrder(Long productId, int quantity) {

        try {
            Thread.sleep(5000); // 👈 giữ lại để test race
        } catch (InterruptedException ignored) {}

        // ✅ ATOMIC UPDATE
        int updated = productRepository.decreaseStock(productId, quantity);

        if (updated == 0) {
            throw new RuntimeException("Not enough stock");
        }

        // ✅ chỉ tạo order khi stock đã trừ thành công
        Order order = new Order();
        order.setProductId(productId);
        order.setQuantity(quantity);
        order.setCreatedAt(LocalDateTime.now());

        orderRepository.save(order);
    }
}


