package de.consol.dus;

import de.consol.dus.entity.Color;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ColorRepository implements PanacheRepository<Color> {

  public Optional<Color> findByName(String name) {
    return find("LOWER(name)", name.toLowerCase()).firstResultOptional();
  }

  public Optional<Color> findByHexdec(String hexdec) {
    return find("LOWER(hexdec)", hexdec.toLowerCase()).firstResultOptional();
  }
}
