package com.learning.oauth2_server_netflix;

import com.learning.oauth2_server_netflix.entity.Account;
import com.learning.oauth2_server_netflix.entity.Plan;
import com.learning.oauth2_server_netflix.entity.enums.PlanType;
import com.learning.oauth2_server_netflix.repositiry.AccountsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class UserDataAppStartupRunner implements CommandLineRunner {
    private final AccountsRepository accountsRepository;

    public UserDataAppStartupRunner(AccountsRepository accountsRepository) {
        this.accountsRepository = accountsRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        String encryptedPassword = "$2a$12$KtqSR1KJOsoBUrL.BFtH8OaXbjmZnRKY7krTYRMKZYBFridCxeidi";

        Plan planAccount1 = new Plan();
        planAccount1.setAccountId(1);
        planAccount1.setCurrentPlanType(PlanType.PREMIUM);
        planAccount1.setPlanExpiresOn(LocalDate.of(2026, 03, 04));

        Account account1 = new Account(1, "Stutya Mallick", "stutya@netflix.com", true, encryptedPassword,
                LocalDate.of(2026, 03, 04), "India", planAccount1);

        accountsRepository.save(account1);
    }
}
