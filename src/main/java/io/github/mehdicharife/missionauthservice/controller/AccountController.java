package io.github.mehdicharife.missionauthservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.mehdicharife.missionauthservice.domain.Account;
import io.github.mehdicharife.missionauthservice.dto.AccountDtoOut;
import io.github.mehdicharife.missionauthservice.dto.CreateAccountBasedOnRequestIdRequest;
import io.github.mehdicharife.missionauthservice.exception.InvalidAccountCreationRequestIdException;
import io.github.mehdicharife.missionauthservice.mapper.AccountMapper;
import io.github.mehdicharife.missionauthservice.service.AccountService;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }
    
    @PostMapping
    public ResponseEntity<Object> createAccountBasedOnRequestId(@RequestBody CreateAccountBasedOnRequestIdRequest request) {
        try {
            Account account = this.accountService.createAccountBasedOnRequestId(request.getRequestId());
            AccountDtoOut accountDtoOut = AccountMapper.toDto(account);
            return new ResponseEntity<>(accountDtoOut, HttpStatus.CREATED);

        } catch(InvalidAccountCreationRequestIdException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
