package com.healthcare.system.controllers;

import com.healthcare.system.dto.PatientDTO;
import com.healthcare.system.dto.ResponseCrudDTO;
import com.healthcare.system.entities.Patient;
import com.healthcare.system.exceptions.ValidationException;
import com.healthcare.system.exceptions.WrongCredentials;
import com.healthcare.system.services.PatientService;

import java.rmi.ServerException;
import java.util.List;

public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    public ResponseCrudDTO<Void> save(PatientDTO patientDTO) {
        try{
            patientService.register(patientDTO);
            return new ResponseCrudDTO<Void>("Saved Patient Successfully!",201,null);
        } catch (ValidationException v) {
            return new ResponseCrudDTO<Void>("Failed to Save!\n Reason: " + v.getMessage(),403,null);
        } catch (WrongCredentials w) {
            return new ResponseCrudDTO<Void>("Failed to Save!\n Reason: " + w.getMessage(), 401,null);
        } catch (ServerException e) {
            return new ResponseCrudDTO<Void>("Failed to Save!\n Reason: " + e.getMessage(), 500,null);
        }
    }

    public ResponseCrudDTO<List<Patient>> findAll() {
       try{
           List<Patient> patients = patientService.findAll();
           return new ResponseCrudDTO<>("Fetched Patients Successfully!", 200, patients);
       } catch (ServerException s) {
           return new ResponseCrudDTO<>("Failed to Save!\n Reason: " + s.getMessage(), 500,null);
       } catch (WrongCredentials w) {
           return new ResponseCrudDTO<>("Failed to Save!\n Reason: " + w.getMessage(), 401,null);
       }
    }
}
