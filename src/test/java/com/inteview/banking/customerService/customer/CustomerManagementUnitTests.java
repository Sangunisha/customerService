package com.inteview.banking.customerService.customer;

import com.inteview.banking.customerService.CustomerServiceApplication;
import com.inteview.banking.customerService.base.exceptions.AppException;
import com.inteview.banking.customerService.constants.GenderTypeEnum;
import com.inteview.banking.customerService.customer.dto.CustomerDTO;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CustomerServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(
		locations = "classpath:application-test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerManagementUnitTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private String getRootUrl() {
		return "http://localhost:" + port;
	}


	private static String customerId;
	private static String customerName;

	@Test
	public void contextLoads() {

	}

	private CustomerDTO getCustomer()
	{
		CustomerDTO customerDTO = new CustomerDTO()
				.setAddress("TestAddress")
				.setAge(32)
				.setCountry("India")
				.setEmailId("Testemail6")
				.setFirstName("Test customername")
				.setMiddleName("middlename")
				.setLastName("lastname")
				.setGender(GenderTypeEnum.MALE)
				.setPassword("password1")
				.setPhoneNumber("9876541111")
				.setState("Kerala");
		return customerDTO;
	}

	@Test
	@Order(1)
	public void testCreateCUstomer() throws AppException {
		CustomerDTO customerDTO = getCustomer();
		CustomerDTO customer = this.restTemplate.postForObject(getRootUrl() + "/customer/",
				customerDTO, CustomerDTO.class);
		customerId = customer.getId();
		customerName = customer.getEmailId();
		assertNotNull(customer);
	}

	@Test
	@Order(2)
	public void testUpdateCustomer() throws AppException {
		CustomerDTO customerDTO = restTemplate.getForObject(getRootUrl() + "/customer/" + customerId, CustomerDTO.class);
		customerDTO.setFirstName("test name2");
		restTemplate.put(getRootUrl() + "/customer/" + customerId, customerDTO);
		CustomerDTO updatedCustomer = restTemplate.getForObject(getRootUrl() + "/customer/" + customerId, CustomerDTO.class);
		assertNotNull(updatedCustomer);
	}

	@Test
	@Order(3)
	public void testGetCustomer() throws AppException {
		CustomerDTO customerDTO = restTemplate.getForObject(getRootUrl() + "/customer/" + customerId, CustomerDTO.class);
		assertNotNull(customerDTO);
	}

	@Test
	@Order(4)
	public void testGetByUsername() throws AppException {
		CustomerDTO customerDTO = restTemplate.getForObject(getRootUrl() + "/customer/getUser/" + customerName, CustomerDTO.class);
		assertNotNull(customerDTO);
	}

	@Test
	@Order(5)
	public void testDeleteCustomer() throws AppException {
		CustomerDTO customerDTO = getCustomer();
		customerDTO.setEmailId("newEmail");
		customerDTO.setPhoneNumber("1234567890");
		CustomerDTO customer = this.restTemplate.postForObject(getRootUrl() + "/customer/",
				customerDTO, CustomerDTO.class);
		//CustomerDTO customerDTO = restTemplate.getForObject(getRootUrl() + "/customer/" + customer., CustomerDTO.class);
		assertNotNull(customerDTO);
		restTemplate.delete(getRootUrl() + "/customer/" + customer.getId());
		try {
			customerDTO = restTemplate.getForObject(getRootUrl() + "/customer/" + customer.getId(), CustomerDTO.class);
		} catch (final HttpClientErrorException e) {
			assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
		}
	}
}
