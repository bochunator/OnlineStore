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
import pl.bochnator.onlinestore.appuser.model.AppUser;
import pl.bochnator.onlinestore.appuser.service.AppUserService;
import pl.bochnator.onlinestore.cart.model.Cart;
import pl.bochnator.onlinestore.cart.model.CreateCartDto;
import pl.bochnator.onlinestore.cart.model.UpdateCartDto;
import pl.bochnator.onlinestore.cart.service.CartService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartController.class)
@Import(ObjectMapper.class)

public class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CartService service;

    @MockitoBean
    private AppUserService appUserService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateCart() throws Exception {

        CreateCartDto dto = new CreateCartDto();

        AppUser dummyAppUser = new AppUser();
        Cart savedCart = new Cart();
        savedCart.setAppUser(dummyAppUser);

        when(service.create(any())).thenReturn(savedCart);

        mockMvc.perform(post("/carts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldFindCartById() throws Exception {

        AppUser dummyAppUser = new AppUser();
        Cart cart = new Cart();
        cart.setId(1L);
        cart.setAppUser(dummyAppUser);

        Mockito.when(service.findById(1L)).thenReturn(cart);

        mockMvc.perform(get("/carts/1"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldUpdateCart() throws Exception {

        UpdateCartDto dto = new UpdateCartDto();

        AppUser dummyAppUser = new AppUser();
        Cart savedCart = new Cart();
        savedCart.setAppUser(dummyAppUser);
        when(service.update(Mockito.eq(1L), any())).thenReturn(savedCart);

        mockMvc.perform(put("/carts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteCart() throws Exception {

        AppUser dummyAppUser = new AppUser();
        Cart cart = new Cart();
        cart.setId(1L);
        cart.setAppUser(dummyAppUser);

        Mockito.when(service.findById(1L)).thenReturn(cart);

        mockMvc.perform(delete("/carts/1"))
                .andExpect(status().isOk());

        Mockito.verify(service).delete(1L);
    }
}
