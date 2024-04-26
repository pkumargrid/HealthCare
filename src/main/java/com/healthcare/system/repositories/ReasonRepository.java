package com.healthcare.system.repositories;

import com.healthcare.system.entities.Reason;
import com.healthcare.system.exceptions.WrongCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReasonRepository extends JpaRepository<Reason,Integer> {

}
