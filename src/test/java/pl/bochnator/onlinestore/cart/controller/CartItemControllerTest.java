package pl.bochnator.onlinestore.cart.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.bochnator.onlinestore.cart.model.Cart;
import pl.bochnator.onlinestore.cart.model.CartItem;
import pl.bochnator.onlinestore.cart.model.CreateCartItemDto;
import pl.bochnator.onlinestore.cart.model.UpdateCartItemDto;
import pl.bochnator.onlinestore.cart.service.CartItemService;
import pl.bochnator.onlinestore.cart.service.CartService;
import pl.bochnator.onlinestore.product.model.Product;
import pl.bochnator.onlinestore.product.service.ProductService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartItemController.class)
@Import(ObjectMapper.class)

public class CartItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CartItemService service;

    @MockitoBean
    private CartService cartService;

    @MockitoBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateCartItem() throws Exception {

        CreateCartItemDto dto = new CreateCartItemDto();
        dto.setQuantity(2);

        Cart dummyCart = new Cart();
        Product dummyProduct = new Product();
        CartItem savedCartItem = new CartItem();
        savedCartItem.setCart(dummyCart);
        savedCartItem.setProduct(dummyProduct);

        when(productService.findById(any())).thenReturn(dummyProduct);

        when(cartService.findById(any())).thenReturn(dummyCart);

        when(service.create(any())).thenReturn(savedCartItem);

        mockMvc.perform(post("/cart-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldFindCartItemById() throws Exception {


        Cart dummyCart = new Cart();
        Product dummyProduct = new Product();

        CartItem cartItem = new CartItem();
        cartItem.setId(1L);
        cartItem.setCart(dummyCart);
        cartItem.setProduct(dummyProduct);

        Mockito.when(service.findById(1L)).thenReturn(cartItem);

        mockMvc.perform(get("/cart-items/1"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldUpdateCartItem() throws Exception {

        UpdateCartItemDto dto = new UpdateCartItemDto();
        dto.setQuantity(5);

        Cart dummyCart = new Cart();
        Product dummyProduct = new Product();
        CartItem savedCartItem = new CartItem();
        savedCartItem.setCart(dummyCart);
        savedCartItem.setProduct(dummyProduct);
        when(service.update(Mockito.eq(1L), any())).thenReturn(savedCartItem);

        mockMvc.perform(put("/cart-items/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteCartItem() throws Exception {

        mockMvc.perform(delete("/cart-items/1"))
                .andExpect(status().isOk());

        Mockito.verify(service).delete(1L);
    }
}
