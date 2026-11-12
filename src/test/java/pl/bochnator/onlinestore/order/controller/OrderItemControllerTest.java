package pl.bochnator.onlinestore.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.bochnator.onlinestore.order.model.CreateOrderItemDto;
import pl.bochnator.onlinestore.order.model.Order;
import pl.bochnator.onlinestore.order.model.OrderItem;
import pl.bochnator.onlinestore.order.service.OrderItemService;
import pl.bochnator.onlinestore.order.service.OrderService;
import pl.bochnator.onlinestore.product.model.Product;
import pl.bochnator.onlinestore.product.service.ProductService;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderItemController.class)
@Import(ObjectMapper.class)
public class OrderItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderItemService service;

    @MockitoBean
    private OrderService orderService;

    @MockitoBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateOrderItem() throws Exception {

        CreateOrderItemDto dto = new CreateOrderItemDto();
        dto.setQuantity(2);
        dto.setPrice(new BigDecimal("50.00"));
        dto.setProductName("Laptop");

        Order dummyOrder = new Order();
        Product dummyProduct = new Product();
        OrderItem savedOrderItem = new OrderItem();
        savedOrderItem.setOrder(dummyOrder);
        savedOrderItem.setProduct(dummyProduct);

        when(orderService.findById(any())).thenReturn(dummyOrder);

        when(productService.findById(any())).thenReturn(dummyProduct);

        when(service.create(any())).thenReturn(savedOrderItem);

        mockMvc.perform(post("/order-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldFindOrderItemById() throws Exception {


        Order dummyOrder = new Order();
        Product dummyProduct = new Product();
        OrderItem orderItem = new OrderItem();
        orderItem.setId(1L);
        orderItem.setOrder(dummyOrder);
        orderItem.setProduct(dummyProduct);

        Mockito.when(service.findById(1L)).thenReturn(orderItem);

        mockMvc.perform(get("/order-items/1"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldUpdateOrderItem() throws Exception {

        CreateOrderItemDto dto = new CreateOrderItemDto();
        dto.setQuantity(5);
        dto.setPrice(new BigDecimal("25.00"));
        dto.setProductName("Phone");


        Order dummyOrder = new Order();
        Product dummyProduct = new Product();
        OrderItem savedOrderItem = new OrderItem();
        savedOrderItem.setOrder(dummyOrder);
        savedOrderItem.setProduct(dummyProduct);
        Mockito.when(service.update(Mockito.eq(1L), any())).thenReturn(savedOrderItem);

        mockMvc.perform(put("/order-items/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteOrderItem() throws Exception {

        mockMvc.perform(delete("/order-items/1"))
                .andExpect(status().isOk());

        Mockito.verify(service).delete(1L);
    }
}
