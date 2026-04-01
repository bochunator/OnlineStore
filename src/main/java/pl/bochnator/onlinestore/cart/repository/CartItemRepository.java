package pl.bochnator.onlinestore.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.bochnator.onlinestore.cart.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
