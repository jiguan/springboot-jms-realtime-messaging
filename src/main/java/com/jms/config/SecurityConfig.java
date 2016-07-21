package com.jms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationSuccessHandler successHandler;
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        //@formatter:off
        auth.inMemoryAuthentication()
        .withUser("user1").password("pass1").roles("USER").and()
        .withUser("user2").password("pass2").roles("USER").and()
        .withUser("user3").password("pass3").roles("USER");
        //@formatter:on
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
    
    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new SuccessHandler("/home");
    }
    

    @Bean
    public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      //@formatter:off
        http
        .authorizeRequests()
            .anyRequest().authenticated()
            .and()
        .formLogin()
            .successHandler(successHandler)
            .loginPage("/login")
            .permitAll()
            .and()
        .logout()
            .permitAll();
        //@formatter:on
        http.csrf().disable().sessionManagement().maximumSessions(-1).sessionRegistry(sessionRegistry());
    }
}
