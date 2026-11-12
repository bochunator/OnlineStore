package pl.bochnator.onlinestore.payment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import pl.bochnator.onlinestore.cart.model.Cart;
import pl.bochnator.onlinestore.order.model.Order;
import pl.bochnator.onlinestore.order.service.OrderService;
import pl.bochnator.onlinestore.payment.model.CreatePaymentDto;
import pl.bochnator.onlinestore.payment.model.Payment;
import pl.bochnator.onlinestore.payment.model.PaymentResponseDto;
import pl.bochnator.onlinestore.payment.model.UpdatePaymentStatusDto;
import pl.bochnator.onlinestore.payment.service.PaymentService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/payments")
@Tag(name = "Payment API")
public class PaymentController {

    private final PaymentService paymentService;
    private final OrderService orderService;

    public PaymentController(PaymentService paymentService, OrderService orderService) {
        this.paymentService = paymentService;
        this.orderService = orderService;
    }

    @PostMapping
    @Operation(summary = "Create payment")
    public PaymentResponseDto create(@RequestBody CreatePaymentDto dto) {

        // TODO: Store all payment attempts (including failed ones).
        // Order should have 1:N relation with Payment to keep history.
        // TODO: Throw exception if order is already paid.

        Order order = orderService.findById(dto.getOrderId());

        Payment payment = new Payment();
        payment.setPaymentMethod(dto.getPaymentMethod());
        payment.setPaymentStatus("CREATED");
        payment.setAmount(dto.getAmount());
        payment.setOrder(order);

        Payment saved = paymentService.create(payment);
        return PaymentResponseDto.from(saved);
    }

    @GetMapping
    @Operation(summary = "Find all payments")
    public List<PaymentResponseDto> findAll() {
        return paymentService.findAll().stream().map(PaymentResponseDto::from).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find payment by id")
    public PaymentResponseDto findById(@PathVariable Long id) {

        Payment payment = paymentService.findById(id);
        return PaymentResponseDto.from(payment);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update payment")
    public PaymentResponseDto update(
            @PathVariable Long id,
            @RequestBody UpdatePaymentStatusDto dto) {

        Payment payment = new Payment();
        payment.setPaymentMethod(dto.getPaymentMethod());
        payment.setPaymentStatus(dto.getPaymentStatus());
        payment.setAmount(dto.getAmount());

        Payment updated = paymentService.update(id, payment);
        return PaymentResponseDto.from(updated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete payment")
    public void delete(@PathVariable Long id) {
        Payment payment = paymentService.findById(id);
        payment.getOrder().setPayment(null);
        paymentService.delete(id);
    }
}
