package de.consol.dus.boundary.color;

import de.consol.dus.boundary.color.response.ColorResponse;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.faulttolerance.ExecutionContext;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.FallbackHandler;
import org.eclipse.microprofile.faulttolerance.Retry;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "color-rest")
@Produces(MediaType.APPLICATION_JSON)
@Path("colors")
public interface ColorRestClient {

  @GET
  @Path("{name}")
  @Retry(maxRetries = 10, delay = 1, delayUnit = ChronoUnit.SECONDS)
  @Fallback(ColorFallback.class)
  Optional<ColorResponse> getColorByName(@PathParam("name") @NotNull @Size(max = 255) String name);

  @Slf4j
  final class ColorFallback implements FallbackHandler<Optional<ColorResponse>> {

    @Override
    public Optional<ColorResponse> handle(ExecutionContext executionContext) {
      log.info("fallback called");
      return Optional.of(ColorResponse.builder()
          .hexdec("000000")
          .name("blackForSadTimes")
          .build());
    }
  }
}
