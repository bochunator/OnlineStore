package pl.bochnator.onlinestore.address.controller;

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
import pl.bochnator.onlinestore.address.model.CreateAddressDto;
import pl.bochnator.onlinestore.address.service.AddressService;
import pl.bochnator.onlinestore.appuser.model.AppUser;
import pl.bochnator.onlinestore.appuser.service.AppUserService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AddressController.class)
@Import(ObjectMapper.class)

public class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AddressService service;

    @MockitoBean
    private AppUserService appUserService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateAddress() throws Exception {

        CreateAddressDto dto = new CreateAddressDto();
        dto.setStreet("Testowa 1");
        dto.setCity("Warszawa");
        dto.setPostalCode("00-001");
        dto.setCountry("Polska");
        dto.setHomeNumber("123456789");

        AppUser dummyAppUser = new AppUser();
        Address savedAddress = new Address();
        savedAddress.setAppUser(dummyAppUser);
        when(service.create(any())).thenReturn(savedAddress);

        mockMvc.perform(post("/addresses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldFindAddressById() throws Exception {

        AppUser dummyAppUser = new AppUser();
        Address address = new Address();
        address.setAppUser(dummyAppUser);
        address.setId(1L);

        Mockito.when(service.findById(1L)).thenReturn(address);

        mockMvc.perform(get("/addresses/1"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldUpdateAddress() throws Exception {

        CreateAddressDto dto = new CreateAddressDto();
        dto.setStreet("Nowa 2");
        dto.setCity("Kraków");
        dto.setPostalCode("30-001");
        dto.setCountry("Polska");
        dto.setHomeNumber("987654321");

        AppUser dummyAppUser = new AppUser();
        Address savedAddress = new Address();
        savedAddress.setAppUser(dummyAppUser);
        Mockito.when(service.update(Mockito.eq(1L), any())).thenReturn(savedAddress);

        mockMvc.perform(put("/addresses/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteAddress() throws Exception {

        mockMvc.perform(delete("/addresses/1"))
                .andExpect(status().isOk());

        Mockito.verify(service).delete(1L);
    }
}
