package com.healthcare.system.controllers;

import com.healthcare.system.exceptions.AlreadyLoggedInException;
import com.healthcare.system.exceptions.ValidationException;
import com.healthcare.system.exceptions.WrongCredentials;
import com.healthcare.system.services.HealthProviderService;
import com.healthcare.system.services.NurseService;
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
@WebMvcTest(NurseController.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class NurseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NurseController nurseController;

    @MockBean
    private NurseService nurseService;

    @Test
    public void testRegister_Success() throws Exception {
        doNothing().when(nurseService).register(any());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/nurses/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"John\", \"password\": \"pass123\", \"email\": \"john@example.com\", \"healthProviderId\": 1}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string("Saved Nurse Successfully!"));

    }

    @Test
    public void testRegisterValidationException() throws Exception {
        doThrow(new ValidationException("Validation failed")).when(nurseService).register(any());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/nurses/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"John\", \"password\": \"pass123\", \"email\": \"john@example.com\", \"healthProviderId\": 1}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Validation failed"));
    }

    @Test
    public void testRegisterAlreadyLoggedInException() throws Exception {
        doThrow(new AlreadyLoggedInException("Already Logged in")).when(nurseService).register(any());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/nurses/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"John\", \"password\": \"pass123\", \"email\": \"john@example.com\", \"healthProviderId\": 1}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andExpect(MockMvcResultMatchers.content().string("Already Logged in"));
    }

    @Test
    public void testRegisterWrongCredentialsException() throws Exception {
        doThrow(new WrongCredentials("Wrong Credentials")).when(nurseService).register(any());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/nurses/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"John\", \"password\": \"pass123\", \"email\": \"john@example.com\", \"healthProviderId\": 1}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.content().string("Wrong Credentials"));
    }
}