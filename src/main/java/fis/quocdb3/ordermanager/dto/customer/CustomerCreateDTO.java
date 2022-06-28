package fis.quocdb3.ordermanager.dto.customer;

import fis.quocdb3.ordermanager.domain.Customer;
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

    public static class Mapper {
        public static CustomerCreateDTO fromEntity (Customer customer) {
            return CustomerCreateDTO.builder()
                    .name(customer.getName())
                    .mobile(customer.getMobile())
                    .address(customer.getAddress())
                    .build();
        }
    }
}
