package fis.quocdb3.ordermanager.dto.order;

import fis.quocdb3.ordermanager.domain.OrderStatus;
import fis.quocdb3.ordermanager.utils.DateFormatter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UpdateOrderDTO {
    private Long id;
    @DateTimeFormat(pattern = DateFormatter.DATE_FORMAT)
    private LocalDateTime orderDateTime;
    private Long idCustomer;
    private Double totalAmount;
    private OrderStatus status;
}
