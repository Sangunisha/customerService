package com.inteview.banking.customerService.account.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountsResponseDTO implements Serializable {

    private List<AccountDTO> accounts;

    public List<AccountDTO> getAccounts() {
        return accounts;
    }

    public AccountsResponseDTO setAccounts(List<AccountDTO> accounts) {
        this.accounts = accounts;
        return this;
    }
}
