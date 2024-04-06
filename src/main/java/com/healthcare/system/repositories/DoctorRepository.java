package com.healthcare.system.repositories;

import com.healthcare.system.entities.Doctor;

import java.util.List;

public interface DoctorRepository {


    void save(Doctor doctor);

    Doctor getById(int id);
    Doctor deleteById(int id);

    List<Doctor> getByName(String name);

    void update(Doctor doctor);

    List<Doctor> findAll();

}
