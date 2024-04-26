package com.healthcare.system.repositories;

import com.healthcare.system.entities.HealthRecord;
import com.healthcare.system.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Integer> {
    HealthRecord findHealthRecordById(Integer id);
}
