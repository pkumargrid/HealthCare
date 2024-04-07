package com.healthcare.system.repositories;

import com.healthcare.system.entities.Report;

import java.util.List;

public interface ReportRepository {
    void save(Report report);

    Report findById(int id);

    void deleteById(int id);

    void update(Report report);

    List<Report> findAll();
}
