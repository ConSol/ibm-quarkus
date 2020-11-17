package de.consol.dus.boundary.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CreateColorRequest {

  @NotNull
  @Pattern(regexp = "[0-9a-fA-F]{6}")
  private String hexdec;

  @NotNull
  @Size(max = 255)
  private String name;
}
