package de.consol.dus.entity;

import de.consol.dus.boundary.request.CreateUserRequest;
import de.consol.dus.boundary.response.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface UserMapper {
  UserResponse entityToResponse(User user);
  User requestToUser(CreateUserRequest request);
}
