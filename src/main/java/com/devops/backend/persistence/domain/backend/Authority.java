package com.devops.backend.persistence.domain.backend;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by ALadin Zaier PC IBS on 08/02/2017.
 */
public class Authority implements GrantedAuthority {

    private final String authority;

    public Authority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }
}
