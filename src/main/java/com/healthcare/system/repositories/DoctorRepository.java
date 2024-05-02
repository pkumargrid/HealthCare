package com.healthcare.system.repositories;

import com.healthcare.system.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor,Integer> {
    Doctor findByEmail(String email);
}
