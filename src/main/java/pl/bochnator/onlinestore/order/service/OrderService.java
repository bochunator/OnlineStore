package pl.bochnator.onlinestore.order.service;

import org.springframework.stereotype.Service;
import pl.bochnator.onlinestore.order.model.Order;
import pl.bochnator.onlinestore.order.repository.OrderRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order create(Order order) {
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setCreatedAt(LocalDateTime.now());
        order.setUpdatedAt(LocalDateTime.now());
        return orderRepository.save(order);
    }

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow();
    }

    public Order update(Long id, Order updated) {
        Order order = findById(id);
        order.setStatus(updated.getStatus());
        order.setTotalPrice(updated.getTotalPrice());
        order.setPayment(updated.getPayment());
        order.setUpdatedAt(LocalDateTime.now());
        return orderRepository.save(order);
    }

    public void delete(Long id) {
        orderRepository.deleteById(id);
    }
}
