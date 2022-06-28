package fis.quocdb3.ordermanager.security.service;

import fis.quocdb3.ordermanager.domain.Customer;
import fis.quocdb3.ordermanager.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private CustomerRepository customerRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Customer customer = customerRepository.findCustomerByUsername(username);

            return UserDetailsImpl.build(customer);
        } catch (Exception e) {
            log.error("Error loadUserByUsername");
            throw new IllegalArgumentException("Error loadUserByUsername");
        }
    }
}
