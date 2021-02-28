package com.inteview.banking.customerService.account.repo;

import com.inteview.banking.customerService.account.domain.Account;
import com.inteview.banking.customerService.customer.domain.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepo extends CrudRepository<Account, String> {
    List<Account> findByCustomer(Customer customerId);

    Account findByAccountNumber(String accountNumber);
}
