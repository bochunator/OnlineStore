package pl.bochnator.onlinestore.payment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.bochnator.onlinestore.order.model.Order;
import pl.bochnator.onlinestore.order.service.OrderService;
import pl.bochnator.onlinestore.payment.model.CreatePaymentDto;
import pl.bochnator.onlinestore.payment.model.Payment;
import pl.bochnator.onlinestore.payment.service.PaymentService;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaymentController.class)
@Import(ObjectMapper.class)
public class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PaymentService service;

    @MockitoBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreatePayment() throws Exception {

        CreatePaymentDto dto = new CreatePaymentDto();
        dto.setPaymentMethod("CARD");
        dto.setAmount(new BigDecimal("199.99"));


        Order dummyOrder = new Order();
        Payment savedPayment = new Payment();
        savedPayment.setOrder(dummyOrder);
        when(service.create(any())).thenReturn(savedPayment);

        mockMvc.perform(post("/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldFindPaymentById() throws Exception {

        Order dummyOrder = new Order();
        Payment payment = new Payment();
        payment.setId(1L);
        payment.setOrder(dummyOrder);

        Mockito.when(service.findById(1L)).thenReturn(payment);

        mockMvc.perform(get("/payments/1"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldUpdatePayment() throws Exception {

        CreatePaymentDto dto = new CreatePaymentDto();
        dto.setPaymentMethod("BLIK");
        dto.setAmount(new BigDecimal("299.99"));

        Order dummyOrder = new Order();
        Payment savedPayment = new Payment();
        savedPayment.setOrder(dummyOrder);
        Mockito.when(service.update(Mockito.eq(1L), any())).thenReturn(savedPayment);

        mockMvc.perform(put("/payments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeletePayment() throws Exception {



        Order dummyOrder = new Order();
        Payment savedPayment = new Payment();
        savedPayment.setId(1L);
        savedPayment.setOrder(dummyOrder);

        Mockito.when(service.findById(1L)).thenReturn(savedPayment);

        mockMvc.perform(delete("/payments/1"))
                .andExpect(status().isOk());

        Mockito.verify(service).delete(1L);
    }
}
