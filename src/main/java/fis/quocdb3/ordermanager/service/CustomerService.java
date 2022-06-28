package fis.quocdb3.ordermanager.service;

import fis.quocdb3.ordermanager.domain.Customer;
import fis.quocdb3.ordermanager.dto.customer.CustomerCreateDTO;
import fis.quocdb3.ordermanager.dto.customer.CustomerDTO;
import fis.quocdb3.ordermanager.dto.customer.CustomerUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerService {
    Page<CustomerDTO> findAll(Pageable pageable);
    CustomerDTO findById(Long id);
    CustomerDTO create(CustomerCreateDTO customer);
    CustomerDTO update(CustomerUpdateDTO customer);
    void delete(Long id);
    List<CustomerDTO> findAll();

}
