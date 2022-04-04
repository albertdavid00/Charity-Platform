package com.example.softbinatorlabs.config;

import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@KeycloakConfiguration
@Import(KeycloakSpringBootConfigResolver.class)
class KeycloakConfig extends KeycloakWebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {

        KeycloakAuthenticationProvider keycloakAuthenticationProvider
                = keycloakAuthenticationProvider();
        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(
                new SimpleAuthorityMapper());
        auth.authenticationProvider(keycloakAuthenticationProvider);
    }

    @Bean
    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(
                new SessionRegistryImpl());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http
                // CORS si CSRF ne precizeaza de unde pot veni request-urile, dam disable la csrf
                .cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/users/register-user", "/login", "/refresh").permitAll()
                .antMatchers("/users/register-admin").hasRole("ADMIN")
                .antMatchers("/users/delete/**").hasRole("ADMIN")
                .antMatchers("/users").authenticated()
                .antMatchers("/categories/add", "/categories/update/**", "/categories/delete/**").hasRole("ADMIN")
                .antMatchers("/events/add/**", "/events/delete/**").authenticated()
                .antMatchers("/comments/add/**", "/comments/delete/**", "/comments/update/**").authenticated()
                .antMatchers("/wallet", "/wallet/add-funds").authenticated()
                .antMatchers("/donations/donate/**").authenticated()
                .anyRequest().permitAll();
    }
}