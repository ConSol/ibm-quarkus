package de.consol.dus.boundary.keycloak;

import de.consol.dus.boundary.keycloak.exception.KeycloakException;
import de.consol.dus.boundary.request.CreateUserRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

@ApplicationScoped
@RequiredArgsConstructor
public class KeycloakService {

  public static final String KEYCLOAK_UNEXPECTED_RESPONSE_CODE_ERROR_MESSAGE_FORMAT =
      "Error during creating the user %s with email %s in keycloak. Expected status code %s, but "
          + "got %s instead. Full response: %s";
  public static final String GENERAL_KEYCLOAK_ERROR_MESSAGE_FORMAT =
      "Error during creation of user %s with email %s in keycloak.";
  public static final String UNKNOWN_KEYCLOAK_ERROR_MESSAGE_FORMAT =
      "Unknown error during creation of user %s with email %s in keycloak.";
  public static final String ENTITY_UNREADABLE_MESSAGE = "(Entity unreadable)";

  private final Keycloak keycloak;

  private String realmName;

  @Inject
  KeycloakService setRealmName(
      @ConfigProperty(name = "quarkus.oidc.realm-name") String realmName) {
    this.realmName = realmName;
    return this;
  }

  public void createAccount(CreateUserRequest request) {
    UserRepresentation userRepresentation = constructUserRepresentation(request);
    UsersResource usersResource = keycloak.realm(realmName).users();
    String name = request.getUsername();
    String email = request.getEmail();
    try (Response response = usersResource.create(userRepresentation)) {
      validateKeycloakResponse(request, response);
    } catch (WebApplicationException webApplicationException) {
      throw new KeycloakException(
          String.format(GENERAL_KEYCLOAK_ERROR_MESSAGE_FORMAT, name, email),
          webApplicationException);
    } catch (ProcessingException processingException) {
      throw new KeycloakException(
          String.format(UNKNOWN_KEYCLOAK_ERROR_MESSAGE_FORMAT, name, email),
          processingException);
    }
  }

  private UserRepresentation constructUserRepresentation(final CreateUserRequest request) {
    final UserRepresentation userRepresentation = new UserRepresentation();
    userRepresentation.setEnabled(true);
    userRepresentation.setUsername(request.getUsername());
    userRepresentation.setEmail(request.getEmail());
    userRepresentation.setCredentials(
        List.of(constructCredentialsRepresentation(request.getPassword()))
    );
    return userRepresentation;
  }

  private CredentialRepresentation constructCredentialsRepresentation(String password) {
    CredentialRepresentation credentials = new CredentialRepresentation();
    credentials.setType(CredentialRepresentation.PASSWORD);
    credentials.setTemporary(false);
    credentials.setValue(password);
    return credentials;
  }

  private void validateKeycloakResponse(CreateUserRequest request, Response response) {
    if (!Objects.equals(Response.Status.CREATED.getStatusCode(), response.getStatus())) {
      String body;
      body = readMessageEntity(response);
      throw new KeycloakException(
          String.format(
              KEYCLOAK_UNEXPECTED_RESPONSE_CODE_ERROR_MESSAGE_FORMAT,
              request.getUsername(),
              request.getEmail(),
              Response.Status.CREATED.getStatusCode(),
              response.getStatus(),
              body));
    }
  }

  private String readMessageEntity(Response response) {
    String body;
    try {
      body = new String(((InputStream) response.getEntity()).readAllBytes());
    } catch (ClassCastException | IOException e) {
      body = ENTITY_UNREADABLE_MESSAGE;
    }
    return body;
  }
}
