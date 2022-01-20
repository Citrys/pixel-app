package com.cocktail.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

     @Value("${spring.security.oauth2.client.registration.cognito.client-id}")
     String clientId;
     @Value("${cognito.logoutUrl}")
     String logoutUrl;

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//                 http
//                .csrf()
//                .and()
//                .authorizeRequests(authorize ->
//                        authorize.mvcMatchers("/api").permitAll()
//                                .anyRequest().authenticated())
//                .oauth2Login()
//                .and()
//                .logout()
//                .logoutSuccessHandler(new CognitoOidcLogoutSuccessHandler(logoutUrl, clientId));
//    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.POST, "/");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http

              .csrf().disable();
    }
}