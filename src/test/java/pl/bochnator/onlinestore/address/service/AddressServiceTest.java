package pl.bochnator.onlinestore.address.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.bochnator.onlinestore.address.model.Address;
import pl.bochnator.onlinestore.address.repository.AddressRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class AddressServiceTest {

    private final AddressRepository repository = Mockito.mock(AddressRepository.class);
    private final AddressService service = new AddressService(repository);

    @Test
    void shouldCreateAddress() {

        Address address = new Address();
        address.setStreet("Testowa 1");

        Mockito.when(repository.save(Mockito.any(Address.class))).thenReturn(address);

        Address saved = service.create(address);

        assertEquals("Testowa 1", saved.getStreet());
    }

    @Test
    void shouldFindAddressById() {

        Address address = new Address();
        address.setId(1L);

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(address));

        Address result = service.findById(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void shouldUpdateAddress() {

        Address existing = new Address();
        existing.setId(1L);
        existing.setCity("Warszawa");

        Address update = new Address();
        update.setCity("Gdańsk");
        update.setStreet("Nowa 5");

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(existing));
        Mockito.when(repository.save(Mockito.any(Address.class))).thenReturn(existing);

        Address updated = service.update(1L, update);

        assertEquals("Gdańsk", updated.getCity());
        assertEquals("Nowa 5", updated.getStreet());
    }

    @Test
    void shouldDeleteAddress() {

        service.delete(1L);

        Mockito.verify(repository).deleteById(1L);
    }
}
