package de.consol.dus.boundary;

import de.consol.dus.UserService;
import de.consol.dus.boundary.request.CreateUserRequest;
import de.consol.dus.boundary.response.ErrorResponse;
import de.consol.dus.boundary.response.UserResponse;
import java.net.URI;
import javax.enterprise.context.ApplicationScoped;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Metered;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

@ApplicationScoped
@Path(UserResource.PATH)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@AllArgsConstructor
public class UserResource {

  public static final String PATH = "/users";

  private final UserService userService;

  @Operation(summary = "Create a new user.")
  @APIResponses(value = {
      @APIResponse(
          responseCode = "201",
          description = "When the newly created user was successfully created.",
          content = @Content(
              mediaType = MediaType.APPLICATION_JSON,
              schema = @Schema(implementation = UserResponse.class))),
      @APIResponse(
          responseCode = "400",
          description = "When the username is longer than 255 characters, the email " +
              "malformed or a user with this username or email already exists",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @Counted(name = "postUserCount", description = "How often POST /users has been called")
  @Metered(name = "postUsersdMeter", description = "Meter information for POST /users")
  @Timed(name = "postUserTimer", description = "How long it takes to to create a single users.")
  @POST
  public Response postUser(@Valid CreateUserRequest request) {
    return Response
        .created(URI.create(String.format("%s/%s", PATH, request.getUsername())))
        .entity(userService.createUser(request)).build();
  }

  @Operation(summary = "Gets a user by their username (case-insensitive).")
  @APIResponses(value = {
      @APIResponse(
          responseCode = "200",
          description = "When the user was fetched.",
          content = @Content(
              mediaType = MediaType.APPLICATION_JSON,
              schema = @Schema(implementation = UserResponse.class))),
      @APIResponse(
          responseCode = "404",
          description = "When no user with this username has been found.",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @Counted(name = "getUserCount", description = "How often GET /users/{username} has been called")
  @Metered(name = "getUserMeter", description = "Meter information GET /users/{username}")
  @Timed(name = "getUserTimer", description = "How long it takes to to fetch a single users.")
  @Path("/{username}")
  @GET
  public Response getUser(@PathParam("username") String username) {
    return Response.ok(userService.getUserByUsername(username)).build();
  }
}
