package com.inteview.banking.customerService.customer.repo;

import com.inteview.banking.customerService.customer.domain.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepo extends CrudRepository<Customer, String> {

    Optional<Customer> findByEmailId(String email);
    Optional<Customer> findByPhoneNumber(String phoneNumber);
}
