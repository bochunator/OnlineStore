package pl.bochnator.onlinestore.cart.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.bochnator.onlinestore.cart.model.CartItem;
import pl.bochnator.onlinestore.cart.repository.CartItemRepository;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class CartItemServiceTest {

    private final CartItemRepository repository = Mockito.mock(CartItemRepository.class);
    private final CartItemService service = new CartItemService(repository);

    @Test
    void shouldCreateCartItem() {

        CartItem item = new CartItem();
        item.setQuantity(2);

        Mockito.when(repository.save(Mockito.any(CartItem.class))).thenReturn(item);

        CartItem saved = service.create(item);

        assertEquals(2, saved.getQuantity());
    }

    @Test
    void shouldFindCartItemById() {

        CartItem item = new CartItem();
        item.setId(1L);

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(item));

        CartItem result = service.findById(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void shouldUpdateCartItem() {

        CartItem existing = new CartItem();
        existing.setId(1L);
        existing.setQuantity(1);
        existing.setPrice(new BigDecimal("10"));

        CartItem update = new CartItem();
        update.setQuantity(5);
        update.setPrice(new BigDecimal("20"));

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(existing));
        Mockito.when(repository.save(Mockito.any(CartItem.class))).thenReturn(existing);

        CartItem updated = service.update(1L, update);

        assertEquals(5, updated.getQuantity());
        assertEquals(new BigDecimal("20"), updated.getPrice());
    }

    @Test
    void shouldDeleteCartItem() {

        service.delete(1L);

        Mockito.verify(repository).deleteById(1L);
    }
}
