package com.avwaveaf.accounts.service;

import com.avwaveaf.accounts.dto.CustomerDTO;

public interface IAccountService {
    /**
     * Create Account Service
     * @param customerDTO - Customer DTO Object
     */
    void createAccount(CustomerDTO customerDTO);

    CustomerDTO getAccountDetail(String mobileNumber);

    /**
    *  handler for update PUT method in customer Object
    * @param updatedCustomer - CustomerDTO Object
    * */
    boolean updateAccount(CustomerDTO updatedCustomer);
}
