package com.healthcare.system.controllers;

import com.healthcare.system.entities.HealthProvider;
import com.healthcare.system.exceptions.ValidationException;
import com.healthcare.system.exceptions.WrongCredentials;
import com.healthcare.system.services.HealthProviderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class HealthProviderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HealthProviderController healthProviderController;

    @MockBean
    private HealthProviderService healthProviderService;

    @Test
    @WithMockUser(username = "apollo@gmail.com", password = "1234@Aabc#1", roles = {"healthProvider"})
    public void testRegister_Success() throws Exception {
        doNothing().when(healthProviderService).register(any());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/healthcare/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"John\", \"password\": \"pass123\", \"email\": \"john@example.com\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string("Saved HealthProvider successfully!"));

    }

    @Test
    @WithMockUser(username = "apollo@gmail.com", password = "1234@Aabc#1", roles = {"healthProvider"})
    public void testRegister_ValidationException() throws Exception {
        doThrow(new ValidationException("Validation failed")).when(healthProviderService).register(any());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/healthcare/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"John\", \"password\": \"pass123\", \"email\": \"john@example.com\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Validation failed"));
    }

    @Test
    @WithMockUser(username = "apollo@gmail.com", password = "1234@Aabc#1", roles = {"healthProvider"})
    public void testRegister_WrongCredentialsException() throws Exception {
        doThrow(new WrongCredentials("Wrong Credentials")).when(healthProviderService).register(any());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/healthcare/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"John\", \"password\": \"pass123\", \"email\": \"john@example.com\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.content().string("Wrong Credentials"));
    }

    @Test
    @WithMockUser(username = "apollo@gmail.com", password = "1234@Aabc#1")
    public void testFindAll() throws Exception {
        HealthProvider healthProvider1 = HealthProvider.builder().id(1).name("apollo").email("apollo@gmail.com").password("1234#aAprat").build();
        HealthProvider healthProvider2 = HealthProvider.builder().id(2).name("aiims").email("aiims@gmail.com").password("18901#aAprat").build();
        given(healthProviderService.findAll()).willReturn(List.of(healthProvider1, healthProvider2));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/healthcare/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.objects[0].email").value("apollo@gmail.com"));
    }

}