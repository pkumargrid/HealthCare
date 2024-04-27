package com.healthcare.system.repositories;

import com.healthcare.system.entities.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report,Integer> {

}
