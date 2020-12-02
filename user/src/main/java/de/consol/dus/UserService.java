package de.consol.dus;

import de.consol.dus.boundary.keycloak.KeycloakService;
import de.consol.dus.boundary.request.CreateUserRequest;
import de.consol.dus.boundary.response.UserResponse;
import de.consol.dus.entity.User;
import de.consol.dus.entity.UserMapper;
import de.consol.dus.exception.NoSuchUserException;
import de.consol.dus.exception.UserAlreadyExistsException;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final KeycloakService keycloakService;

  public UserResponse getUserByUsername(String username) {
    return userRepository.findByUsername(username)
        .map(userMapper::entityToResponse)
        .orElseThrow(() -> new NoSuchUserException(
            String.format("User with username %s does not exist", username)));
  }

  @Transactional
  public UserResponse createUser(CreateUserRequest request) {
    final String username = request.getUsername();
    if (userRepository.findByUsername(username).isPresent()) {
      throw new UserAlreadyExistsException(
          String.format("User with username %s already exists", username));
    }
    final String email = request.getEmail();
    if (userRepository.findByEmail(email).isPresent()) {
      throw new UserAlreadyExistsException(
          String.format("User with email %s already exists", email));
    }

    keycloakService.createAccount(request);

    final User toCreate = userMapper.requestToUser(request);
    userRepository.persist(toCreate);
    return userMapper.entityToResponse(toCreate);
  }
}
