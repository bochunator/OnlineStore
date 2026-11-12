package pl.bochnator.onlinestore.product.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.bochnator.onlinestore.product.model.Product;
import pl.bochnator.onlinestore.product.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ProductServiceTest {

    private final ProductRepository repository = Mockito.mock(ProductRepository.class);
    private final ProductService service = new ProductService(repository);

    @Test
    void shouldCreateProduct() {

        Product product = new Product();
        product.setName("Laptop");

        Mockito.when(repository.save(Mockito.any(Product.class))).thenReturn(product);

        Product saved = service.create(product);

        assertEquals("Laptop", saved.getName());
    }

    @Test
    void shouldFindProductById() {

        Product product = new Product();
        product.setId(1L);

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(product));

        Product result = service.findById(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void shouldUpdateProduct() {

        Product existing = new Product();
        existing.setId(1L);
        existing.setName("Old");
        existing.setPrice(new BigDecimal("100"));

        Product update = new Product();
        update.setName("New");
        update.setPrice(new BigDecimal("200"));

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(existing));
        Mockito.when(repository.save(Mockito.any(Product.class))).thenReturn(existing);

        Product updated = service.update(1L, update);

        assertEquals("New", updated.getName());
        assertEquals(new BigDecimal("200"), updated.getPrice());
    }

    @Test
    void shouldDeleteProduct() {

        service.delete(1L);

        Mockito.verify(repository).deleteById(1L);
    }
}
