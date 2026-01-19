package com.avwaveaf.accounts.mapper;

import com.avwaveaf.accounts.dto.AccountsDTO;
import com.avwaveaf.accounts.entity.Accounts;

public class AccountsMapper {

    public static AccountsDTO toDTO(Accounts accounts,  AccountsDTO accountsDTO) {
        accountsDTO.setAccountNumber(accounts.getAccountNumber());
        accountsDTO.setAccountType(accounts.getAccountType());
        accountsDTO.setBranchAddress(accounts.getBranchAddress());
        return accountsDTO;
    }

    public static Accounts toEntity(AccountsDTO accountsDTO,  Accounts accounts) {
        accounts.setAccountNumber(accountsDTO.getAccountNumber());
        accounts.setAccountType(accountsDTO.getAccountType());
        accounts.setBranchAddress(accountsDTO.getBranchAddress());
        return accounts;
    }

}
