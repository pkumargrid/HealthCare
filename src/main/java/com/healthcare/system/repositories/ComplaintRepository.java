package com.healthcare.system.repositories;

import com.healthcare.system.entities.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplaintRepository extends JpaRepository<Complaint, Integer> {

}
