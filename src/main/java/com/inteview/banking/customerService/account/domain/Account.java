package com.inteview.banking.customerService.account.domain;

import com.inteview.banking.customerService.base.domain.BaseDomainObject;
import com.inteview.banking.customerService.constants.AccountTypeEnum;
import com.inteview.banking.customerService.customer.domain.Customer;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Entity class for account table
 */
@Entity
public class Account extends BaseDomainObject<Account> {

    @Size(min = 12, max = 12)
    @NotNull
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    @NotNull
    private AccountTypeEnum accountType;

    private String typeSpecificId;

    private Double balanceAmount;

    @NotNull
    private String branchId;

    @NotNull
    @ManyToOne
    private Customer customer;

    public String getAccountNumber() {
        return accountNumber;
    }

    public Account setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    public AccountTypeEnum getAccountType() {
        return accountType;
    }

    public Account setAccountType(AccountTypeEnum accountType) {
        this.accountType = accountType;
        return this;
    }

    public String getTypeSpecificId() {
        return typeSpecificId;
    }

    public Account setTypeSpecificId(String typeSpecificId) {
        this.typeSpecificId = typeSpecificId;
        return this;
    }

    public Double getBalanceAmount() {
        return balanceAmount;
    }

    public Account setBalanceAmount(Double balanceAmount) {
        this.balanceAmount = balanceAmount;
        return this;
    }

    public String getBranchId() {
        return branchId;
    }

    public Account setBranchId(String branchId) {
        this.branchId = branchId;
        return this;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Account setCustomer(Customer customer) {
        this.customer = customer;
        return this;
    }
}
