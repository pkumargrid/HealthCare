package com.healthcare.system.repositories;


import com.healthcare.system.entities.HealthRecord;
import com.healthcare.system.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient,Integer> {

    @Query("select h from HealthRecord h where h.patient.id = :id")
    HealthRecord findHealthRecordById(@Param("id") Integer id);

    Patient findByEmail(String email);
}
