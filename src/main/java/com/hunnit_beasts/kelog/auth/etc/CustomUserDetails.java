package com.hunnit_beasts.kelog.auth.etc;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final CustomUserInfoDTO customUserInfoDTO;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + customUserInfoDTO.getUserType().getType().toUpperCase()));
    }

    @Override
    public String getPassword() {
        return customUserInfoDTO.getPassword();
    }

    @Override
    public String getUsername() {
        return customUserInfoDTO.getUserId();
    }

    public Long getId(){
        return customUserInfoDTO.getId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
