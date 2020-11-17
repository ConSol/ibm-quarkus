package de.consol.dus.boundary.response;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class UserResponse {
  String username;
  String email;

  @JsonPOJOBuilder(withPrefix = "")
  public class Builder {}
}
