package com.avwaveaf.accounts.controller;

import com.avwaveaf.accounts.constants.AccountConstants;
import com.avwaveaf.accounts.dto.CustomerDTO;
import com.avwaveaf.accounts.dto.ResponseDTO;
import com.avwaveaf.accounts.service.IAccountService;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountsController {

    private IAccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDTO> createAccount(
            @RequestBody CustomerDTO customerDTO
    ) {
        accountService.createAccount(customerDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDTO(AccountConstants.S_CREATED, AccountConstants.M_CREATED));
    }

    @GetMapping("/fetch")
    public ResponseEntity<CustomerDTO> getAccountsDetail(
            @RequestParam String mobileNumber
    ) {
        CustomerDTO customerDTO = accountService.getAccountDetail(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(customerDTO);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDTO> updateAccountsDetail(
            @RequestBody CustomerDTO customerDTO
    ) {
        boolean isUpdated = accountService.updateAccount(customerDTO);
        if (isUpdated) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseDTO(AccountConstants.S_REQ_SUCCESS, AccountConstants.M_REQ_SUCCESS)
            );
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ResponseDTO(AccountConstants.S_INT_ERR, AccountConstants.M_INT_ERR)
            );
        }
    }
}
