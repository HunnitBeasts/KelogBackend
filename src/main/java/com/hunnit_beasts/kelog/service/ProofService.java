package com.hunnit_beasts.kelog.service;

import org.springframework.security.core.Authentication;

public interface ProofService {
    Long getId(Authentication authentication);
}
