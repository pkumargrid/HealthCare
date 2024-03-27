package com.healthcare.system.repositories;

import com.healthcare.system.entities.Patient;

import java.util.ArrayList;
import java.util.List;

public class PatientRepositoryImpl implements PatientRepository{


    List<Patient> patients;
    public PatientRepositoryImpl(){
        patients = new ArrayList<>();
    }
    @Override
    public Patient findById(Integer id) {
        return patients.stream().filter(patient -> patient.getId() == id).findFirst().get();
    }

    @Override
    public List<Patient> findAll() {
        return patients;
    }

    @Override
    public void save(Patient patient) {
        patients.add(patient);
    }

    @Override
    public void update(Patient patient) {
        return;
    }

    @Override
    public void deleteById(Integer id) {
        Patient patient = patients.stream().filter(patient1 -> patient1.getId() == id).findFirst().get();
        patients.remove(patient);
    }
}
