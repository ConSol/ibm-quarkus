package de.consol.dus.boundary.exceptionmapping;

import de.consol.dus.boundary.response.ErrorCode;
import de.consol.dus.boundary.response.ErrorResponse;
import de.consol.dus.exception.EntityAlreadyExistsException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class EntityAlreadyExistsExceptionMapper
    implements ExceptionMapper<EntityAlreadyExistsException> {
  @Override
  public Response toResponse(EntityAlreadyExistsException exception) {
    return Response
        .status(Response.Status.BAD_REQUEST)
        .entity(ErrorResponse.builder()
            .errorCode(ErrorCode.ALREADY_EXISTS)
            .errorMessage(exception.getMessage())
            .build())
        .build();
  }
}
