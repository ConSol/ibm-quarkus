package de.consol.dus.boundary;

import de.consol.dus.UserService;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lombok.AllArgsConstructor;

@ApplicationScoped
@Path(UserResource.PATH)
@Produces(MediaType.APPLICATION_JSON)
@AllArgsConstructor
public class UserResource {

  public static final  String PATH = "/users";

  private final UserService userService;

  @GET
  public Response getUser() {
    return Response.ok(userService.getUser()).build();
  }
}
