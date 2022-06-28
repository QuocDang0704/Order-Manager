package fis.quocdb3.ordermanager.controller;

import fis.quocdb3.ordermanager.domain.OrderStatus;
import fis.quocdb3.ordermanager.dto.order.CreateOrderDTO;
import fis.quocdb3.ordermanager.dto.order.CreateOrderItemDTO;
import fis.quocdb3.ordermanager.dto.order.OrderDTO;
import fis.quocdb3.ordermanager.dto.order.RemoveOrderItemDTO;
import fis.quocdb3.ordermanager.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    @GetMapping("/{pageNumber}/{pageSize}")
    public ResponseEntity<Page<OrderDTO>> findAll(
            @PathVariable(name = "pageNumber") Integer pageNumber,
            @PathVariable(name = "pageSize") Integer pageSize) {
        return ResponseEntity.ok().body(orderService.findAll(PageRequest.of(pageNumber, pageSize)));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> findById(@PathVariable(name = "orderId") Long orderId) {
        return ResponseEntity.ok(orderService.findById(orderId));
    }

    @PutMapping("/")
    public ResponseEntity<OrderDTO> create(@RequestBody CreateOrderDTO createOrderDTO) {
        return ResponseEntity.ok(orderService.create(createOrderDTO));
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<OrderDTO> delete(@PathVariable(name = "orderId") Long orderId) {
        orderService.delete(orderId);
        return ResponseEntity.ok(null);
    }

    @PostMapping("/addOrderItem")
    public ResponseEntity<OrderDTO> addOrderItem(@RequestBody CreateOrderItemDTO createOrderItemDTO) {
        return ResponseEntity.ok(orderService.addOrderItem(createOrderItemDTO));
    }

    @PostMapping("/removeOrderItem")
    public ResponseEntity<OrderDTO> removeOrderItem(@RequestBody RemoveOrderItemDTO removeOrderItemDTO) {
        return ResponseEntity.ok(orderService.removeOrderItem(removeOrderItemDTO));
    }

    @PostMapping("/paid/{orderId}")
    public ResponseEntity<OrderDTO> paid(@PathVariable(name = "orderId") Long orderId) {
        return ResponseEntity.ok(orderService.updateStatus(orderId, OrderStatus.PAID));
    }
    @PostMapping("/cancel/{orderId}")
    public ResponseEntity<OrderDTO> cancel(@PathVariable(name = "orderId") Long orderId) {
        return ResponseEntity.ok(orderService.updateStatus(orderId, OrderStatus.CANCELLED));
    }
}
