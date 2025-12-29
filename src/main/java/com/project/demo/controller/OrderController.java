package com.project.demo.controller;

import com.project.demo.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/doanh")
    public String health() {
        return "OK doanh oi có tiền rồi";
    }

    @PostMapping
    public ResponseEntity<String> order(
            @RequestParam(name = "productId") Long productId,
            @RequestParam(name = "quantity") int quantity) {

        orderService.placeOrder(productId, quantity);
        return ResponseEntity.ok("OK created");
    }
}
