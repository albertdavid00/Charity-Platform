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

        // Set the request body
        // Keycloak primeste cele 4 atribute de mai jos, asa ca noi construim un map
        // in care punem valorile noastre
        MultiValueMap<String, String> loginCredentials = new LinkedMultiValueMap<>();
        loginCredentials.add("client_id", keycloakClient);
        loginCredentials.add("username", inAppUser.get().getId().toString());
        loginCredentials.add("password", loginDto.getPassword());
        loginCredentials.add("grant_type", loginDto.getGrantType());
        // Keycloak login (will return an Access Token)
        // Pe Frontend sunt stocate si accessToken-ul si refreshToken-ul
        // Odata ce expira accessToken-ul (pica un request cu 401 unauthorized),
        // trebuie apelat endpoint-ul pentru refresh token de pe frontend, care va returna un nou access token valid
        // (atata timp cat nici refresh token-ul nu a expirat).
        TokenDto token = authClient.login(loginCredentials);
        return token;

    }

    @SneakyThrows
    public TokenDto refresh(RefreshTokenDto refreshTokenDto) {

        // Logica de mai sus intervine si pentru refresh token
        // Set the request body
        MultiValueMap<String, String> refreshCredentials = new LinkedMultiValueMap<>();
        refreshCredentials.add("client_id", keycloakClient);
        refreshCredentials.add("refresh_token", refreshTokenDto.getRefreshToken());
        refreshCredentials.add("grant_type", refreshTokenDto.getGrantType());

        TokenDto token = authClient.refresh(refreshCredentials);
        return token;
    }

}
