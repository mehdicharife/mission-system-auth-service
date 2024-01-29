package io.github.mehdicharife.missionauthservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.mehdicharife.missionauthservice.domain.AccountCreationRequest;
import io.github.mehdicharife.missionauthservice.dto.AccountCreationRequestDto;
import io.github.mehdicharife.missionauthservice.exception.UsernameAlreadyExistsException;
import io.github.mehdicharife.missionauthservice.mapper.AccountCreationRequestMapper;
import io.github.mehdicharife.missionauthservice.service.AccountCreationRequestService;

@RestController
@RequestMapping("/account-creation-requests")
public class AccountCreationRequestController {

    private AccountCreationRequestService accountCreationRequestService;

    public AccountCreationRequestController(AccountCreationRequestService accountCreationRequestService) {
        this.accountCreationRequestService = accountCreationRequestService;
    }


    @PostMapping
    public ResponseEntity<Object> submitAccountCreationRequest(AccountCreationRequestDto accountCreationRequestDto) {
        AccountCreationRequest accountCreationRequest = AccountCreationRequestMapper.fromDto(accountCreationRequestDto);
        try {
            this.accountCreationRequestService.createAccountCreationRequest(accountCreationRequest);
            return new ResponseEntity<>(accountCreationRequest, HttpStatus.CREATED);
        } catch(UsernameAlreadyExistsException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
    
}
