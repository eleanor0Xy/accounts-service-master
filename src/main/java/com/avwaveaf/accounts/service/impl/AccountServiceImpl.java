package com.avwaveaf.accounts.service.impl;

import com.avwaveaf.accounts.constants.AccountConstants;
import com.avwaveaf.accounts.dto.AccountsDTO;
import com.avwaveaf.accounts.dto.CustomerDTO;
import com.avwaveaf.accounts.entity.Accounts;
import com.avwaveaf.accounts.entity.Customer;
import com.avwaveaf.accounts.exception.CustomerAlreadyExists;
import com.avwaveaf.accounts.exception.ResourceNotFoundException;
import com.avwaveaf.accounts.mapper.AccountsMapper;
import com.avwaveaf.accounts.mapper.CustomerMapper;
import com.avwaveaf.accounts.repository.AccountsRepository;
import com.avwaveaf.accounts.repository.CustomerRepository;
import com.avwaveaf.accounts.service.IAccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;

    /**
     * Create Account Service
     *
     * @param customerDTO - Customer DTO Object
     */
    @Override
    public void createAccount(CustomerDTO customerDTO) {
        Customer customer = CustomerMapper.toEntity(customerDTO, new Customer());

        Optional<Customer> eCustomer = customerRepository.findByMobileNumber(customerDTO.getMobileNumber());

        if (eCustomer.isPresent())
            throw new CustomerAlreadyExists("Customer Already Registered for this Moblile Number");

        customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy("Guest");

        Customer sCustomer = customerRepository.save(customer);
        accountsRepository.save(createNewAccount(sCustomer));
    }

    /**
     * @param mobileNumber customer mobile number field
     * @return CustomerDTO - object
     */
    @Override
    public CustomerDTO getAccountDetail(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );

        Accounts acc = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Accounts", "customerId", customer.getCustomerId().toString())
        );

        CustomerDTO res = CustomerMapper.toDTO(customer, new CustomerDTO());
        res.setAccounts(AccountsMapper.toDTO(acc, new AccountsDTO()));
        return res;
    }

    /**
     * handler for update PUT method in customer Object
     *
     * @param updatedCustomer - CustomerDTO Object
     * @return boolean - success flag
     */
    @Override
    public boolean updateAccount(CustomerDTO updatedCustomer) {
        boolean isUpdated = false;
        AccountsDTO accDTO = updatedCustomer.getAccounts();
        if(accDTO!=null){
            // Find and map accounts object
            Accounts acc = accountsRepository.findById(accDTO.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Accounts", "Account Number", accDTO.getAccountNumber().toString())
            );
            AccountsMapper.toEntity(accDTO, acc);
            acc = accountsRepository.save(acc);

            // find and map customer object
            Long customerId = acc.getCustomerId();
            Customer cust = customerRepository.findById(customerId).orElseThrow(
                    ()-> new ResourceNotFoundException("Customer", "Customer ID", customerId.toString())
            );
            CustomerMapper.toEntity(updatedCustomer, cust);
            customerRepository.save(cust);

            isUpdated = true;
        }

        return isUpdated;
    }

    /**
     * (HELPER-MTHD)
     * Create New Account Object Helper which generate
     * automatic account number, setting default type and branch address
     *
     * @param customer [Object]
     * @return Accounts [NEW - Object]
     */
    private Accounts createNewAccount(Customer customer) {
        Accounts accounts = Accounts.builder()
                .customerId(customer.getCustomerId())
                .accountNumber(1000000000L + new Random().nextInt(900000000))
                .accountType(AccountConstants.SAVINGS)
                .branchAddress(AccountConstants.ADDRESS)
                .build();
        accounts.setCreatedAt(LocalDateTime.now());
        accounts.setCreatedBy("Guest");

        return accounts;
    }

}
