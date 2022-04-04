package com.example.softbinatorlabs.services;

import com.example.softbinatorlabs.clients.AuthClient;
import com.example.softbinatorlabs.dtos.LoginDto;
import com.example.softbinatorlabs.dtos.RefreshTokenDto;
import com.example.softbinatorlabs.dtos.TokenDto;
import com.example.softbinatorlabs.models.User;
import com.example.softbinatorlabs.repositories.UserRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.ws.rs.NotFoundException;
import java.util.Optional;

@Service
public class AuthService {

    private final AuthClient authClient;
    private final UserRepository userRepository;

    @Value("${keycloak.resource}")
    private String keycloakClient;

    @Autowired
    public AuthService(AuthClient authClient, UserRepository userRepository) {
        this.authClient = authClient;
        this.userRepository = userRepository;
    }

    @SneakyThrows
    public TokenDto login(LoginDto loginDto) {
        // Verificam daca exista user-ul in db dupa username, altfel aruncam o eroare
        Optional<User> inAppUser = userRepository.findByEmail(loginDto.getEmail());
        if (inAppUser.isEmpty()) {
            throw new NotFoundException("The user doesn't exist!");
        }


        MultiValueMap<String, String> loginCredentials = new LinkedMultiValueMap<>();
        loginCredentials.add("client_id", keycloakClient);
        loginCredentials.add("username", inAppUser.get().getId().toString());
        loginCredentials.add("password", loginDto.getPassword());
        loginCredentials.add("grant_type", loginDto.getGrantType());

        TokenDto token = authClient.login(loginCredentials);
        return token;

    }

    @SneakyThrows
    public TokenDto refresh(RefreshTokenDto refreshTokenDto) {

        MultiValueMap<String, String> refreshCredentials = new LinkedMultiValueMap<>();
        refreshCredentials.add("client_id", keycloakClient);
        refreshCredentials.add("refresh_token", refreshTokenDto.getRefreshToken());
        refreshCredentials.add("grant_type", refreshTokenDto.getGrantType());

        TokenDto token = authClient.refresh(refreshCredentials);
        return token;
    }

}
