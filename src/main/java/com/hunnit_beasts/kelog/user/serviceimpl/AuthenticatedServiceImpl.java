package com.hunnit_beasts.kelog.user.serviceimpl;

import com.hunnit_beasts.kelog.user.etc.CustomUserDetails;
import com.hunnit_beasts.kelog.user.service.AuthenticatedService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticatedServiceImpl implements AuthenticatedService {

    @Override
    public Long getId(Authentication authentication) {
        return ((CustomUserDetails) authentication.getPrincipal()).getId();
    }
}
