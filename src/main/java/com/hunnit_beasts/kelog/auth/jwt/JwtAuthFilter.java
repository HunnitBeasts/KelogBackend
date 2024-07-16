package com.hunnit_beasts.kelog.auth.jwt;

import com.hunnit_beasts.kelog.auth.etc.CustomUserDetails;
import com.hunnit_beasts.kelog.auth.handler.FilterExceptionHandler;
import com.hunnit_beasts.kelog.auth.serviceimpl.CustomUserDetailsService;
import com.hunnit_beasts.kelog.common.handler.exception.ExpectException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtUtil jwtUtil;
    private final FilterExceptionHandler filterExceptionHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.substring(7);

        if(!jwtUtil.validateToken(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        Long id = jwtUtil.getId(token);

        try{
            CustomUserDetails userDetails = customUserDetailsService.loadCustomUserByUsername(id);

            if (userDetails == null) {
                filterChain.doFilter(request,response);
                return;
            }

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        }catch (ExpectException e){
            filterExceptionHandler.handleExpectException(response,e);
            return;
        }

        filterChain.doFilter(request,response);

    }
}