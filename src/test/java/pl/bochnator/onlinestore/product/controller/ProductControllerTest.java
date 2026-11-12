package pl.bochnator.onlinestore.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.bochnator.onlinestore.product.model.Category;
import pl.bochnator.onlinestore.product.model.CreateProductDto;
import pl.bochnator.onlinestore.product.model.Product;
import pl.bochnator.onlinestore.product.model.UpdateProductDto;
import pl.bochnator.onlinestore.product.service.CategoryService;
import pl.bochnator.onlinestore.product.service.ProductService;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@Import(ObjectMapper.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService service;

    @MockitoBean
    private CategoryService categoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateProduct() throws Exception {

        CreateProductDto dto = new CreateProductDto();
        dto.setName("Laptop");
        dto.setDescription("Gaming laptop");
        dto.setPrice(new BigDecimal("4999.99"));
        dto.setSku("LAP-001");
        dto.setStockQuantity(10);

        Product savedProduct = new Product();
        Category dummyCategory = new Category();
        savedProduct.setCategory(dummyCategory);

        when(service.create(any())).thenReturn(savedProduct);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldFindProductById() throws Exception {

        Category dummyCategory = new Category();
        Product product = new Product();
        product.setId(1L);
        product.setCategory(dummyCategory);

        Mockito.when(service.findById(1L)).thenReturn(product);

        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldUpdateProduct() throws Exception {

        UpdateProductDto dto = new UpdateProductDto();
        dto.setName("Updated Laptop");
        dto.setPrice(new BigDecimal("5999.99"));


        Category dummyCategory = new Category();
        Product savedProduct = new Product();
        savedProduct.setCategory(dummyCategory);
        when(service.update(Mockito.eq(1L), any())).thenReturn(savedProduct);

        mockMvc.perform(put("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteProduct() throws Exception {

        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isOk());

        Mockito.verify(service).delete(1L);
    }
}
