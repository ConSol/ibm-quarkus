package de.consol.dus.boundary.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CreateUserRequest {

  @NotNull
  @Size(max = 255)
  private String username;

  @NotNull
  @Email
  private String email;

  @NotNull
  private String password;
}
