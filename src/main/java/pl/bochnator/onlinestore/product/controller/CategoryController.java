package pl.bochnator.onlinestore.product.controller;

import org.springframework.web.bind.annotation.*;
import pl.bochnator.onlinestore.product.model.Category;
import pl.bochnator.onlinestore.product.model.CategoryResponseDto;
import pl.bochnator.onlinestore.product.model.CreateCategoryDto;
import pl.bochnator.onlinestore.product.model.UpdateCategoryDto;
import pl.bochnator.onlinestore.product.service.CategoryService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public CategoryResponseDto create(@RequestBody CreateCategoryDto dto) {

        Category parent = null;
        if (dto.getParentCategoryId() != null) {
            parent = categoryService.findById(dto.getParentCategoryId());
        }

        Category category = new Category();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category.setParentCategory(parent);

        Category saved = categoryService.create(category);
        return CategoryResponseDto.from(saved);
    }

    @GetMapping
    public List<CategoryResponseDto> findAll() {
        return categoryService.findAll().stream().map(CategoryResponseDto::from).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CategoryResponseDto findById(@PathVariable Long id) {

        Category category = categoryService.findById(id);
        return CategoryResponseDto.from(category);
    }

    @PutMapping("/{id}")
    public CategoryResponseDto update(@PathVariable Long id, @RequestBody UpdateCategoryDto dto) {

        Category parent = null;
        if (dto.getParentCategoryId() != null) {
            parent = categoryService.findById(dto.getParentCategoryId());
        }

        Category category = new Category();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category.setParentCategory(parent);

        Category updated = categoryService.update(id, category);
        return CategoryResponseDto.from(updated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        // TODO: add exception, you cannot category if you not delete subcategories first
        categoryService.delete(id);
    }
}
