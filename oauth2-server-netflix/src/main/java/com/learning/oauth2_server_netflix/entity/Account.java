package com.learning.oauth2_server_netflix.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ACCOUNTS")
public class Account {

    @Id
    private int id;

    private String fullName;

    @Column(unique = true, nullable = false)
    private String emailId;

    private boolean emailVerified;

    @Column(nullable = false)
    private String password;

    private LocalDate passwordLastSetDate;

    private String country;

    @JsonManagedReference
    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    private Plan plan;

}
