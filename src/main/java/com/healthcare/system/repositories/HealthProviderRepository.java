package com.healthcare.system.repositories;

import com.healthcare.system.entities.HealthProvider;
import com.healthcare.system.exceptions.WrongCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HealthProviderRepository extends JpaRepository<HealthProvider,Integer> {


}
