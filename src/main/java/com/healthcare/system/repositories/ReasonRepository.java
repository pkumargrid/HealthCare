package com.healthcare.system.repositories;

import com.healthcare.system.entities.Reason;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReasonRepository extends JpaRepository<Reason, Integer> {

}
