package com.healthcare.system.repositories;

import com.healthcare.system.entities.Appointment;
import com.healthcare.system.entities.Doctor;
import com.healthcare.system.entities.Nurse;
import com.healthcare.system.entities.Patient;
import java.util.List;

public interface DoctorRepository {


    void save(Doctor doctor);

    Doctor getById(int id);
    Doctor deleteById(int id);

    List<Doctor> getByName(String name);

    void updateById(int id, Doctor doctor);

    List<Doctor> findAll();

}
