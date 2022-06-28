package fis.quocdb3.ordermanager.service;

import fis.quocdb3.ordermanager.domain.OrderStatus;
import fis.quocdb3.ordermanager.dto.order.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import fis.quocdb3.ordermanager.dto.QueryOrderDTO;
import fis.quocdb3.ordermanager.domain.Order;

import java.util.List;

public interface OrderService {
    Page<OrderDTO> findAll(Pageable pageable);

    OrderDTO findById(Long orderId);

    OrderDTO create(CreateOrderDTO createOrderDTO);

    void delete(Long id);

    OrderDTO addOrderItem(CreateOrderItemDTO createOrderItemDTO);

    OrderDTO removeOrderItem(RemoveOrderItemDTO removeOrderItemDTO);

    OrderDTO updateStatus(Long id, OrderStatus status);
}
