package pl.bochnator.onlinestore.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.bochnator.onlinestore.cart.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
