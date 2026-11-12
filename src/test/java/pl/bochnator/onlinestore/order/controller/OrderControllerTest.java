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
import pl.bochnator.onlinestore.address.model.Address;
import pl.bochnator.onlinestore.address.service.AddressService;
import pl.bochnator.onlinestore.appuser.model.AppUser;
import pl.bochnator.onlinestore.appuser.service.AppUserService;
import pl.bochnator.onlinestore.order.model.CreateOrderDto;
import pl.bochnator.onlinestore.order.model.Order;
import pl.bochnator.onlinestore.order.service.OrderService;
import pl.bochnator.onlinestore.payment.service.PaymentService;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
@Import(ObjectMapper.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService service;

    @MockitoBean
    private AppUserService appUserService;

    @MockitoBean
    private AddressService addressServiceTest;

    @MockitoBean
    private PaymentService paymentServiceTest;


    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateOrder() throws Exception {

        CreateOrderDto dto = new CreateOrderDto();
        dto.setTotalPrice(new BigDecimal("100.00"));

        AppUser dummyAppUser = new AppUser();
        Address dummyAddress = new Address();
        Order savedOrder = new Order();
        savedOrder.setAppUser(dummyAppUser);
        savedOrder.setAddress(dummyAddress);

        when(appUserService.findById(any())).thenReturn(dummyAppUser);

        when(addressServiceTest.findById(any())).thenReturn(dummyAddress);

        when(service.create(any())).thenReturn(savedOrder);

        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldFindOrderById() throws Exception {

        AppUser dummyAppUser = new AppUser();
        Address dummyAddress = new Address();
        Order order = new Order();
        order.setId(1L);
        order.setAppUser(dummyAppUser);
        order.setAddress(dummyAddress);

        Mockito.when(service.findById(1L)).thenReturn(order);

        mockMvc.perform(get("/orders/1"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldUpdateOrder() throws Exception {

        CreateOrderDto dto = new CreateOrderDto();
        dto.setTotalPrice(new BigDecimal("200.00"));


        AppUser dummyAppUser = new AppUser();
        Address dummyAddress = new Address();
        Order savedOrder = new Order();
        savedOrder.setAppUser(dummyAppUser);
        savedOrder.setAddress(dummyAddress);

        Mockito.when(service.update(Mockito.eq(1L), any())).thenReturn(savedOrder);

        mockMvc.perform(put("/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteOrder() throws Exception {

        mockMvc.perform(delete("/orders/1"))
                .andExpect(status().isOk());

        Mockito.verify(service).delete(1L);
    }
}
