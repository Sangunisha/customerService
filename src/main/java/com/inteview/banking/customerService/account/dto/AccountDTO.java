package com.inteview.banking.customerService.account.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.inteview.banking.customerService.base.dto.BaseDTO;
import com.inteview.banking.customerService.constants.AccountTypeEnum;
import com.inteview.banking.customerService.customer.domain.Customer;
import com.inteview.banking.customerService.customer.dto.CustomerDTO;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountDTO extends BaseDTO<AccountDTO> implements Serializable {

    @NotNull
    private String accountNumber;

    private String typeSpecificId;

    @Enumerated(EnumType.STRING)
    @NotNull
    private AccountTypeEnum accountType;

    private Double balanceAmount;

    @NotNull
    private String branchId;

    @NotNull
    private CustomerDTO customer;

    public String getAccountNumber() {
        return accountNumber;
    }

    public AccountDTO setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public String getTypeSpecificId() {
        return typeSpecificId;
    }

    public AccountDTO setTypeSpecificId(String typeSpecificId) {
        this.typeSpecificId = typeSpecificId;
        return this;
    }

    public AccountTypeEnum getAccountType() {
        return accountType;
    }

    public AccountDTO setAccountType(AccountTypeEnum accountType) {
        this.accountType = accountType;
        return this;
    }

    public Double getBalanceAmount() {
        return balanceAmount;
    }

    public AccountDTO setBalanceAmount(Double balanceAmount) {
        this.balanceAmount = balanceAmount;
        return this;
    }

    public String getBranchId() {
        return branchId;
    }

    public AccountDTO setBranchId(String branchId) {
        this.branchId = branchId;
        return this;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public AccountDTO setCustomer(CustomerDTO customer) {
        this.customer = customer;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountDTO)) return false;
        if (!super.equals(o)) return false;
        AccountDTO that = (AccountDTO) o;
        return Objects.equals(accountNumber, that.accountNumber) &&
                Objects.equals(typeSpecificId, that.typeSpecificId) &&
                accountType == that.accountType &&
                Objects.equals(balanceAmount, that.balanceAmount) &&
                Objects.equals(branchId, that.branchId) &&
                Objects.equals(customer, that.customer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), accountNumber, typeSpecificId, accountType, balanceAmount, branchId, customer);
    }
}
