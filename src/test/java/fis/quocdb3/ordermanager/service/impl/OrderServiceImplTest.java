package fis.quocdb3.ordermanager.service.impl;

import fis.quocdb3.ordermanager.domain.*;
import fis.quocdb3.ordermanager.dto.order.OrderDTO;
import fis.quocdb3.ordermanager.repository.OrderRepository;
import fis.quocdb3.ordermanager.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@Slf4j
class OrderServiceImplTest {
    private OrderServiceImpl orderService;
    private CustomerService customerService;

    private OrderRepository orderRepository;
    private Order order;
    private Product product;
    private Customer customer;
    private Pageable pageable;

    @BeforeEach
    void init() {
        pageable = mock(Pageable.class);
        orderRepository = mock(OrderRepository.class);
        customerService = mock(CustomerService.class);

        orderService = new OrderServiceImpl();
        customer = Customer.builder()
                .id(1L)
                .name("Đặng Bảo Quốc")
                .mobile("0372087588")
                .address("Nam Định")
                .username("quocdb3")
                .password("123")
                .role(Role.ADMIN)
                .build();
        product = Product.builder()
                .id(1L)
                .name("tra sua")
                .avaiable(9L)
                .price(10.0)
                .build();
        OrderItem orderItem = OrderItem.builder()
                .id(1L)
                .amount(16000.0)
                .quantity(3)
                .product(product)
                .build();
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);
        order = Order.builder()
                .id(1L)
                .customer(customer)
                .totalAmount(1000.0)
                .status(OrderStatus.CREATED)
                .orderDateTime(LocalDateTime.now())
                .orderItems(orderItems)
                .build();
    }
    @Test
    void findAll() {
        List<Order> orderList = new ArrayList<>();
        orderList.add(order);
        Page<Order> orders = new PageImpl<>(orderList);

        when(orderRepository.findAll(pageable)).thenReturn(orders);
        log.info("Order List size: {}", orderList.size());
        Page<OrderDTO> orderDTOPage = orderService.findAll(pageable);
        assertEquals(orderDTOPage.getTotalElements(), orders.getTotalElements());
    }

    @Test
    void findById() {
        when(orderRepository.findById(1L)).thenReturn(Optional.ofNullable(order));
        OrderDTO detailOrderDTO = orderService.findById(1L);
        assertThat(detailOrderDTO).isNotNull();
        assertThat(detailOrderDTO.getTotalAmount()).isEqualTo(123);
        assertThat(detailOrderDTO.getStatus()).isEqualTo(OrderStatus.CREATED);
    }

    @Test
    void create() {

    }
}