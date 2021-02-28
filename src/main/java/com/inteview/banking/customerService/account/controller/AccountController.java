package com.inteview.banking.customerService.account.controller;

import com.inteview.banking.customerService.account.dto.AccountBalanceResponseDTO;
import com.inteview.banking.customerService.account.dto.AccountDTO;
import com.inteview.banking.customerService.account.dto.AccountsResponseDTO;
import com.inteview.banking.customerService.account.service.AccountManagementService;
import com.inteview.banking.customerService.base.exceptions.AppException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class to handle account management requests
 *
 * @createdby sangeetha on 20/02/2021
 */
@RestController
public class AccountController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AccountManagementService accountManagementService;

    /**
     * To check whether the logged in user is authorized to perform the action
     *
     * @param customerId
     * @return
     */
    private Boolean isAuthorized(String customerId) {
        /*String loggedInCustomerId = "4028abfa77df99680177df9984230000";
        UserRoleEnum loggedInCustomerRole = UserRoleEnum.CUSTOMER;
        if (loggedInCustomerRole.equals(UserRoleEnum.CUSTOMER) && !loggedInCustomerId.equals(customerId)) {
            return false;
        }*/
        return true;
    }

    /**
     * Gets all accounts hold by the customer
     *
     * @param customerId
     * @return
     */
    @GetMapping("/accounts/{customerId}")
    public AccountsResponseDTO getAccounts(@PathVariable("customerId") String customerId) {
        if (!isAuthorized(customerId)) {
            throw new AppException(AppException.AppExceptionErrorCode.not_permitted, "You are not permitted to do this action");
        }
        List<AccountDTO> accounts = accountManagementService.getCustomerAccounts(customerId);
        return new AccountsResponseDTO().setAccounts(accounts);
    }

    /**
     * Gets the account details from the account id
     *
     * @param accountId
     * @return
     */
    @GetMapping("/account/{accountId}")
    public AccountDTO getAccountDetails(@PathVariable("accountId") String accountId) {
        AccountDTO accountDTO = accountManagementService.get(accountId);
        if (null != accountDTO && !isAuthorized(accountDTO.getCustomer().getId())) {
            throw new AppException(AppException.AppExceptionErrorCode.not_permitted, "You are not permitted to do this action");
        }
        return accountDTO;
    }

    /**
     * Gets the account details from the account number
     *
     * @param accountNumber
     * @return
     */
    @GetMapping("/getAccount/{accountNumber}")
    public AccountDTO getAccountDetailsFromNumber(@PathVariable("accountNumber") String accountNumber) {
        AccountDTO accountDTO = accountManagementService.getByAccountNumber(accountNumber);
        if (null != accountDTO && !isAuthorized(accountDTO.getCustomer().getId())) {
            throw new AppException(AppException.AppExceptionErrorCode.not_permitted, "You are not permitted to do this action");
        }
        return accountDTO;
    }

    /**
     * Creates a new account for the customer
     *
     * @param account
     * @return
     */
    @PostMapping("/account")
    public AccountDTO createAccount(@RequestBody AccountDTO account) {
        logger.info("Request for creating a new account");
        return accountManagementService.createAccount(account);
    }

    /**
     * Updates the type specific account id (savings account id/ loan account id)
     *
     * @param accountId
     * @param account
     * @return
     */
    @PutMapping("/accountId/{accountId}")
    public AccountDTO updateAccountTypeSpecifcId(@PathVariable("accountId") String accountId, @RequestBody AccountDTO account) {
        return accountManagementService.updateAccountTypeSpecifcId(accountId, account);
    }

    /**
     * Updates the bank account
     *
     * @param accountId
     * @param account
     * @return
     */
    @PutMapping("/account/{accountId}")
    public AccountDTO updateAccount(@PathVariable("accountId") String accountId, @RequestBody AccountDTO account) {
        return accountManagementService.updateAccount(accountId, account);
    }

    /**
     * Updates the account balance
     *
     * @param accountId
     * @param accountDTO
     * @return
     */
    @PutMapping("/accountBalance/{accountId}")
    public AccountDTO updateAccountBalance(@PathVariable("accountId") String accountId, @RequestBody AccountDTO accountDTO) {
        if (null != accountDTO && !isAuthorized(accountDTO.getCustomer().getId())) {
            throw new AppException(AppException.AppExceptionErrorCode.not_permitted, "You are not permitted to do this action");
        }
        return accountManagementService.updateAccountBalance(accountDTO);
    }

    /**
     * Gets the account balance from the account number
     *
     * @param accountNumber
     * @return
     */
    @GetMapping("/accountBalance/{accountNumber}")
    public AccountBalanceResponseDTO getAccountBalance(@PathVariable("accountId") String accountNumber) {
        AccountDTO accountDTO = accountManagementService.getByAccountNumber(accountNumber);
        return new AccountBalanceResponseDTO().setAccountNumber(accountDTO.getAccountNumber())
                .setBalanceAmount(accountDTO.getBalanceAmount());
    }

    /**
     * Deletes the customer account
     *
     * @param accountId
     * @return
     */
    @DeleteMapping("/account/{accountId}")
    public Boolean deleteCustomer(@PathVariable("accountId") String accountId) {
        AccountDTO accountDTO = accountManagementService.get(accountId);
        if (null != accountDTO && !isAuthorized(accountDTO.getCustomer().getId())) {
            throw new AppException(AppException.AppExceptionErrorCode.not_permitted, "You are not permitted to do this action");
        }
        return accountManagementService.deleteAccount(accountId);
    }
}
