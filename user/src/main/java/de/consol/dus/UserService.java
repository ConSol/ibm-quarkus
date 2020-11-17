package de.consol.dus;

import de.consol.dus.boundary.response.UserResponse;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserService {

  public UserResponse getUser() {
    return UserResponse.builder()
        .username("Alice Wonder")
        .email("alice@wonder.land")
        .build();
  }
}
