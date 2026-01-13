package com.learning.oauth2_server_netflix.repositiry;

import com.learning.oauth2_server_netflix.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountsRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByEmailId(String username);
}
