package pl.bochnator.onlinestore.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.bochnator.onlinestore.appuser.model.AppUser;
import pl.bochnator.onlinestore.security.model.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
