package de.consol.dus;

import de.consol.dus.boundary.color.ColorRestClient;
import de.consol.dus.boundary.request.CreateUserRequest;
import de.consol.dus.boundary.response.UserResponse;
import de.consol.dus.entity.User;
import de.consol.dus.entity.UserMapper;
import de.consol.dus.exception.EntityAlreadyExistsException;
import de.consol.dus.exception.NoSuchEntityException;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.WebApplicationException;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final ColorRestClient colorRestClient;

  public UserService(
      UserRepository userRepository,
      UserMapper userMapper,
      @RestClient ColorRestClient colorRestClient) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
    this.colorRestClient = colorRestClient;
  }

  public UserResponse getUserByUsername(String username) {
    return userRepository.findByUsername(username)
        .map(userMapper::entityToResponse)
        .orElseThrow(() -> new NoSuchEntityException(
            String.format("User with username %s does not exist", username)));
  }

  @Transactional
  public UserResponse createUser(CreateUserRequest request) {
    final String username = request.getUsername();
    if (userRepository.findByUsername(username).isPresent()) {
      throw new EntityAlreadyExistsException(
          String.format("User with username %s already exists", username));
    }
    final String email = request.getEmail();
    if (userRepository.findByEmail(email).isPresent()) {
      throw new EntityAlreadyExistsException(
          String.format("User with email %s already exists", email));
    }

    final String favoriteColorName = request.getFavoriteColor();
    try {
      colorRestClient.getColorByName(favoriteColorName);
    } catch (WebApplicationException e) {
      throw new NoSuchEntityException(
          String.format("Color with name %s does not exist", favoriteColorName));
    }
    final User toCreate = userMapper.requestToUser(request);
    userRepository.persist(toCreate);
    return userMapper.entityToResponse(toCreate);
  }
}
