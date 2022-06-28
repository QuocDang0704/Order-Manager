package fis.quocdb3.ordermanager.service.impl;

import fis.quocdb3.ordermanager.domain.Customer;
import fis.quocdb3.ordermanager.dto.customer.CustomerCreateDTO;
import fis.quocdb3.ordermanager.dto.customer.CustomerDTO;
import fis.quocdb3.ordermanager.dto.customer.CustomerUpdateDTO;
import fis.quocdb3.ordermanager.exception.CustomerNotFoundException;
import fis.quocdb3.ordermanager.repository.CustomerRepository;
import fis.quocdb3.ordermanager.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private ModelMapper mapper;
    @Autowired
    CustomerRepository customerRepository;

    @Override
    public Page<CustomerDTO> findAll(Pageable pageable) {
        return customerRepository.findAll(pageable)
                .map(CustomerDTO.Mapper::fromEntity);
    }

    @Override
    public CustomerDTO findById(Long id) {
        return CustomerDTO.Mapper.fromEntity(customerRepository.findById(id)
                .orElseThrow(() -> {
                    throw new CustomerNotFoundException("Can't not find customer by id:" +id);
                })
        );
    }

    @Override
    public CustomerDTO create(CustomerCreateDTO customerCreate) {
        Customer customer = mapper.map(customerCreate, Customer.class);

        return CustomerDTO.Mapper.fromEntity(
                customerRepository.save(customer)
        );
    }

    @Override
    public CustomerDTO update(CustomerUpdateDTO customerUpdate) {
        Customer customer = mapper.map(customerUpdate, Customer.class);
        return CustomerDTO.Mapper.fromEntity(
                customerRepository.save(customer)
        );
    }

    @Override
    public void delete(Long id) {
        delete(id);
    }

    @Override
    public List<CustomerDTO> findAll() {
        return customerRepository.findAll().stream()
                .map(CustomerDTO.Mapper::fromEntity)
                .collect(Collectors.toList());
    }
}
