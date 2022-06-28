package fis.quocdb3.ordermanager.dto.customer;

import fis.quocdb3.ordermanager.domain.Customer;
import fis.quocdb3.ordermanager.domain.Order;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDTO {
    private Long id;
    private String name;
    private String mobile;
    private String address;
    private List<Order> orders;

    public static class Mapper {
        public static CustomerDTO fromEntity (Customer customer) {
            return CustomerDTO.builder().id(customer.getId())
                    .name(customer.getName())
                    .mobile(customer.getMobile())
                    .address(customer.getAddress())
                    .orders(customer.getOrders())
                    .build();
        }
    }
}
