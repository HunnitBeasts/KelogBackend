package com.hunnit_beasts.kelog.user.service;

import org.springframework.security.core.Authentication;

public interface AuthenticatedService {
    Long getId(Authentication authentication);
}
