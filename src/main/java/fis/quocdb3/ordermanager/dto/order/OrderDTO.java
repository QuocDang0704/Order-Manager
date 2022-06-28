package fis.quocdb3.ordermanager.dto.order;

import fis.quocdb3.ordermanager.domain.Customer;
import lombok.*;
import fis.quocdb3.ordermanager.domain.Order;
import fis.quocdb3.ordermanager.domain.OrderItem;
import fis.quocdb3.ordermanager.domain.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {

    private Long id;

    private LocalDateTime orderDateTime;

    private Customer customer;

    private List<OrderItem> orderItems;

    private Double totalAmount;

    private OrderStatus status;

    public static class Mapper{
        public static  OrderDTO fromEntity(Order order) {
            return OrderDTO.builder().id(order.getId())
                     .orderDateTime(order.getOrderDateTime())
                    .orderItems(order.getOrderItems())
                    .customer(order.getCustomer())
                    .totalAmount(order.getTotalAmount())
                    .status(order.getStatus())
                    .build();
        }
    }
}
