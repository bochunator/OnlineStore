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
import pl.bochnator.onlinestore.product.model.CreateCategoryDto;
import pl.bochnator.onlinestore.product.model.UpdateCategoryDto;
import pl.bochnator.onlinestore.product.service.CategoryService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
@Import(ObjectMapper.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CategoryService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateCategory() throws Exception {

        CreateCategoryDto dto = new CreateCategoryDto();
        dto.setName("Electronics");
        dto.setDescription("Electronic devices");
        dto.setParentCategoryId(null);

        when(service.create(any())).thenReturn(new Category());

        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldFindCategoryById() throws Exception {

        Category category = new Category();
        category.setId(1L);

        Mockito.when(service.findById(1L)).thenReturn(category);

        mockMvc.perform(get("/categories/1"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldUpdateCategory() throws Exception {

        UpdateCategoryDto dto = new UpdateCategoryDto();
        dto.setName("Updated");
        dto.setDescription("Updated description");

        when(service.update(Mockito.eq(1L), any())).thenReturn(new Category());

        mockMvc.perform(put("/categories/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteCategory() throws Exception {

        mockMvc.perform(delete("/categories/1"))
                .andExpect(status().isOk());

        Mockito.verify(service).delete(1L);
    }
}
