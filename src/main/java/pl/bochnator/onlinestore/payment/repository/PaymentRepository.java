package pl.bochnator.onlinestore.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.bochnator.onlinestore.payment.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
