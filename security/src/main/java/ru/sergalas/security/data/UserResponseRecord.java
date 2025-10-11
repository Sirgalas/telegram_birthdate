package ru.sergalas.security.data;

import java.util.List;
import java.util.Map;
import org.keycloak.representations.idm.CredentialRepresentation;
import ru.sergalas.security.web.data.ResponseData;

public record UserResponseRecord(
        String id,
        String username,
        String email,
        String firstName,
        String lastName,
        Boolean enabled,
        Long createdTimestamp,
        Map<String, List<String>> attributes,
        List<CredentialRepresentation> credentials,
        List<String> realmRoles
) implements ResponseData {

}
