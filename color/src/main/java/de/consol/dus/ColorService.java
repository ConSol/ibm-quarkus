package de.consol.dus;

import de.consol.dus.boundary.request.CreateColorRequest;
import de.consol.dus.boundary.response.ColorResponse;
import de.consol.dus.entity.Color;
import de.consol.dus.entity.ColorMapper;
import de.consol.dus.exception.EntityAlreadyExistsException;
import de.consol.dus.exception.NoSuchEntityException;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class ColorService {

  private final ColorRepository colorRepository;
  private final ColorMapper colorMapper;

  public ColorResponse getColorByName(String name) {
    return colorRepository.findByName(name)
        .map(colorMapper::entityToResponse)
        .orElseThrow(() -> new NoSuchEntityException(
            String.format("Color with name %s does not exist", name)));
  }

  @Transactional
  public ColorResponse createColor(CreateColorRequest request) {
    final String name = request.getName();
    if (colorRepository.findByName(name).isPresent()) {
      throw new EntityAlreadyExistsException(
          String.format("Color with name %s already exists", name));
    }
    final String hexdec = request.getHexdec();
    if (colorRepository.findByHexdec(hexdec).isPresent()) {
      throw new EntityAlreadyExistsException(
          String.format("Color with hexdec %s already exists", hexdec));
    }
    final Color toCreate = colorMapper.requestToEntity(request);
    colorRepository.persist(toCreate);
    return colorMapper.entityToResponse(toCreate);
  }

}
