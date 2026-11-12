package pl.bochnator.onlinestore.appuser.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.bochnator.onlinestore.appuser.model.AppUser;
import pl.bochnator.onlinestore.appuser.repository.AppUserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class AppUserServiceTest {
    private final AppUserRepository repository = Mockito.mock(AppUserRepository.class);
    private final AppUserService service = new AppUserService(repository);

    @Test
    void shouldCreateUser() {

        AppUser user = new AppUser();
        user.setEmail("test@test.com");

        Mockito.when(repository.save(Mockito.any(AppUser.class))).thenReturn(user);

        AppUser saved = service.create(user);

        assertEquals("test@test.com", saved.getEmail());
    }

    @Test
    void shouldFindUserById() {

        AppUser user = new AppUser();
        user.setId(1L);

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(user));

        AppUser result = service.findById(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void shouldUpdateUser() {

        AppUser existing = new AppUser();
        existing.setId(1L);
        existing.setEmail("old@test.com");

        AppUser update = new AppUser();
        update.setEmail("new@test.com");

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(existing));
        Mockito.when(repository.save(Mockito.any(AppUser.class))).thenReturn(existing);

        AppUser updated = service.update(1L, update);

        assertEquals("new@test.com", updated.getEmail());
    }

    @Test
    void shouldDeleteUser() {

        service.delete(1L);

        Mockito.verify(repository).deleteById(1L);
    }
}
