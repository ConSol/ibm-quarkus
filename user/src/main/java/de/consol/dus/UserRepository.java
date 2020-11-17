package de.consol.dus;

import de.consol.dus.entity.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {

  public Optional<User> findByUsername(String username) {
    return find("LOWER(username)", username.toLowerCase()).firstResultOptional();
  }

  public Optional<User> findByEmail(String email) {
    return find("LOWER(email)", email.toLowerCase()).firstResultOptional();
  }
}
