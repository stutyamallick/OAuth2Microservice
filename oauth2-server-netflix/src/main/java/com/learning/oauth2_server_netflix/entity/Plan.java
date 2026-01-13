package com.learning.oauth2_server_netflix.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.learning.oauth2_server_netflix.entity.enums.PlanType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "PLANS")
public class Plan {

    @Id
    private int accountId;

    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Enumerated(EnumType.STRING)
    private PlanType currentPlanType;

    private LocalDate planExpiresOn;
}
