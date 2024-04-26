package com.healthcare.system.repositories;

import com.healthcare.system.entities.Nurse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NurseRepository extends JpaRepository<Nurse, Integer> {

}
