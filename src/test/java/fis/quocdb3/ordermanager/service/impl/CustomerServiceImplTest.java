package fis.quocdb3.ordermanager.service.impl;

import fis.quocdb3.ordermanager.domain.Customer;
import fis.quocdb3.ordermanager.dto.customer.CustomerCreateDTO;
import fis.quocdb3.ordermanager.dto.customer.CustomerDTO;
import fis.quocdb3.ordermanager.repository.CustomerRepository;
import fis.quocdb3.ordermanager.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {
    @Autowired
    private CustomerService customerService;

    private CustomerRepository customerRepository;
    private Customer customer;
    private Pageable pageable;
    @BeforeEach
    void init() {
        pageable = mock(Pageable.class);
        customerRepository = mock(CustomerRepository.class);
        customerService = new CustomerServiceImpl();
        customer = Customer.builder().id(1L).name("Đặng Bảo Quốc").address("Nam Định").mobile("0372087588").build();
    }


    @Test
    void findAll() {
        List<Customer> customerList = new ArrayList<>();
        Customer customer1 = new Customer();
        Customer customer2 = new Customer();
        Customer customer3 = new Customer();
        customerList.add(customer1);
        customerList.add(customer2);
        customerList.add(customer3);
        Page<Customer> customers = new PageImpl<>(customerList);

        when(customerRepository.findAll(pageable)).thenReturn(customers);
        Page<CustomerDTO> customerDTOPage = customerService.findAll(pageable);

        assertEquals(customerDTOPage.getTotalElements(), customers.getTotalElements());
        verify(customerRepository, times(1)).findAll(pageable);
    }


    @Test
    void findById() {
        when(customerRepository.findById(1L)).thenReturn(Optional.ofNullable(customer));
        CustomerDTO customer1 = customerService.findById(1L);
        assertThat(customer1).isNotNull();
        assertThat(customer1.getName()).isEqualTo("Đặng Bảo Quốc");
        assertThat(customer1.getAddress()).isEqualTo("Nam Định");
        assertThat(customer1.getMobile()).isEqualTo("0372087588");
    }

    @Test
    void delete() {
        Long id = 1L;
        willDoNothing().given(customerRepository).deleteById(id);
        customerService.delete(id);
        verify(customerRepository, times(1)).deleteById(id);
    }
}