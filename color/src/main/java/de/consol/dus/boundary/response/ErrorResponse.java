package de.consol.dus.boundary.response;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class ErrorResponse {
  ErrorCode errorCode;
  String errorMessage;

  @JsonPOJOBuilder(withPrefix = "")
  public static class Builder {}
}
