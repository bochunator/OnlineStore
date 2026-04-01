package pl.bochnator.onlinestore.payment.service;

import org.springframework.stereotype.Service;
import pl.bochnator.onlinestore.payment.model.Payment;
import pl.bochnator.onlinestore.payment.repository.PaymentRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public Payment create(Payment payment) {
        payment.setTransactionId(UUID.randomUUID().toString());
        payment.setCreatedAt(LocalDateTime.now());
        return paymentRepository.save(payment);
    }

    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    public Payment findById(Long id) {
        return paymentRepository.findById(id).orElseThrow();
    }

    public Payment update(Long id, Payment updated) {
        Payment payment = findById(id);
        payment.setPaymentMethod(updated.getPaymentMethod());
        payment.setPaymentStatus(updated.getPaymentStatus());
        payment.setAmount(updated.getAmount());
        return paymentRepository.save(payment);
    }

    public void delete(Long id) {
        paymentRepository.deleteById(id);
    }
}
