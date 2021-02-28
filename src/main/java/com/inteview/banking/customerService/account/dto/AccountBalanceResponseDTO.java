package com.inteview.banking.customerService.account.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountBalanceResponseDTO implements Serializable {

    @NotNull
    private String accountNumber;

    private Double balanceAmount;

    public String getAccountNumber() {
        return accountNumber;
    }

    public AccountBalanceResponseDTO setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public Double getBalanceAmount() {
        return balanceAmount;
    }

    public AccountBalanceResponseDTO setBalanceAmount(Double balanceAmount) {
        this.balanceAmount = balanceAmount;
        return this;
    }
}
