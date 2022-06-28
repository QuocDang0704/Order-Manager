package fis.quocdb3.ordermanager.dto.order;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RemoveOrderItemDTO {
    private Long orderId;
    private Long orderItemId;

}
