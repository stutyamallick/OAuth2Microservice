package com.learning.oauth2_server_netflix.config.authentication;

import com.learning.oauth2_server_netflix.entity.Account;
import com.learning.oauth2_server_netflix.repositiry.AccountsRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final AccountsRepository accountsRepository;

    public CustomUserDetailService(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountsRepository.findByEmailId(username)
                .orElseThrow();

        return new UserPrincipal(account);
    }
}
