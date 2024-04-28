package com.healthcare.system.repositories;

import com.healthcare.system.entities.Report;
import org.springframework.data.jpa.repository.JpaRepository;


import java.rmi.ServerException;
import java.util.List;

import org.springframework.stereotype.Repository;
@Repository
public interface ReportRepository extends JpaRepository<Report,Integer> {

}
