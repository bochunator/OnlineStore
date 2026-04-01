package pl.bochnator.onlinestore.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.bochnator.onlinestore.order.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
