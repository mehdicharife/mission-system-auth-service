package io.github.mehdicharife.missionauthservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import io.github.mehdicharife.missionauthservice.domain.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("""
        SELECT a FROM Account a WHERE a.accountDetails.id IN 
            (SELECT ad.id FROM AccountDetails ad WHERE ad.username = :username)    
    """)
    Optional<Account> findByUsername(String username);
}
