package com.jms.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class SuccessHandler implements AuthenticationSuccessHandler {
    protected String targetUrl;
    protected RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    
    public SuccessHandler(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

}
