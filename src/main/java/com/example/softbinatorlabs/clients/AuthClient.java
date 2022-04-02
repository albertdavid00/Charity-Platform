package com.example.softbinatorlabs.clients;

import com.example.softbinatorlabs.config.AuthClientConfig;
import com.example.softbinatorlabs.dtos.TokenDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient(value="auth", configuration = AuthClientConfig.class, url="${my.keycloak.url}")
public interface AuthClient {

    @RequestMapping(method = RequestMethod.POST, value = "${my.keycloak.auth.endpoint}")
    TokenDto login(Map<String, ?> loginForm);

    @RequestMapping(method = RequestMethod.POST, value = "${my.keycloak.auth.endpoint}")
    TokenDto refresh(Map<String, ?> refreshForm);


}
