package fis.quocdb3.ordermanager.repository;

import fis.quocdb3.ordermanager.domain.Order;
import fis.quocdb3.ordermanager.domain.OrderStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderRepositoryTest {
    @Autowired
    OrderRepository orderRepository;

    @Test
    void updateStatusById() {
        Order order = orderRepository.findById(5L).get();
        orderRepository.updateStatusById(OrderStatus.CANCELLED, 5L);
        assertEquals(OrderStatus.CANCELLED.toString(), orderRepository.findById(5L).get().getStatus().toString());
    }
}