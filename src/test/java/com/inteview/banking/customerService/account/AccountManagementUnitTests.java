package com.inteview.banking.customerService.account;

import com.inteview.banking.customerService.CustomerServiceApplication;
import com.inteview.banking.customerService.account.dto.AccountBalanceResponseDTO;
import com.inteview.banking.customerService.account.dto.AccountDTO;
import com.inteview.banking.customerService.account.dto.AccountsResponseDTO;
import com.inteview.banking.customerService.base.exceptions.AppException;
import com.inteview.banking.customerService.constants.AccountTypeEnum;
import com.inteview.banking.customerService.constants.GenderTypeEnum;
import com.inteview.banking.customerService.customer.dto.CustomerDTO;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CustomerServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(
        locations = "classpath:application-test.properties")
public class AccountManagementUnitTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    private static String accountId;
    private static String customerId;
    private static String accountNumber;

    @Test
    public void contextLoads() {

    }

    private AccountDTO getAccount() {
        CustomerDTO customerDTO = new CustomerDTO()
                .setAddress("TestAddress")
                .setAge(32)
                .setCountry("India")
                .setEmailId("Testemail5")
                .setFirstName("Test firstname")
                .setMiddleName("middle")
                .setLastName("last")
                .setGender(GenderTypeEnum.FEMALE)
                .setPassword("password")
                .setPhoneNumber("9876533211")
                .setState("Kerala");
        AccountDTO accountDTO = new AccountDTO()
                .setAccountType(AccountTypeEnum.SAVINGS)
                .setBranchId("Test Branch")
                .setCustomer(customerDTO);
        return accountDTO;
    }


    @Test
    @Order(1)
    public void testCreateAccount() throws AppException {
        AccountDTO accountDTO = getAccount();
        AccountDTO response = this.restTemplate.postForObject(getRootUrl() + "/account",
                accountDTO, AccountDTO.class);
        accountId = response.getId();
        customerId = response.getCustomer().getId();
        accountNumber = response.getAccountNumber();
        assertNotNull(response);
    }

    @Test
    @Order(2)
    public void testUpdateAccount() throws AppException {
        AccountDTO accountDTO = restTemplate.getForObject(getRootUrl() + "/account/" + accountId, AccountDTO.class);
        accountDTO.setBalanceAmount(100d);
        restTemplate.put(getRootUrl() + "/account/" + accountId, accountDTO);
        AccountDTO updatedAccount = restTemplate.getForObject(getRootUrl() + "/account/" + accountId, AccountDTO.class);
        assertNotNull(updatedAccount);
    }

    @Test
    @Order(3)
    public void testGetAccount() throws AppException {
        AccountDTO accountDTO = restTemplate.getForObject(getRootUrl() + "/account/" + accountId, AccountDTO.class);
        assertNotNull(accountDTO);
    }

    @Test
    @Order(4)
    public void testGetByAccountNumber() throws AppException {
        AccountDTO accountDTO = restTemplate.getForObject(getRootUrl() + "/getAccount/" + accountNumber, AccountDTO.class);
        assertNotNull(accountDTO);
    }

    @Test
    @Order(5)
    public void testGetByCustomerId() throws AppException {
        AccountsResponseDTO accountDTO = restTemplate.getForObject(getRootUrl() + "/accounts/" + customerId, AccountsResponseDTO.class);
        assertNotNull(accountDTO);
    }

    @Test
    @Order(6)
    public void testGetAccountBalance() throws AppException {
        AccountBalanceResponseDTO accountDTO = restTemplate.getForObject(getRootUrl() + "/accountBalance/" + accountNumber, AccountBalanceResponseDTO.class);
        assertNotNull(accountDTO);
    }

    @Test
    @Order(7)
    public void testPutAccountBalance() throws AppException {
        AccountDTO accountDTO = restTemplate.getForObject(getRootUrl() + "/account/" + accountId, AccountDTO.class);
        accountDTO.setBalanceAmount(200d);
        restTemplate.put(getRootUrl() + "/accountBalance/" + accountId, accountDTO);
        AccountDTO updatedAccount = restTemplate.getForObject(getRootUrl() + "/account/" + accountId, AccountDTO.class);
        assertNotNull(updatedAccount);
    }

    @Test
    @Order(8)
    public void testDeleteAccount() throws AppException {
        AccountDTO accountDTO = getAccount();
        accountDTO.setAccountType(AccountTypeEnum.LOAN);
        accountDTO.getCustomer().setId(customerId);
        accountDTO = this.restTemplate.postForObject(getRootUrl() + "/account",
                accountDTO, AccountDTO.class);


        String id = accountDTO.getId();
        //AccountDTO accountDTO = restTemplate.getForObject(getRootUrl() + "/account/" + id, AccountDTO.class);
        assertNotNull(accountDTO);
        restTemplate.delete(getRootUrl() + "/account/" + id);
        try {
            accountDTO = restTemplate.getForObject(getRootUrl() + "/account/" + id, AccountDTO.class);
        } catch (final HttpClientErrorException e) {
            assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }
}

