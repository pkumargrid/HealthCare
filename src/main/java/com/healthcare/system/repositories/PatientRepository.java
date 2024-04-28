package com.healthcare.system.repositories;

import com.healthcare.system.entities.HealthRecord;
import com.healthcare.system.entities.Patient;
import com.healthcare.system.exceptions.WrongCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

<<<<<<< HEAD
@Repository
public interface PatientRepository extends JpaRepository<Patient,Integer> {

    @Query("select h from HealthRecord h where h.patient.id = :id")
    HealthRecord findHealthRecordById(@Param("id") Integer id);
=======
public interface PatientRepository {
    Patient findById(int id);
    List<Patient> findAll();
    void save(Patient patient) throws WrongCredentials;
    void update(Patient patient) throws WrongCredentials;
    void deleteById(int id) throws WrongCredentials;

    List<Patient> findPatientByDoctorId(int id);
>>>>>>> 0d0b0e05f28cc077e7d42a1a22f542fe7df0398b
}
