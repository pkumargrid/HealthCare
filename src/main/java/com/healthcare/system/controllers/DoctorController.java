package com.healthcare.system.controllers;

import com.healthcare.system.controllers.dto.DoctorDTO;
import com.healthcare.system.controllers.dto.ResponseCrudDTO;
import com.healthcare.system.entities.Doctor;
import com.healthcare.system.exceptions.ValidationException;
import com.healthcare.system.services.DoctorService;

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
        }
    }

    public ResponseCrudDTO<List<Doctor>> findAll() {
        List<Doctor> doctors = doctorService.findAll();
        return new ResponseCrudDTO<>("Fetched Doctors Successfully!", 200, doctors);
    }


}
