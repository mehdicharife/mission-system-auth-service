package io.github.mehdicharife.missionauthservice.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/{id}")
    @PreAuthorize("#id == principal.id or hasRole('ADMIN')")
    public ResponseEntity<Object> getAccount(@PathVariable("id") Long id) {
        Optional<Account> optionalAccount = this.accountService.getAccountById(id);
        if(optionalAccount.isPresent()) {
            return new ResponseEntity<>(AccountMapper.toDto(optionalAccount.get()), HttpStatus.OK);
        }
        
        return new ResponseEntity<>("The id " + id + " refers to no existing account.",HttpStatus.BAD_REQUEST);
    }
}
