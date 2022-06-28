package fis.quocdb3.ordermanager.repository;

import fis.quocdb3.ordermanager.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findCustomerByUsername(String username);
}
