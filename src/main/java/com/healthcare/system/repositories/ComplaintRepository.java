package com.healthcare.system.repositories;

import com.healthcare.system.entities.Complaint;
import com.healthcare.system.exceptions.WrongCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint,Integer> {

}
