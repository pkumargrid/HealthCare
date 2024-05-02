package com.healthcare.system.controllers;

import com.healthcare.system.exceptions.ValidationException;
import com.healthcare.system.exceptions.WrongCredentials;
import com.healthcare.system.services.DoctorService;
import com.healthcare.system.services.HealthProviderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

@AutoConfigureMockMvc
@WebMvcTest(HealthProviderController.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class HealthProviderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HealthProviderController healthProviderController;

    @MockBean
    private HealthProviderService healthProviderService;

    @Test
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
    public void testRegister_WrongCredentialsException() throws Exception {
        doThrow(new WrongCredentials("Wrong Credentials")).when(healthProviderService).register(any());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/healthcare/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"John\", \"password\": \"pass123\", \"email\": \"john@example.com\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.content().string("Wrong Credentials"));
    }

}