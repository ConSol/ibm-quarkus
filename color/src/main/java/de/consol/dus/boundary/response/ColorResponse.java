package de.consol.dus.boundary.response;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class ColorResponse {
  String hexdec;
  String name;
}
