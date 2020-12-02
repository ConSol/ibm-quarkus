package de.consol.dus.boundary.keycloak;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;

@ApplicationScoped
public class KeycloakBean {

  /**
   * Produces the Keycloak bean.
   *
   * @param builder
   *     the keycloak builder to use.
   * @param serverUrl
   *     the url to the keycloak server.
   * @param realmName
   *     the realm name to use.
   * @param adminCliId
   *     the id of the client that has admin rights on the realm.
   * @param adminCliSecret
   *     the secret of that client.
   *
   * @return the Keycloak bean.
   */
  @Produces
  Keycloak keycloak(
      KeycloakBuilder builder,
      @ConfigProperty(name = "quarkus.oidc.auth-base-url") String serverUrl,
      @ConfigProperty(name = "quarkus.oidc.realm-name") String realmName,
      @ConfigProperty(name = "quarkus.oidc.admin-cli-id") String adminCliId,
      @ConfigProperty(name = "quarkus.oidc.credentials.admin-cli-secret") String adminCliSecret) {
    return builder
        .serverUrl(serverUrl)
        .realm(realmName)
        .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
        .clientId(adminCliId)
        .clientSecret(adminCliSecret)
        .build();
  }

  /**
   * Produces the standard keycloak builder.
   *
   * @return the KeycloakBuilder bean.
   */
  @Produces
  KeycloakBuilder builder() {
    return KeycloakBuilder.builder();
  }
}

