package com.healthcare.system.repositories;

import com.healthcare.system.entities.Report;
import com.healthcare.system.exceptions.WrongCredentials;

import java.util.List;

public interface ReportRepository {
    void save(Report report) throws WrongCredentials;

    Report findById(int id);

    void deleteById(int id) throws WrongCredentials;

    void update(Report report) throws WrongCredentials;

    List<Report> findAll();
}
