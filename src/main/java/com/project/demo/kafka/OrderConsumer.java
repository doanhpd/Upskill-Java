package com.project.demo.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class OrderConsumer {
    // Đảm bảo idempotency bằng cache đơn giản (có thể thay bằng Redis/DB cho production)
    private final Set<String> processedOrderIds = ConcurrentHashMap.newKeySet();

    @KafkaListener(topics = "order-topic", groupId = "demo-group")
    public void listen(ConsumerRecord<String, String> record) {
        String orderId = record.key();
        if (processedOrderIds.contains(orderId)) {
            // Đã xử lý rồi, bỏ qua
            return;
        }
        // Đánh dấu đã xử lý
        processedOrderIds.add(orderId);
        // Xử lý message
        System.out.println("Received order event: " + record.value());
        // TODO: Thực hiện logic xử lý đơn hàng ở đây
    }
}
