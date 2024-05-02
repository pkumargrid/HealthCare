package com.healthcare.system.repositories;

import com.healthcare.system.entities.HealthProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthProviderRepository extends JpaRepository<HealthProvider,Integer> {


    HealthProvider findByEmail(String email);
}
