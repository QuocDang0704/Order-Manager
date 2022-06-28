package fis.quocdb3.ordermanager.dto.order;

import fis.quocdb3.ordermanager.domain.Customer;
import fis.quocdb3.ordermanager.domain.OrderItem;
import fis.quocdb3.ordermanager.domain.OrderStatus;
import fis.quocdb3.ordermanager.utils.DateFormatter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CreateOrderDTO {
    private Long customerId;
    private List<OrderItemOnCreateOrderDTO> orderItems;
}
