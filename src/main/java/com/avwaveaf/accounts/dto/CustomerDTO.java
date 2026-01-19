package com.avwaveaf.accounts.dto;

import lombok.Data;

@Data
public class CustomerDTO {
    private String name;
    private String email;
    private String mobileNumber;
    private AccountsDTO accounts;
}
