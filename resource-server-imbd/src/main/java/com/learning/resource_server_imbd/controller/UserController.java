package com.learning.resource_server_imbd.controller;

import com.learning.resource_server_imbd.model.UserInfo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
public class UserController {

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/api/imbd/user-info")
    public UserInfo userInfo(Authentication authentication){

        return new UserInfo(
                authentication.getName(),
                authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList())
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/api/imbd/roleCheck")
    public String roleCheck(){ return "Role Check Working!";}

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/api/imbd/authorityCheck")
    public String authorityCheckAll(){ return "Authority Check Working!";}
}
