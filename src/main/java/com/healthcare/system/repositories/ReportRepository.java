package com.healthcare.system.repositories;

import com.healthcare.system.entities.Report;
import com.healthcare.system.exceptions.WrongCredentials;

import java.rmi.ServerException;
import java.util.List;

public interface ReportRepository {
    void save(Report report) throws WrongCredentials, ServerException;

    Report findById(int id) throws WrongCredentials,ServerException;

    void deleteById(int id) throws WrongCredentials,ServerException;

    void update(Report report) throws WrongCredentials,ServerException;

    List<Report> findAll() throws WrongCredentials,ServerException;
}
