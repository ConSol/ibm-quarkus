package de.consol.dus.boundary;

import de.consol.dus.ColorService;
import de.consol.dus.boundary.request.CreateColorRequest;
import de.consol.dus.boundary.response.ColorResponse;
import de.consol.dus.boundary.response.ErrorResponse;
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
@Path(ColorResource.PATH)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@AllArgsConstructor
public class ColorResource {

  public static final String PATH = "/colors";

  private final ColorService colorService;

  @Operation(summary = "Create a new color.")
  @APIResponses(value = {
      @APIResponse(
          responseCode = "201",
          description = "When the newly created color was successfully created.",
          content = @Content(
              mediaType = MediaType.APPLICATION_JSON,
              schema = @Schema(implementation = ColorResponse.class))),
      @APIResponse(
          responseCode = "400",
          description = "When the name is longer than 255 characters, the hexdec is " +
              "malformed or a color with this name or hexdec already exists",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @Counted(name = "postColorCount", description = "How often POST /colors has been called")
  @Metered(name = "postColordMeter", description = "Meter information for POST /colors")
  @Timed(name = "postColorTimer", description = "How long it takes to to create a single color.")
  @POST
  public Response postUser(@Valid CreateColorRequest request) {
    return Response
        .created(URI.create(String.format("%s/%s", PATH, request.getName())))
        .entity(colorService.createColor(request)).build();
  }

  @Operation(summary = "Gets a color by its name (case-insensitive).")
  @APIResponses(value = {
      @APIResponse(
          responseCode = "200",
          description = "When the color was fetched.",
          content = @Content(
              mediaType = MediaType.APPLICATION_JSON,
              schema = @Schema(implementation = ColorResponse.class))),
      @APIResponse(
          responseCode = "404",
          description = "When no color with this name has been found.",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  @Counted(name = "getColorCount", description = "How often GET /colors/{name} has been called")
  @Metered(name = "getColorMeter", description = "Meter information GET /colors/{name}")
  @Timed(name = "getColorTimer", description = "How long it takes to to fetch a single color.")
  @Path("/{name}")
  @GET
  public Response getUser(@PathParam("name") String name) {
    return Response.ok(colorService.getColorByName(name)).build();
  }
}
