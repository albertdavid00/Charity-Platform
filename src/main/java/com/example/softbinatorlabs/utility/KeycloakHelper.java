package com.example.softbinatorlabs.utility;

import org.keycloak.KeycloakPrincipal;
import org.springframework.security.core.Authentication;

public class KeycloakHelper {
    public static String getUser(Authentication authentication) {
        return ((KeycloakPrincipal) authentication.getPrincipal()).getKeycloakSecurityContext().getToken().getPreferredUsername();
    }
    public static Boolean userIsAdmin(Authentication authentication) {
        return ((KeycloakPrincipal) authentication.getPrincipal()).getKeycloakSecurityContext().getToken().getRealmAccess().getRoles().contains("ROLE_ADMIN");
    }
}
