package fis.quocdb3.ordermanager.controller;

import fis.quocdb3.ordermanager.dto.customer.CustomerCreateDTO;
import fis.quocdb3.ordermanager.dto.customer.CustomerDTO;
import fis.quocdb3.ordermanager.dto.customer.CustomerUpdateDTO;
import fis.quocdb3.ordermanager.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @GetMapping("/{pageNumber}/{pageSize}")
    public ResponseEntity<Page<CustomerDTO>> findAll(
            @PathVariable(name="pageNumber") Integer pageNumber,
            @PathVariable(name="pageSize") Integer pageSize
    ) {
        return ResponseEntity.ok(customerService.findAll(PageRequest.of(pageNumber, pageSize)));
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> findById(@PathVariable(name = "customerId") Long customerId) {
        return ResponseEntity.ok(customerService.findById(customerId));
    }

    @PutMapping("/")
    public ResponseEntity<List<CustomerDTO>> create(@RequestBody CustomerCreateDTO customerCreateDTO) {
        customerService.create(customerCreateDTO);
        return ResponseEntity.ok(customerService.findAll());
    }

    @PostMapping("/{customerId}")
    public ResponseEntity<List<CustomerDTO>> update(
            @PathVariable(name = "customerId") Long customerId,
            @RequestBody CustomerUpdateDTO customerUpdateDTO) {
        if (customerId.equals(customerUpdateDTO.getId())) {
            customerService.update(customerUpdateDTO);
            return ResponseEntity.ok(customerService.findAll());
        }
        throw new IllegalArgumentException("Id not match");
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<?> delete(@PathVariable(name = "customerId") Long customerId) {
        return ResponseEntity.ok("successful");
    }

}
