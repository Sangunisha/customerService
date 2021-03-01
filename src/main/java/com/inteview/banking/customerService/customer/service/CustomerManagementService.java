package com.inteview.banking.customerService.customer.service;

import com.inteview.banking.customerService.account.dto.AccountDTO;
import com.inteview.banking.customerService.account.service.AccountManagementService;
import com.inteview.banking.customerService.base.exceptions.AppException;
import com.inteview.banking.customerService.base.service.AbstractService;
import com.inteview.banking.customerService.base.util.CustomerServiceUtility;
import com.inteview.banking.customerService.constants.UserRoleEnum;
import com.inteview.banking.customerService.customer.domain.Customer;
import com.inteview.banking.customerService.customer.dto.CustomerDTO;
import com.inteview.banking.customerService.customer.dto.CustomerSearchRequestDTO;
import com.inteview.banking.customerService.customer.repo.CustomerRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service class to manage customer data manipulation
 */
@Service
public class CustomerManagementService extends AbstractService<Customer, CustomerDTO> {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private AccountManagementService accountManagementService;

    @Override
    public CrudRepository<Customer, String> getRepository() {
        return customerRepo;
    }

    /**
     * Gets customer DTO from entity
     *
     * @param entity
     * @param dto
     * @return
     * @throws AppException
     */
    @Override
    public CustomerDTO getDTO(Customer entity, CustomerDTO dto) throws AppException {
        dto.setFirstName(entity.getFirstName())
                .setMiddleName(entity.getMiddleName())
                .setLastName(entity.getLastName())
                .setGender(entity.getGender())
                .setAge(entity.getAge())
                .setPhoneNumber(entity.getPhoneNumber())
                .setEmailId(entity.getEmailId())
                .setAddress(entity.getAddress())
                .setState(entity.getState())
                .setCountry(entity.getCountry())
                .setCustomerId(entity.getUserId())
                .setPassword(entity.getPassword())
                .setUserRole(UserRoleEnum.CUSTOMER.name());
        return copyDTO(entity, dto);
    }

    /**
     * Gets customer entity from dto
     *
     * @param dto
     * @param entity
     * @return
     * @throws AppException
     */
    @Override
    public Customer populateEntity(CustomerDTO dto, Customer entity) throws AppException {

        entity.setFirstName(dto.getFirstName())
                .setMiddleName(dto.getMiddleName())
                .setLastName(dto.getLastName())
                .setGender(dto.getGender())
                .setAge(dto.getAge())
                .setPhoneNumber(dto.getPhoneNumber())
                .setEmailId(dto.getEmailId())
                .setAddress(dto.getAddress())
                .setState(dto.getState())
                .setCountry(dto.getCountry())
                .setUserId(dto.getCustomerId())
                .setPassword((null != dto.getPassword()) ? BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt()) : null);
        entity.setId(dto.getId());
        return entity;
    }

    /**
     * Sets the entity with the updated field values from dto
     *
     * @param dto
     * @param existingEntity
     * @return
     * @throws AppException
     */
    @Override
    public Customer updateModifiedParams(CustomerDTO dto, Customer existingEntity) throws AppException {
        existingEntity.setFirstName(dto.getFirstName())
                .setMiddleName(dto.getMiddleName())
                .setLastName(dto.getLastName())
                .setAge(dto.getAge())
                .setPhoneNumber(dto.getPhoneNumber())
                .setEmailId(dto.getEmailId())
                .setAddress(dto.getAddress())
                .setState(dto.getState())
                .setCountry(dto.getCountry());
        return existingEntity;
    }

    /**
     * Gets new customer dto
     *
     * @return
     * @throws AppException
     */
    @Override
    public CustomerDTO getNewDTO() throws AppException {
        return new CustomerDTO();
    }

    /**
     * Gets new customer entity
     *
     * @return
     * @throws AppException
     */
    @Override
    public Customer getNewEntity() throws AppException {
        return new Customer();
    }

    /**
     * Gets customer details from id
     *
     * @param customerId
     * @return
     */
    public CustomerDTO getCustomerDetails(String customerId) {
        return get(customerId);
    }

    /**
     * Gets customer details from email
     *
     * @param email
     * @return
     */
    public CustomerDTO getCustomerByEmail(String email) {
        Optional<Customer> customer = customerRepo.findByEmailId(email);
        if (customer.isPresent()) {
            return getDTO(customer.get(), getNewDTO());
        }
        return null;
    }

    /**
     * Gets customer details from phone
     *
     * @param phoneNumber
     * @return
     */
    private CustomerDTO getCustomerByPhone(String phoneNumber) {
        Optional<Customer> customer = customerRepo.findByPhoneNumber(phoneNumber);
        if (customer.isPresent()) {
            return getDTO(customer.get(), getNewDTO());
        }
        return null;
    }

    /**
     * Creates new customer
     *
     * @param customer
     * @return
     */
    //@Retryable
    public CustomerDTO createCustomer(CustomerDTO customer) {
        CustomerDTO existingCustomer = searchCustomer(new CustomerSearchRequestDTO()
                .setEmailId(customer.getEmailId())
                .setPhoneNumber(customer.getPhoneNumber()));
        if (null != existingCustomer) {
            throw new AppException(AppException.AppExceptionErrorCode.invalid_request_exception, "Customer with given email or phone number already exists");
        }
        customer.setCustomerId(CustomerServiceUtility.getCustomerId());
        CustomerDTO customerDTO = create(customer);
        logger.info("Created new customer with id :" + customer.getCustomerId());
        return customerDTO;
    }

    /**
     * Updates the customer
     *
     *
     * @param customerId
     * @param customer
     * @return
     */
    public CustomerDTO updateCustomer(String customerId, CustomerDTO customer) {
        Customer existingCustomer = getEntity(customerId);
        existingCustomer = updateModifiedParams(customer, existingCustomer);
        existingCustomer = updateEntity(existingCustomer);
        logger.info("Updated the customer with id :" + existingCustomer.getUserId());
        return getDTO(existingCustomer, getNewDTO());
    }

    /**
     * Deletes the customer with the bank accounts
     *
     * @param customerId
     * @return
     */
    @Transactional
    public Boolean deleteCustomer(String customerId) {
        List<AccountDTO> customerAccounts = accountManagementService.getCustomerAccounts(customerId);
        if (null != customerAccounts) {
            customerAccounts.stream().forEach(account -> accountManagementService.deleteAccount(account.getId()));
        }
        Boolean status = disable(customerId);
        logger.info("Deleted the customer with id :" + customerId);
        return status;
    }

    /**
     * To get the existing customer data with same email id or phonenumber
     *
     * @param searchRequestDTO
     * @return
     */
    public CustomerDTO searchCustomer(CustomerSearchRequestDTO searchRequestDTO) {
        if (null == searchRequestDTO || null == searchRequestDTO.getEmailId() && null == searchRequestDTO.getPhoneNumber()) {
            throw new AppException(AppException.AppExceptionErrorCode.invalid_request_exception, "Invalid request");
        }
        CustomerDTO customer = null;
        if (null != searchRequestDTO.getEmailId()) {
            customer = getCustomerByEmail(searchRequestDTO.getEmailId());
        }
        if (null == customer && null != searchRequestDTO.getPhoneNumber()) {
            customer = getCustomerByEmail(searchRequestDTO.getEmailId());
        }
        return customer;
    }
}
