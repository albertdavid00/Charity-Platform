package com.example.softbinatorlabs.services;

import com.example.softbinatorlabs.dtos.RegisterUserDto;
import com.example.softbinatorlabs.dtos.TokenDto;
import com.example.softbinatorlabs.dtos.UserInfoDto;
import com.example.softbinatorlabs.models.User;
import com.example.softbinatorlabs.repositories.UserRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.ws.rs.BadRequestException;
import java.util.List;

@Service
// Serviciul actioneaza ca middleman intre controller si aplicatie
// REMEMBER - NO BUSINESS LOGIC IN CONTROLLER, asta e treaba serviciului
public class UserService {
    private final UserRepository userRepository;
    private final KeycloakAdminService keycloakAdminService;

    // Injectam repository-ul de user pentru a lucra cu modelul de User din DB
    @Autowired
    public UserService(UserRepository userRepository, KeycloakAdminService keycloakAdminService) {
        this.userRepository = userRepository;
        this.keycloakAdminService = keycloakAdminService;
    }

    public UserInfoDto getUser(Long userId) {
        // Facem retrieve din DB folosind repository-ul
        // Daca nu gasim user-ul, aruncam o exceptie ce genereaza un raspuns 404 cu mesajul "User does not exist"
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist"));
        // Nu vrem ca un user sa primeasca parola altuia, asa ca mappam Userul pe un UserInfoDto care nu contine fieldul de parola
        // Aici, construim DTO-ul folosind un builder pattern oferit de Lombok
        // E la fel ca si cum am fi folosit un constructor urmat de setteri
        return UserInfoDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    public List<User> getUsers() {
        List<User> users = userRepository.findAll();
        return users;
    }

    @SneakyThrows
    public TokenDto registerUser(RegisterUserDto registerUserDto, String role) {

        if (userRepository.existsByEmail(registerUserDto.getEmail())) {
            throw new BadRequestException("User with email " + registerUserDto.getEmail() + " already exists!");
        }

        User newUser = User.builder()
                .username(registerUserDto.getUsername())
                .email(registerUserDto.getEmail())
                .build();
        Long userId = userRepository.save(newUser).getId();
        TokenDto token = keycloakAdminService.addUserToKeycloak(userId, registerUserDto.getPassword(), role);

        return token;
    }

    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            keycloakAdminService.deleteUser(id);
            userRepository.deleteById(id);
        }
        else {
            throw new BadRequestException("User with id " + id + " doesn't exist!");
        }
    }
}
