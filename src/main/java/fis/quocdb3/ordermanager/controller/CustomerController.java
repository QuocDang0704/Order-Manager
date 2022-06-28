package fis.quocdb3.ordermanager.controller;

import fis.quocdb3.ordermanager.dto.auth.JwtResponse;
import fis.quocdb3.ordermanager.dto.auth.LoginRequest;
import fis.quocdb3.ordermanager.dto.customer.CustomerCreateDTO;
import fis.quocdb3.ordermanager.dto.customer.CustomerDTO;
import fis.quocdb3.ordermanager.dto.customer.CustomerUpdateDTO;
import fis.quocdb3.ordermanager.security.jwt.JwtUtils;
import fis.quocdb3.ordermanager.security.service.UserDetailsImpl;
import fis.quocdb3.ordermanager.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    CustomerService customerService;

    @GetMapping("/{pageNumber}/{pageSize}")
    public ResponseEntity<Page<CustomerDTO>> findAll(
            @PathVariable(name = "pageNumber") Integer pageNumber,
            @PathVariable(name = "pageSize") Integer pageSize
    ) {
        return ResponseEntity.ok(customerService.findAll(PageRequest.of(pageNumber, pageSize)));
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> findById(@PathVariable(name = "customerId") Long customerId) {
        return ResponseEntity.ok(customerService.findById(customerId));
    }

    @PutMapping("/")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<CustomerDTO>> create(@RequestBody CustomerCreateDTO customerCreateDTO) {
        customerCreateDTO.setPassword(encoder.encode(customerCreateDTO.getPassword()));
        customerService.create(customerCreateDTO);
        return ResponseEntity.ok(customerService.findAll());
    }

    @PostMapping("/{customerId}")
    @PreAuthorize("hasAuthority('ADMIN')")
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
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable(name = "customerId") Long customerId) {
        return ResponseEntity.ok("successful");
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }


}
