package com.inteview.banking.customerService.customer.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerSearchRequestDTO implements Serializable {

    private String emailId;
    private String phoneNumber;

    public String getEmailId() {
        return emailId;
    }

    public CustomerSearchRequestDTO setEmailId(String emailId) {
        this.emailId = emailId;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public CustomerSearchRequestDTO setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }
}
