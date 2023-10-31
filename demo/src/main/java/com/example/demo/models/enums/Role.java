package com.example.demo.models.enums;

import org.springframework.security.core;

public enum Role implements GrantedAuthority {
    ROLE_USER, ROLE_ADMIN, ROLE_OWNER;

    @Override
    public String getAuthority() {
        return name();
    }
}