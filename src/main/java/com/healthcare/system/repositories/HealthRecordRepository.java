package com.healthcare.system.repositories;

import com.healthcare.system.entities.HealthRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthRecordRepository extends JpaRepository<HealthRecord,Integer> {

}
