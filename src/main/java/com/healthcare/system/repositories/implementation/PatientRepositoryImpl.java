package com.healthcare.system.repositories.implementation;

import com.healthcare.system.entities.Patient;
import com.healthcare.system.exceptions.WrongCredentials;
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
        return patients.stream().filter(patient -> patient.getId() == id).findFirst().orElse(null);
    }

    @Override
    public List<Patient> findAll() {
        return patients;
    }

    @Override
    public void save(Patient patient) throws WrongCredentials {
        if (patients.contains(patient)) {
            update(patient);
            return;
        }
        patients.add(patient);
    }

    @Override
    public void update(Patient patient) throws WrongCredentials {
        Patient prevPatient = patients.stream().filter(p -> p.getId() == patient.getId()).findFirst().orElse(null);
        if(prevPatient == null) {
            throw new WrongCredentials("Patient with id " + patient.getId() + " does not exist");
        }
        prevPatient.setPassword(patient.getPassword());
        prevPatient.setNurse(patient.getNurse());
        prevPatient.setName(patient.getName());
        prevPatient.setHealthProviderList(patient.getHealthProviderList());
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
    public void deleteById(int id) throws WrongCredentials {
        Patient patient = patients.stream().filter(patient1 -> patient1.getId() == id).findFirst().orElse(null);
        if(patient == null) {
            throw new WrongCredentials("Patient with id " + id + " does not exist");
        }
        patients.remove(patient);
    }

    @Override
    public List<Patient> findPatientByDoctorId(int id) {
        return null;
    }

    @Override
    public List<Patient> findByNurseId(int id) {
        return null;
    }

    @Override
    public List<Patient> findByHealthProviderById(int id) {
        return null;
    }
}
