package de.consol.dus.boundary.response;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class UserResponse {
  String username;
  String email;
  String favoriteColor;
}
