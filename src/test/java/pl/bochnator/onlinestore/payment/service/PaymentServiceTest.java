package pl.bochnator.onlinestore.payment.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.bochnator.onlinestore.payment.model.Payment;
import pl.bochnator.onlinestore.payment.repository.PaymentRepository;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentServiceTest {

    private final PaymentRepository repository = Mockito.mock(PaymentRepository.class);
    private final PaymentService service = new PaymentService(repository);

    @Test
    void shouldCreatePayment() {

        Payment payment = new Payment();
        payment.setPaymentMethod("CARD");
        payment.setAmount(new BigDecimal("100.00"));

        Mockito.when(repository.save(Mockito.any(Payment.class))).thenReturn(payment);

        Payment saved = service.create(payment);

        assertEquals("CARD", saved.getPaymentMethod());
    }

    @Test
    void shouldFindPaymentById() {

        Payment payment = new Payment();
        payment.setId(1L);

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(payment));

        Payment result = service.findById(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void shouldUpdatePayment() {

        Payment existing = new Payment();
        existing.setId(1L);
        existing.setPaymentStatus("NEW");

        Payment update = new Payment();
        update.setPaymentStatus("PAID");
        update.setAmount(new BigDecimal("250.00"));

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(existing));
        Mockito.when(repository.save(Mockito.any(Payment.class))).thenReturn(existing);

        Payment updated = service.update(1L, update);

        assertEquals("PAID", updated.getPaymentStatus());
        assertEquals(new BigDecimal("250.00"), updated.getAmount());
    }

    @Test
    void shouldDeletePayment() {

        service.delete(1L);

        Mockito.verify(repository).deleteById(1L);
    }
}
