package de.consol.dus.boundary.exceptionmapping;

import de.consol.dus.boundary.response.ErrorCode;
import de.consol.dus.boundary.response.ErrorResponse;
import de.consol.dus.exception.NoSuchEntityException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NoSuchEntityExceptionMapper implements ExceptionMapper<NoSuchEntityException> {
  @Override
  public Response toResponse(NoSuchEntityException exception) {
    return Response
        .status(Response.Status.NOT_FOUND)
        .entity(ErrorResponse.builder()
            .errorCode(ErrorCode.NOT_FOUND)
            .errorMessage(exception.getMessage())
            .build())
        .build();
  }
}
