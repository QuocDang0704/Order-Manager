package fis.quocdb3.ordermanager.dto.customer;

import fis.quocdb3.ordermanager.domain.Customer;
import fis.quocdb3.ordermanager.domain.Role;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerCreateDTO {
    private String name;
    private String mobile;
    private String address;
    private String username;
    private String password;
    private Role role;

    public static class Mapper {
        public static CustomerCreateDTO fromEntity (Customer customer) {
            return CustomerCreateDTO.builder()
                    .name(customer.getName())
                    .mobile(customer.getMobile())
                    .address(customer.getAddress())
                    .username(customer.getUsername())
                    .password(customer.getPassword())
                    .role(customer.getRole())
                    .build();
        }
    }
}
