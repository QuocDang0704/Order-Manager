package fis.quocdb3.ordermanager.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrderItemOnCreateOrderDTO {
    private Long productId;
    private Integer quantity;
}
