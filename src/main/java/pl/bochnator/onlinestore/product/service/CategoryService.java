package pl.bochnator.onlinestore.product.service;

import org.springframework.stereotype.Service;
import pl.bochnator.onlinestore.product.model.Category;
import pl.bochnator.onlinestore.product.repository.CategoryRepository;

import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id).orElseThrow();
    }

    public Category update(Long id, Category updated) {
        Category category = findById(id);
        category.setName(updated.getName());
        category.setDescription(updated.getDescription());
        category.setParentCategory(updated.getParentCategory());
        return categoryRepository.save(category);
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

}
