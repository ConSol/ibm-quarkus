package de.consol.dus.boundary.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CreateUserRequest {
  private String username;
  private String email;
}
