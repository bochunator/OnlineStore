package pl.bochnator.onlinestore.appuser.controller;

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
import pl.bochnator.onlinestore.appuser.model.CreateAppUserDto;
import pl.bochnator.onlinestore.appuser.service.AppUserService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AppUserController.class)
@Import(ObjectMapper.class)
public class AppUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AppUserService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateUser() throws Exception {

        CreateAppUserDto dto = new CreateAppUserDto();
        dto.setEmail("test@test.com");
        dto.setPassword("123");
        dto.setFirstName("Jan");
        dto.setLastName("Kowalski");

        when(service.create(any())).thenReturn(new AppUser());

        mockMvc.perform(post("/app-users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldFindUserById() throws Exception {

        AppUser user = new AppUser();
        user.setId(1L);

        Mockito.when(service.findById(1L)).thenReturn(user);

        mockMvc.perform(get("/app-users/1"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteUser() throws Exception {

        mockMvc.perform(delete("/app-users/1"))
                .andExpect(status().isOk());

        Mockito.verify(service).delete(1L);
    }
}
