package pl.bochnator.onlinestore.product.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import pl.bochnator.onlinestore.product.model.*;
import pl.bochnator.onlinestore.product.service.CategoryService;
import pl.bochnator.onlinestore.product.service.ProductService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
@Tag(name = "Product API")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @PostMapping
    @Operation(summary = "Create product")
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
    @Operation(summary = "Get all products")
    public List<ProductResponseDto> findAll() {
        return productService.findAll().stream().map(ProductResponseDto::from).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find product by id")
    public ProductResponseDto findById(@PathVariable Long id) {
        Product product = productService.findById(id);
        return ProductResponseDto.from(product);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update product")
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
    @Operation(summary = "Delete product")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }
}
