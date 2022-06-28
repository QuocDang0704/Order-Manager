package fis.quocdb3.ordermanager.dto.customer;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class CustomerUpdateDTO {
    private Long id;
    private String name;
    private String mobile;
    private String address;
}
