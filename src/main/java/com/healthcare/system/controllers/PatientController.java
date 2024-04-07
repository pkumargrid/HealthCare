package com.healthcare.system.controllers;

import com.healthcare.system.controllers.dto.ResponseSave;
import com.healthcare.system.entities.Doctor;
import com.healthcare.system.entities.Patient;
import com.healthcare.system.exceptions.ValidationException;
import com.healthcare.system.exceptions.WrongCredentials;
import com.healthcare.system.services.PatientService;

public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    public ResponseSave save(Patient patient) {
        try{
            patientService.savePatient(patient);
            return new ResponseSave("Saved Patient Successfully!");
        } catch (ValidationException v) {
            return new ResponseSave("Failed to Save!\n Reason: " + v.getMessage());
        }
    }

    public ResponseSave update(Patient patient) {
        try{
            patientService.updatePatient(patient);
            return new ResponseSave("Updated Patient Successfully!");
        } catch (ValidationException v) {
            return new ResponseSave("Failed to Save!\n Reason: " + v.getMessage());
        }
    }

    public ResponseSave deleteById(int id) {
        try{
            patientService.deletePatientById(id);
            return new ResponseSave("Deleted Patient Successfully!");
        } catch (WrongCredentials v) {
            return new ResponseSave("Failed to Delete!\n Reason: " + v.getMessage());
        }
    }

}
