package com.inteview.banking.customerService.customer.domain;

import com.inteview.banking.customerService.account.domain.Account;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.Set;

@Entity
@DiscriminatorValue("CUSTOMER")
public class Customer extends User implements Serializable {

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private Set<Account> accounts;

    public Set<Account> getAccounts() {
        return accounts;
    }

    public Customer setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
        return this;
    }
}
