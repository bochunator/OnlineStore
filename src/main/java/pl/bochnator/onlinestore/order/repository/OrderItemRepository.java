package pl.bochnator.onlinestore.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.bochnator.onlinestore.order.model.OrderItem;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByOrderId(Long orderId);
}
