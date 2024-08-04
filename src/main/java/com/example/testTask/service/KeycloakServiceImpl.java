package com.example.testTask.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RoleMappingResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KeycloakServiceImpl implements KeycloakService {

    private final Keycloak keycloak;

    @Value("${keycloak.realm}")
    private String realm;

    @Override
    public void addUser(String username, String password, String role) {

        UserRepresentation user = new UserRepresentation();
        user.setUsername(username);
        user.setEnabled(true);

        CredentialRepresentation passwordCredentials = new CredentialRepresentation();
        passwordCredentials.setValue(password);
        passwordCredentials.setTemporary(false);
        passwordCredentials.setType(CredentialRepresentation.PASSWORD);

        List<CredentialRepresentation> list = new ArrayList<>();
        list.add(passwordCredentials);
        user.setCredentials(list);

        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();
        Response createUser = usersResource.create(user);

        addRealmRoleToUser(username, role);
    }

    private void addRealmRoleToUser(String userName, String roleName) {
        RealmResource realmResource = keycloak.realm(realm);
        List<UserRepresentation> users = realmResource.users().search(userName);
        UserResource userResource = realmResource.users().get(users.get(0).getId());
        RoleRepresentation role = realmResource.roles().get(roleName).toRepresentation();

        RoleMappingResource roleMappingResource = userResource.roles();
        roleMappingResource.realmLevel().add(Collections.singletonList(role));
    }

    @Override
    public void deleteAllUsers() {
        UsersResource users = keycloak.realm(realm).users();
        users.list().forEach(user -> users.delete(user.getId()));
    }

    @Override
    public void deleteUserByUsername(String username) {
        UsersResource users = keycloak.realm(realm).users();
        String id = users.search(username).get(0).getId();
        users.delete(id);
    }
}