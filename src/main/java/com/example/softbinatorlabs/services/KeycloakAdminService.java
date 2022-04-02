package com.example.softbinatorlabs.services;

import com.example.softbinatorlabs.clients.AuthClient;
import com.example.softbinatorlabs.dtos.TokenDto;
import com.example.softbinatorlabs.models.User;
import com.example.softbinatorlabs.repositories.UserRepository;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
import javax.ws.rs.core.Response;
import java.util.Collections;

import static org.keycloak.admin.client.CreatedResponseUtil.getCreatedId;

@Service
public class KeycloakAdminService {

    @Value("${keycloak.realm}")
    private String keycloakRealm;

    @Value("${keycloak.resource}")
    private String keycloakClient;

    private final Keycloak keycloak;
    private final AuthClient authClient;
    private final UserRepository userRepository;
    private RealmResource realm;

    @Autowired
    public KeycloakAdminService(Keycloak keycloak, AuthClient authClient,
                                UserRepository userRepository) {
        this.keycloak = keycloak;
        this.authClient = authClient;
        this.userRepository = userRepository;
    }

    // Imediat ce are loc injectarea din constructor, se apeleaza aceasta metoda,
    // inainte de utilizarea serviciului
    @PostConstruct
    public void initRealmResource() {
        this.realm = this.keycloak.realm(keycloakRealm);
    }

    public TokenDto addUserToKeycloak(Long userId, String password, String role) {

        // Aceste obiecte Representation vin din biblioteca Keycloak

        // Mai jos cream un nou user pe care il salvam ulterior in Keycloak
        UserRepresentation keycloakUser = new UserRepresentation();
        keycloakUser.setEnabled(true);
        keycloakUser.setUsername(userId.toString());

        // Obiect pentru retinerea parolei (Keycloak are propriul algoritm de hash, deci nu mai e nevoie sa ne ocupam noi)
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();

        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(password);
        credentialRepresentation.setTemporary(false);

        keycloakUser.setCredentials(Collections.singletonList(credentialRepresentation));

        // Cream user-ul in realm-ul nostru si ii preluam apoi id-ul din Keycloak pe care il afisam
        Response response = realm.users().create(keycloakUser);
        String keycloakUserId = getCreatedId(response);
        System.out.println("User has been saved with an id: " + keycloakUserId);

        // Cautam user-ul creat si ii adaugam rolul
        UserResource userResource = realm.users().get(keycloakUserId);
        RoleRepresentation roleRepresentation = realm.roles().get(role).toRepresentation();
        userResource.roles().realmLevel().add(Collections.singletonList(roleRepresentation));

        // Facem un request de login pentru a intoarce si un token odata ce s-a inregistrat utilizatorul in app
        MultiValueMap<String, String> loginCredentials = new LinkedMultiValueMap<>();
        loginCredentials.add("client_id", keycloakClient);
        loginCredentials.add("username", userId.toString());
        loginCredentials.add("password", password);
        loginCredentials.add("grant_type", "password");

        TokenDto token = authClient.login(loginCredentials);

        return token;

    }

    /**
     TODO: Endpoint for deleting users
     Instructions: write registerAdmin method (basically the same as registerUser),
     delete-user should be an ADMIN-only endpoint, userId sent as a PathVariable,
     also delete method in userService to delete the user from the database
     */
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist"));

        // Folsind realm-ul,pe care l-am initializat cu permisiuni de admin cautam user-ul dupa id
        // care reprezinta username-ul din keycloak

        UserRepresentation userRepresentation = realm.users().search(id.toString()).get(0);

        realm.users().delete(userRepresentation.getId());
    }

    /**
     TODO: endpoint for password changing in UserController
     You just receive an email and the new password, no extra verification needed (for now)
     */
//    public void changePassword(ChangePasswordDto changePasswordDto) {
//
//        User user = userRepository.findByEmail(changePasswordDto.getEmail())
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist"));
//
//
//        UserRepresentation userRepresentation = realm.users().search(user.getId().toString()).get(0);
//
//        CredentialRepresentation newCredential = new CredentialRepresentation();
//        newCredential.setType(CredentialRepresentation.PASSWORD);
//        newCredential.setValue(changePasswordDto.getNewPassword());
//        newCredential.setTemporary(false);
//        userRepresentation.setCredentials(Collections.singletonList(newCredential));
//
//        realm.users().get(userRepresentation.getId()).update(userRepresentation);
//    }

}
