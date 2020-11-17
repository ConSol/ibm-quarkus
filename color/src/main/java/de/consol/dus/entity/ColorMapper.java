package de.consol.dus.entity;

import de.consol.dus.boundary.request.CreateColorRequest;
import de.consol.dus.boundary.response.ColorResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface ColorMapper {
  ColorResponse entityToResponse(Color color);
  Color requestToEntity(CreateColorRequest request);
}
