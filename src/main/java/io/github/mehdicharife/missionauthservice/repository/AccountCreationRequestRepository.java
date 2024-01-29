package io.github.mehdicharife.missionauthservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.mehdicharife.missionauthservice.domain.AccountCreationRequest;

public interface AccountCreationRequestRepository extends JpaRepository<AccountCreationRequest, Long> {
    
}
