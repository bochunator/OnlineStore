package pl.bochnator.onlinestore.order.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.bochnator.onlinestore.order.model.OrderItem;
import pl.bochnator.onlinestore.order.repository.OrderItemRepository;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class OrderItemServiceTest {

    private final OrderItemRepository repository = Mockito.mock(OrderItemRepository.class);
    private final OrderItemService service = new OrderItemService(repository);

    @Test
    void shouldCreateOrderItem() {

        OrderItem item = new OrderItem();
        item.setProductName("Laptop");
        item.setQuantity(2);
        item.setPrice(new BigDecimal("100.00"));

        Mockito.when(repository.save(Mockito.any(OrderItem.class))).thenReturn(item);

        OrderItem saved = service.create(item);

        assertEquals("Laptop", saved.getProductName());
    }

    @Test
    void shouldFindOrderItemById() {

        OrderItem item = new OrderItem();
        item.setId(1L);

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(item));

        OrderItem result = service.findById(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void shouldUpdateOrderItem() {

        OrderItem existing = new OrderItem();
        existing.setId(1L);
        existing.setProductName("Old");

        OrderItem update = new OrderItem();
        update.setProductName("New");
        update.setQuantity(3);
        update.setPrice(new BigDecimal("200.00"));

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(existing));
        Mockito.when(repository.save(Mockito.any(OrderItem.class))).thenReturn(existing);

        OrderItem updated = service.update(1L, update);

        assertEquals("New", updated.getProductName());
        assertEquals(3, updated.getQuantity());
    }

    @Test
    void shouldDeleteOrderItem() {

        service.delete(1L);

        Mockito.verify(repository).deleteById(1L);
    }
}
