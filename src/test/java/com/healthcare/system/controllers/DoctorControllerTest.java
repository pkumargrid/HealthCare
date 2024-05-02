package com.healthcare.system.controllers;

import com.healthcare.system.entities.Doctor;
import com.healthcare.system.exceptions.ValidationException;
import com.healthcare.system.exceptions.WrongCredentials;
import com.healthcare.system.services.DoctorService;
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

//@DataJpaTest
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DoctorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DoctorController doctorController;

    @MockBean
    private DoctorService doctorService;


    @Test
    @WithMockUser(username = "pkumar@gmail.com", password = "1234Aa#pra", roles = {"healthProvider"})
    public void testRegister_Success() throws Exception {
       doNothing().when(doctorService).register(any());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/doctors/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"John\", \"password\": \"pass123\", \"email\": \"john@example.com\", \"healthProviderId\": 1}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string("Saved Doctor successfully!"));

    }

    @Test
    @WithMockUser(username = "pkumar@gmail.com", password = "1234Aa#pra", roles = {"healthProvider"})
    public void testRegister_ValidationException() throws Exception {
        doThrow(new ValidationException("Validation failed")).when(doctorService).register(any());
         mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/doctors/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"John\", \"password\": \"pass123\", \"email\": \"john@example.com\", \"healthProviderId\": 1}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Validation failed"));
    }

    @Test
    @WithMockUser(username = "pkumar@gmail.com", password = "1234Aa#pra", roles = {"healthProvider"})
    public void testRegister_WrongCredentialsException() throws Exception {
        doThrow(new WrongCredentials("Wrong Credentials")).when(doctorService).register(any());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/doctors/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"John\", \"password\": \"pass123\", \"email\": \"john@example.com\", \"healthProviderId\": 1}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.content().string("Wrong Credentials"));
    }
    @Test
    @WithMockUser(username = "pkumar@gmail.com", password = "1234Aa#pra")
    public void testFindAll() throws Exception {
        Doctor doctor1 = Doctor.builder().id(1).name("ansh").email("a@gmail.com").password("1234#aAprat").build();
        Doctor doctor2 = Doctor.builder().id(2).name("pratik").email("p@gmail.com").password("18901#aAprat").build();
        given(doctorService.findAll()).willReturn(List.of(doctor1, doctor2));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/doctors/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.objects[0].email").value("a@gmail.com"));
    }


}