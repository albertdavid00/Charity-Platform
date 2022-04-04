package com.example.softbinatorlabs.config;

import feign.RequestInterceptor;
import feign.form.FormEncoder;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AuthClientConfig {
    private final ObjectFactory<HttpMessageConverters> messageConverters;

    @Autowired
    AuthClientConfig(ObjectFactory<HttpMessageConverters> messageConverters) {
        this.messageConverters = messageConverters;
    }

    // Login-ul/Refresh-ul poate fi facut astfel cu x-www-form-urlencoded, in loc de json
    @Bean
    FormEncoder feignFormEncoder() {
        return new FormEncoder(new SpringEncoder(this.messageConverters));
    }

    // Pe request-urile din AuthClient vom preciza ca sunt trimise cu tipul form-urlencoded
    // Iar ca raspuns, accepta tipul json (pentru acel TokenDto)
    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("Content-Type", ContentType.APPLICATION_FORM_URLENCODED.getMimeType());
            requestTemplate.header("Accept", ContentType.APPLICATION_JSON.getMimeType());
        };
    }

}
