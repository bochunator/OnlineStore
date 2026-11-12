package pl.bochnator.onlinestore.order.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.bochnator.onlinestore.order.model.Order;
import pl.bochnator.onlinestore.order.repository.OrderRepository;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class OrderServiceTest {

    private final OrderRepository repository = Mockito.mock(OrderRepository.class);
    private final OrderService service = new OrderService(repository);

    @Test
    void shouldCreateOrder() {

        Order order = new Order();
        order.setTotalPrice(new BigDecimal("100.00"));

        Mockito.when(repository.save(Mockito.any(Order.class))).thenReturn(order);

        Order saved = service.create(order);

        assertNotNull(saved.getOrderNumber());
        assertNotNull(saved.getCreatedAt());
        assertNotNull(saved.getUpdatedAt());
    }

    @Test
    void shouldFindOrderById() {

        Order order = new Order();
        order.setId(1L);

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(order));

        Order result = service.findById(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void shouldUpdateOrder() {

        Order existing = new Order();
        existing.setId(1L);
        existing.setStatus("NEW");

        Order update = new Order();
        update.setStatus("PAID");
        update.setTotalPrice(new BigDecimal("250.00"));

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(existing));
        Mockito.when(repository.save(Mockito.any(Order.class))).thenReturn(existing);

        Order updated = service.update(1L, update);

        assertEquals("PAID", updated.getStatus());
        assertEquals(new BigDecimal("250.00"), updated.getTotalPrice());
    }

    @Test
    void shouldDeleteOrder() {

        service.delete(1L);

        Mockito.verify(repository).deleteById(1L);
    }
}
