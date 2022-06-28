package fis.quocdb3.ordermanager.dto.customer;

import fis.quocdb3.ordermanager.domain.Customer;
import fis.quocdb3.ordermanager.domain.Order;
import fis.quocdb3.ordermanager.domain.Role;
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
    private String username;
    private String password;
    private Role role;

    public static class Mapper {
        public static CustomerDTO fromEntity (Customer customer) {
            return CustomerDTO.builder().id(customer.getId())
                    .name(customer.getName())
                    .mobile(customer.getMobile())
                    .address(customer.getAddress())
                    .orders(customer.getOrders())
                    .username(customer.getUsername())
                    .password(customer.getPassword())
                    .role(customer.getRole())
                    .build();
        }
    }
}
