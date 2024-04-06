package com.healthcare.system.repositories.implementation;

import com.healthcare.system.entities.Patient;
import com.healthcare.system.repositories.PatientRepository;

import java.util.ArrayList;
import java.util.List;

public class PatientRepositoryImpl implements PatientRepository {


    List<Patient> patients;
    public PatientRepositoryImpl(){
        patients = new ArrayList<>();
    }
    @Override
    public Patient findById(int id) {
        return patients.stream().filter(patient -> patient.getId() == id).findFirst().get();
    }

    @Override
    public List<Patient> findAll() {
        return patients;
    }

    @Override
    public void save(Patient patient) {
        if (patients.contains(patient)) {
            update(patient);
            return;
        }
        patients.add(patient);
    }

    @Override
    public void update(Patient patient) {
        Patient prevPatient = patients.stream().filter(p -> p.getId() == patient.getId()).findFirst().get();
        prevPatient.setPassword(patient.getPassword());
        prevPatient.setNurse(patient.getNurse());
        prevPatient.setName(patient.getName());
        prevPatient.setHealthProvidersList(patient.getHealthProvidersList());
        prevPatient.setHealthRecordList(patient.getHealthRecordList());
        prevPatient.setPassword(patient.getPassword());
        prevPatient.setId(patient.getId());
        prevPatient.setDoctorList(patient.getDoctorList());
        prevPatient.setName(patient.getName());
        prevPatient.setNurse(patient.getNurse());
        prevPatient.setPassword(patient.getPassword());
        prevPatient.setHealthRecordList(patient.getHealthRecordList());
    }

    @Override
    public void deleteById(int id) {
        Patient patient = patients.stream().filter(patient1 -> patient1.getId() == id).findFirst().get();
        patients.remove(patient);
    }
}
