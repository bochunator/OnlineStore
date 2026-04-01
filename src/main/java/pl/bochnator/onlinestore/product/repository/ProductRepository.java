package pl.bochnator.onlinestore.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.bochnator.onlinestore.product.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
