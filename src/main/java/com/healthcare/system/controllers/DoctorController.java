package com.healthcare.system.controllers;

import com.healthcare.system.dto.DoctorDTO;
import com.healthcare.system.dto.ResponseCrudDTO;
import com.healthcare.system.entities.Doctor;
import com.healthcare.system.exceptions.ValidationException;
import com.healthcare.system.exceptions.WrongCredentials;
import com.healthcare.system.services.DoctorService;

import java.rmi.ServerException;
import java.util.List;


public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    public ResponseCrudDTO<Void> register(DoctorDTO doctorDTO) {
        try{
            doctorService.register(doctorDTO);
            return new ResponseCrudDTO<Void>("Saved Doctor Successfully!", 201,null);
        } catch (ValidationException v) {
            return new ResponseCrudDTO<Void>("Failed to Save!\n Reason: " + v.getMessage(), 403,null);
        } catch (WrongCredentials w) {
            return new ResponseCrudDTO<Void>("Failed to Save!\n Reason: " + w.getMessage(), 401,null);
        } catch (ServerException e) {
            return new ResponseCrudDTO<Void>("Failed to Save!\n Reason: " + e.getMessage(), 500,null);
        }
    }

    public ResponseCrudDTO<List<Doctor>> findAll() {
        try {
            List<Doctor> doctors = doctorService.findAll();
            return new ResponseCrudDTO<>("Fetched Doctors Successfully!", 200, doctors);
        } catch (ServerException e) {
            return new ResponseCrudDTO<>("Failed to Retrieve!\n Reason: " + e.getMessage(), 500,null);
        } catch (WrongCredentials e) {
            return new ResponseCrudDTO<>("Failed to Retrieve!\n Reason: " + e.getMessage(), 401,null);
        }
    }


}
