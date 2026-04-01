package pl.bochnator.onlinestore.order.controller;

import org.springframework.web.bind.annotation.*;
import pl.bochnator.onlinestore.address.model.Address;
import pl.bochnator.onlinestore.address.service.AddressService;
import pl.bochnator.onlinestore.appuser.model.AppUser;
import pl.bochnator.onlinestore.appuser.service.AppUserService;
import pl.bochnator.onlinestore.order.model.CreateOrderDto;
import pl.bochnator.onlinestore.order.model.Order;
import pl.bochnator.onlinestore.order.model.OrderResponseDto;
import pl.bochnator.onlinestore.order.model.UpdateOrderDto;
import pl.bochnator.onlinestore.order.service.OrderService;
import pl.bochnator.onlinestore.payment.model.Payment;
import pl.bochnator.onlinestore.payment.service.PaymentService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final AppUserService appUserService;
    private final PaymentService paymentService;
    private final AddressService addressService;

    public OrderController(OrderService orderService, AppUserService appUserService, PaymentService paymentService, AddressService addressService) {
        this.orderService = orderService;
        this.appUserService = appUserService;
        this.paymentService = paymentService;
        this.addressService = addressService;
    }

    @PostMapping
    public OrderResponseDto create(@RequestBody CreateOrderDto dto) {

        AppUser appUser = appUserService.findById(dto.getUserId());
        Address address = addressService.findById(dto.getAddressId());

        Order order = new Order();
        order.setAppUser(appUser);
        order.setAddress(address);
        order.setStatus("CREATED");
        order.setTotalPrice(dto.getTotalPrice());

        Order saved = orderService.create(order);
        return OrderResponseDto.from(saved);
    }

    @GetMapping
    public List<OrderResponseDto> findAll() {
        return orderService.findAll().stream().map(OrderResponseDto::from).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public OrderResponseDto findById(@PathVariable Long id) {

        Order order = orderService.findById(id);
        return OrderResponseDto.from(order);
    }

    @PutMapping("/{id}")
    public OrderResponseDto update(@PathVariable Long id, @RequestBody UpdateOrderDto dto) {

        Payment payment = null;
        if (dto.getPaymentId() != null) {
            payment = paymentService.findById(dto.getPaymentId());
        }

        Order order = new Order();
        order.setStatus(dto.getStatus());
        order.setTotalPrice(dto.getTotalPrice());
        order.setPayment(payment);

        Order updated = orderService.update(id, order);

        return OrderResponseDto.from(updated);
    }

    @DeleteMapping("/{id}")
    // TODO: You cannot delete order, if there are any order item left
    // TODO: Make exception or change logic: You cannot delete Order, you have to firstly delete Payment
    public void delete(@PathVariable Long id) {
        orderService.delete(id);
    }
}
