package pl.bochnator.onlinestore.cart.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.bochnator.onlinestore.cart.model.Cart;
import pl.bochnator.onlinestore.cart.repository.CartRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class CartServiceTest {

    private final CartRepository repository = Mockito.mock(CartRepository.class);
    private final CartService service = new CartService(repository);

    @Test
    void shouldCreateCart() {

        Cart cart = new Cart();
        cart.setId(1L);

        Mockito.when(repository.save(Mockito.any(Cart.class))).thenReturn(cart);

        Cart saved = service.create(cart);

        assertEquals(1L, saved.getId());
    }

    @Test
    void shouldFindCartById() {

        Cart cart = new Cart();
        cart.setId(1L);

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(cart));

        Cart result = service.findById(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void shouldUpdateCart() {

        Cart existing = new Cart();
        existing.setId(1L);

        Cart update = new Cart();

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(existing));
        Mockito.when(repository.save(Mockito.any(Cart.class))).thenReturn(existing);

        Cart updated = service.update(1L, update);

        assertEquals(1L, updated.getId());
    }

    @Test
    void shouldDeleteCart() {

        service.delete(1L);

        Mockito.verify(repository).deleteById(1L);
    }
}
