package pl.bochnator.onlinestore.product.service;

import org.springframework.stereotype.Service;
import pl.bochnator.onlinestore.product.model.Product;
import pl.bochnator.onlinestore.product.repository.ProductRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public Product create(Product product) {
        product.setCratedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        return productRepository.save(product);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow();
    }

    public Product update(Long id, Product updatedProduct) {
        Product product = findById(id);
        product.setName(updatedProduct.getName());
        product.setDescription(updatedProduct.getDescription());
        product.setPrice(updatedProduct.getPrice());
        product.setSku(updatedProduct.getSku());
        product.setStockQuantity(updatedProduct.getStockQuantity());
        product.setStatus(updatedProduct.getStatus());
        product.setCategory(updatedProduct.getCategory());
        product.setUpdatedAt(LocalDateTime.now());
        return productRepository.save(product);
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
