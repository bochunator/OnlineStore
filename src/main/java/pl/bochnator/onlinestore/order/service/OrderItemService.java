package pl.bochnator.onlinestore.order.service;

import org.springframework.stereotype.Service;
import pl.bochnator.onlinestore.order.model.OrderItem;
import pl.bochnator.onlinestore.order.repository.OrderItemRepository;

import java.util.List;

@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public OrderItem create(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    public List<OrderItem> findAll() {
        return orderItemRepository.findAll();
    }

    public List<OrderItem> findByOrderId(Long orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }

    public OrderItem findById(Long id) {
        return orderItemRepository.findById(id).orElseThrow();
    }

    public OrderItem update(Long id, OrderItem updated) {
        OrderItem item = findById(id);
        item.setQuantity(updated.getQuantity());
        item.setPrice(updated.getPrice());
        item.setProductName(updated.getProductName());
        item.setProduct(updated.getProduct());
        return orderItemRepository.save(item);
    }

    public void delete(Long id) {
        orderItemRepository.deleteById(id);
    }
}
