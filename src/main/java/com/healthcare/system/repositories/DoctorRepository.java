package com.healthcare.system.repositories;

import com.healthcare.system.entities.Doctor;
import com.healthcare.system.exceptions.WrongCredentials;

import java.util.List;

public interface DoctorRepository {


    void save(Doctor doctor) throws WrongCredentials;

    Doctor getById(int id);
    Doctor deleteById(int id) throws WrongCredentials;

    List<Doctor> getByName(String name);

    void update(Doctor doctor) throws WrongCredentials;

    List<Doctor> findAll();

}
