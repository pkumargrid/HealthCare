package com.healthcare.system.controllers;

import com.healthcare.system.exceptions.ValidationException;
import com.healthcare.system.exceptions.WrongCredentials;
import com.healthcare.system.services.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

@AutoConfigureMockMvc
@WebMvcTest(PatientController.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PatientController patientController;

    @MockBean
    private PatientService patientService;

    @Test
    public void testRegisterSuccess() throws Exception {
        doNothing().when(patientService).register(any());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/patients/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"John\", \"password\": \"pass123\", \"email\": \"john@example.com\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string("Saved Patient Successfully!"));
    }

    @Test
    public void testRegisterValidationException() throws Exception {
        doThrow(new ValidationException("Validation failed")).when(patientService).register(any());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/patients/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"John\", \"password\": \"pass123\", \"email\": \"john@example.com\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Validation failed"));
    }

    @Test
    public void testRegisterWrongCredentials() throws Exception {
        doThrow(new WrongCredentials("Wrong Credentials")).when(patientService).register(any());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/patients/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"John\", \"password\": \"pass123\", \"email\": \"john@example.com\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.content().string("Wrong Credentials"));
    }
}