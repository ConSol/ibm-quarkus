package de.consol.dus.boundary.exceptionmapping;

import de.consol.dus.boundary.response.ErrorCode;
import de.consol.dus.boundary.response.ErrorResponse;
import de.consol.dus.exception.UserAlreadyExistsException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UserAlreadyExistsExceptionMapper implements ExceptionMapper<UserAlreadyExistsException> {
  @Override
  public Response toResponse(UserAlreadyExistsException exception) {
    return Response
        .status(Response.Status.BAD_REQUEST)
        .entity(ErrorResponse.builder()
            .errorCode(ErrorCode.ALREADY_EXISTS)
            .errorMessage(exception.getMessage())
            .build())
        .build();
  }
}
