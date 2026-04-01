package pl.bochnator.onlinestore.product.controller;

import org.springframework.web.bind.annotation.*;
import pl.bochnator.onlinestore.product.model.*;
import pl.bochnator.onlinestore.product.service.CategoryService;
import pl.bochnator.onlinestore.product.service.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @PostMapping
    public ProductResponseDto create(@RequestBody CreateProductDto dto) {

        Category category = categoryService.findById(dto.getCategoryId());

        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setSku(dto.getSku());
        product.setStockQuantity(dto.getStockQuantity());
        product.setCategory(category);

        Product saved = productService.create(product);
        return ProductResponseDto.from(saved);
    }

    @GetMapping
    public List<ProductResponseDto> findAll() {
        return productService.findAll().stream().map(ProductResponseDto::from).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ProductResponseDto findById(@PathVariable Long id) {
        Product product = productService.findById(id);
        return ProductResponseDto.from(product);
    }

    @PutMapping("/{id}")
    public ProductResponseDto update(@PathVariable Long id, @RequestBody UpdateProductDto dto) {

        Category category = categoryService.findById(dto.getCategoryId());

        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStockQuantity(dto.getStockQuantity());
        product.setStatus(dto.getStatus());
        product.setCategory(category);

        Product updated = productService.update(id, product);
        return ProductResponseDto.from(updated);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }
}
