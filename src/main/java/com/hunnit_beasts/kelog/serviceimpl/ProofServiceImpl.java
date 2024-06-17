package com.hunnit_beasts.kelog.serviceimpl;

import com.hunnit_beasts.kelog.dto.convert.user.CustomUserDetails;
import com.hunnit_beasts.kelog.service.ProofService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class ProofServiceImpl implements ProofService {

    @Override
    public Long getId(Authentication authentication) {
        return ((CustomUserDetails) authentication.getPrincipal()).getId();
    }
}
