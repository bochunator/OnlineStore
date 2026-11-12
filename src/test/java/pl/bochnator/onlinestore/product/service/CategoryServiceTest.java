package pl.bochnator.onlinestore.product.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import pl.bochnator.onlinestore.product.model.Category;
import pl.bochnator.onlinestore.product.repository.CategoryRepository;

public class CategoryServiceTest {

    private final CategoryRepository repository = Mockito.mock(CategoryRepository.class);
    private final CategoryService service = new CategoryService(repository);

    @Test
    void shouldCreateCategory() {

        Category category = new Category();
        category.setName("Electronics");

        Mockito.when(repository.save(Mockito.any(Category.class))).thenReturn(category);

        Category saved = service.create(category);

        assertEquals("Electronics", saved.getName());
    }

    @Test
    void shouldFindCategoryById() {

        Category category = new Category();
        category.setId(1L);

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(category));

        Category result = service.findById(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void shouldUpdateCategory() {

        Category existing = new Category();
        existing.setId(1L);
        existing.setName("Old");

        Category update = new Category();
        update.setName("New");

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(existing));
        Mockito.when(repository.save(Mockito.any(Category.class))).thenReturn(existing);

        Category updated = service.update(1L, update);

        assertEquals("New", updated.getName());
    }

    @Test
    void shouldDeleteCategory() {

        service.delete(1L);

        Mockito.verify(repository).deleteById(1L);
    }
}
