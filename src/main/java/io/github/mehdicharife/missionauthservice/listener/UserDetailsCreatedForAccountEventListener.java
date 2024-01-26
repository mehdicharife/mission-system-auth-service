package io.github.mehdicharife.missionauthservice.listener;

import java.util.Optional;

import io.github.mehdicharife.missionauthservice.domain.Account;
import io.github.mehdicharife.missionauthservice.event.UserDetailsCreatedForAccountEvent;
import io.github.mehdicharife.missionauthservice.repository.AccountRepository;

public class UserDetailsCreatedForAccountEventListener {
    
    private AccountRepository accountRepository;

    public UserDetailsCreatedForAccountEventListener(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void react(UserDetailsCreatedForAccountEvent userDetailsCreatedForAccountEvent) {
        Long accountId = userDetailsCreatedForAccountEvent.getAccountId();
        Optional<Account> optionalAccount = this.accountRepository.findById(accountId);
        if(optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            account.setUserId(userDetailsCreatedForAccountEvent.getUserId());
            this.accountRepository.save(account);
        }
    }
}
