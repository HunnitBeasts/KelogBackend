package com.hunnit_beasts.kelog.auth.service;

import org.springframework.security.core.Authentication;

public interface AuthenticatedService {
    Long getId(Authentication authentication);
}
