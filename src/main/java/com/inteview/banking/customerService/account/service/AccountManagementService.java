package com.inteview.banking.customerService.account.service;

import com.inteview.banking.customerService.account.domain.Account;
import com.inteview.banking.customerService.account.dto.AccountDTO;
import com.inteview.banking.customerService.account.repo.AccountRepo;
import com.inteview.banking.customerService.base.exceptions.AppException;
import com.inteview.banking.customerService.base.service.AbstractService;
import com.inteview.banking.customerService.base.util.CustomerServiceUtility;
import com.inteview.banking.customerService.customer.domain.Customer;
import com.inteview.banking.customerService.customer.dto.CustomerDTO;
import com.inteview.banking.customerService.customer.service.CustomerManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service to manage account operations
 *
 * @createdby sangeetha on 20/02/2021
 */
@Service
public class AccountManagementService extends AbstractService<Account, AccountDTO> {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private CustomerManagementService customerManagementService;

    /**
     * @return
     */
    @Override
    public CrudRepository<Account, String> getRepository() {
        return accountRepo;
    }

    /**
     * Converts entity object to corresponding dto object
     *
     * @param entity
     * @param dto
     * @return
     * @throws AppException
     */
    @Override
    public AccountDTO getDTO(Account entity, AccountDTO dto) throws AppException {
        dto.setAccountNumber(entity.getAccountNumber())
                .setAccountType(entity.getAccountType())
                .setBalanceAmount(entity.getBalanceAmount())
                .setBranchId(entity.getBranchId())
                .setTypeSpecificId(entity.getTypeSpecificId())
                .setCustomer(customerManagementService.getDTO(entity.getCustomer(), customerManagementService.getNewDTO()));
        return copyDTO(entity, dto);
    }

    /**
     * Populates entity from the dto
     *
     * @param dto
     * @param entity
     * @return
     * @throws AppException
     */
    @Override
    public Account populateEntity(AccountDTO dto, Account entity) throws AppException {
        return entity.setAccountNumber(dto.getAccountNumber())
                .setAccountType(dto.getAccountType())
                .setBalanceAmount(dto.getBalanceAmount())
                .setBranchId(dto.getBranchId())
                .setTypeSpecificId(dto.getTypeSpecificId())
                .setCustomer(customerManagementService.getEntity(dto.getCustomer().getId()))
                // .setCustomer(customerManagementService.populateEntity(dto.getCustomer(), customerManagementService.getNewEntity()))
                .setId(dto.getId());
    }

    /**
     * Sets the entity with the updated values from the dto
     *
     * @param dto
     * @param existingEntity
     * @return
     * @throws AppException
     */
    @Override
    public Account updateModifiedParams(AccountDTO dto, Account existingEntity) throws AppException {
        return existingEntity
                .setBalanceAmount(dto.getBalanceAmount())
                .setTypeSpecificId(dto.getTypeSpecificId());
    }

    /**
     * Gets new account dto
     *
     * @return
     * @throws AppException
     */
    @Override
    public AccountDTO getNewDTO() throws AppException {
        return new AccountDTO();
    }

    /**
     * Gets new account entity
     *
     * @return
     * @throws AppException
     */
    @Override
    public Account getNewEntity() throws AppException {
        return new Account();
    }

    /**
     * Gets all accounts hold by the customer
     *
     * @param customerId
     * @return
     */
    public List<AccountDTO> getCustomerAccounts(String customerId) {
        Customer customer = customerManagementService.getEntity(customerId);
        List<Account> accounts = accountRepo.findByCustomer(customer);
        if (null != accounts) {
            return accounts.stream().map(acc -> getDTO(acc, getNewDTO())).collect(Collectors.toList());
        }
        return null;
    }

    /**
     * Creates new bank account with customer if not exists
     *
     * @param account
     * @return
     */
    @Transactional
    public AccountDTO createAccount(AccountDTO account) {
        CustomerDTO customer = null;
        if (null != account.getCustomer() && null == account.getCustomer().getId()) {
            customer = customerManagementService.createCustomer(account.getCustomer());
        } else {
            customer = customerManagementService.get(account.getCustomer().getId());
        }
        account.setCustomer(customer);
        account.setAccountNumber(CustomerServiceUtility.getAccountNumber());
        AccountDTO accountDTO = create(account);
        logger.info("Created new account for the customer " + customer.getFirstName() + " with id " + customer.getCustomerId());
        return accountDTO;
    }

    /**
     * Updates account balance
     *
     * @param accountDTO
     * @return
     */
    public AccountDTO updateAccountBalance(AccountDTO accountDTO) {
        Account existingAccount = getEntity(accountDTO.getId());
        existingAccount.setBalanceAmount(accountDTO.getBalanceAmount());
        existingAccount = updateEntity(existingAccount);
        AccountDTO account = getDTO(existingAccount, getNewDTO());
        logger.info("Updated the balance for the account number " + account.getAccountNumber());
        return account;
    }

    /**
     * Soft deletes the account
     *
     * @param accountId
     * @return
     */
    public Boolean deleteAccount(String accountId) {
        Boolean status = disable(accountId);
        logger.info("Deleted the account with id " + accountId);
        return status;
    }

    /**
     * Updates the type specific account id (savings account id/ loan account id)
     *
     * @param accountId
     * @param accountDTO
     * @return
     */
    public AccountDTO updateAccountTypeSpecifcId(String accountId, AccountDTO accountDTO) {
        Account existingAccount = getEntity(accountId);
        existingAccount.setTypeSpecificId(accountDTO.getTypeSpecificId());
        existingAccount = updateEntity(existingAccount);
        return getDTO(existingAccount, getNewDTO());
    }

    /**
     * Updates the account
     *
     * @param accountId
     * @param accountDTO
     * @return
     */
    public AccountDTO updateAccount(String accountId, AccountDTO accountDTO) {
        Account existingAccount = getEntity(accountId);
        existingAccount.setTypeSpecificId(accountDTO.getTypeSpecificId());
        existingAccount.setBalanceAmount(accountDTO.getBalanceAmount());
        existingAccount = updateEntity(existingAccount);
        AccountDTO account = getDTO(existingAccount, getNewDTO());
        logger.info("Updated the account : " + account.getAccountNumber());
        return account;
    }

    /**
     * Gets account from account number
     *
     * @param accountNumber
     * @return
     */
    public AccountDTO getByAccountNumber(String accountNumber) {
        Account existingAccount = accountRepo.findByAccountNumber(accountNumber);
        return getDTO(existingAccount, getNewDTO());
    }
}
