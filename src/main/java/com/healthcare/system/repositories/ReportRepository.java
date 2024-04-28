package com.healthcare.system.repositories;

import com.healthcare.system.entities.Report;
import com.healthcare.system.exceptions.WrongCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.rmi.ServerException;
import java.util.List;

<<<<<<< HEAD
@Repository
public interface ReportRepository extends JpaRepository<Report,Integer> {

=======
public interface ReportRepository {
    void save(Report report) throws WrongCredentials, ServerException;

    Report findById(int id) throws WrongCredentials,ServerException;

    void deleteById(int id) throws WrongCredentials,ServerException;

    void update(Report report) throws WrongCredentials,ServerException;

    List<Report> findAll() throws WrongCredentials,ServerException;
>>>>>>> 0d0b0e05f28cc077e7d42a1a22f542fe7df0398b
}
