package de.consol.dus.boundary;

import de.consol.dus.UserService;
import de.consol.dus.boundary.request.CreateUserRequest;
import java.net.URI;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lombok.AllArgsConstructor;

@ApplicationScoped
@Path(UserResource.PATH)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@AllArgsConstructor
public class UserResource {

  public static final  String PATH = "/users";

  private final UserService userService;

  @POST
  public Response postUser(CreateUserRequest request) {
    return Response
        .created(URI.create(String.format("%s/%s", PATH, request.getUsername())))
        .entity(userService.createUser(request)).build();
  }

  @Path("/{username}")
  @GET
  public Response getUser(@PathParam("username") String username) {
    return Response.ok(userService.getUserByUsername(username)).build();
  }
}
