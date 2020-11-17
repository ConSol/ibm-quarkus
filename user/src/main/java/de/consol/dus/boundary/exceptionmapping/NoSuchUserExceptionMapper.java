package de.consol.dus.boundary.exceptionmapping;

import de.consol.dus.boundary.response.ErrorCode;
import de.consol.dus.boundary.response.ErrorResponse;
import de.consol.dus.exception.NoSuchUserException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NoSuchUserExceptionMapper implements ExceptionMapper<NoSuchUserException> {
  @Override
  public Response toResponse(NoSuchUserException exception) {
    return Response
        .status(Response.Status.NOT_FOUND)
        .entity(ErrorResponse.builder()
            .errorCode(ErrorCode.NOT_FOUND)
            .errorMessage(exception.getMessage())
            .build())
        .build();
  }
}
