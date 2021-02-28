package com.inteview.banking.customerService.customer.controller;

import com.inteview.banking.customerService.customer.dto.CustomerDTO;
import com.inteview.banking.customerService.customer.dto.CustomerSearchRequestDTO;
import com.inteview.banking.customerService.customer.service.CustomerManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class that accepts customer requests
 *
 * @Createdby sangeetha on 21/02/2021
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CustomerManagementService customerManagementService;

    /**
     * Gets customer details from id
     *
     * @param customerId
     * @return
     */
    @GetMapping("/{customerId}")
    public CustomerDTO getCustomerDetails(@PathVariable("customerId") String customerId) {
        return customerManagementService.getCustomerDetails(customerId);
    }

    /**
     * Gets customer from username
     *
     * @param username
     * @return
     */
    @GetMapping("/getUser/{username}")
    public CustomerDTO getUserDetails(@PathVariable("username") String username) {
        return customerManagementService.getCustomerByEmail(username);
    }

    /**
     * To get the existing customer data with same email id or phonenumber
     *
     * @param searchRequestDTO
     * @return
     */
    @PostMapping("/search")
    public CustomerDTO searchCustomer(@RequestBody CustomerSearchRequestDTO searchRequestDTO) {
        return customerManagementService.searchCustomer(searchRequestDTO);
    }

    /**
     * Creates customer
     *
     * @param customer
     * @return
     */
    @PostMapping("/")
    public CustomerDTO createCustomer(@RequestBody CustomerDTO customer) {
        return customerManagementService.createCustomer(customer);
    }

    /**
     * Updates the customer
     *
     * @param customerId
     * @param customer
     * @return
     */
    @PutMapping("/{customerId}")
    public CustomerDTO updateCustomer(@PathVariable("customerId") String customerId, @RequestBody CustomerDTO customer) {
        return customerManagementService.updateCustomer(customer);
    }

    /**
     * Deletes(soft delete) the customer
     *
     * @param customerId
     * @return
     */
    @DeleteMapping("/{customerId}")
    public Boolean deleteCustomer(@PathVariable("customerId") String customerId) {
        return customerManagementService.deleteCustomer(customerId);
    }
}
