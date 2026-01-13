package com.learning.oauth2_server_netflix.config.authentication;

import com.learning.oauth2_server_netflix.entity.Account;
import com.learning.oauth2_server_netflix.entity.enums.PlanType;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class UserPrincipal implements UserDetails {

    private final Account account;

    public UserPrincipal(Account account) {
        this.account = account;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority user = new SimpleGrantedAuthority("ROLE_USER");
        SimpleGrantedAuthority privilegeUser = new SimpleGrantedAuthority("ROLE_PRIVILEGE_USER");
        SimpleGrantedAuthority admin = new SimpleGrantedAuthority("ROLE_ADMIN");

        if(account.getPlan().getCurrentPlanType().equals(PlanType.PREMIUM))
            return Arrays.asList(user, privilegeUser, admin);
        else if(account.getPlan().getCurrentPlanType().equals(PlanType.STANDARD))
            return Arrays.asList(user, privilegeUser);
        else if(account.getPlan().getCurrentPlanType().equals(PlanType.MOBILE))
            return List.of(user);

        return Collections.emptyList();
    }

    @Override
    public @Nullable String getPassword() {
        return account.getPassword();
    }

    @Override
    public String getUsername() {
        return account.getEmailId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        LocalDate sixMonthsFromToday = LocalDate.now().minusMonths(6);
        return account.getPasswordLastSetDate().isBefore(sixMonthsFromToday);
    }

    @Override
    public boolean isEnabled() {
        return account.isEmailVerified();
    }
}
