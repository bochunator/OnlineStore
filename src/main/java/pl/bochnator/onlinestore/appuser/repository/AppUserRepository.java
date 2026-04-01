package pl.bochnator.onlinestore.appuser.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.bochnator.onlinestore.appuser.model.AppUser;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmail(String email);
}
