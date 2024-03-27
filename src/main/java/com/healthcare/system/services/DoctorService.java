package com.healthcare.system.services;

import com.healthcare.system.entities.Doctor;
import com.healthcare.system.entities.Patient;

import java.util.List;
public interface DoctorService {
    void save(Doctor doctor);

    Doctor getById(int id);
    Doctor deleteById(int id);

    List<Doctor> getByName(String name);

    void updateById(int id, Doctor doctor);

    List<Doctor> findAll();

    void assignNurse(int id, Patient patient);

    void attendTeleMedConsultation();
}
