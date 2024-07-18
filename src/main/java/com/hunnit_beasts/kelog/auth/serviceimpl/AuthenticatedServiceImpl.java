package com.hunnit_beasts.kelog.auth.serviceimpl;

import com.hunnit_beasts.kelog.auth.etc.CustomUserDetails;
import com.hunnit_beasts.kelog.auth.service.AuthenticatedService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticatedServiceImpl implements AuthenticatedService {

    @Override
    public Long getId(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated())
            return null;
        return ((CustomUserDetails) authentication.getPrincipal()).getId();
    }
}
