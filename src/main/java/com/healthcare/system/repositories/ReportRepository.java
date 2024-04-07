package com.healthcare.system.repositories;

import com.healthcare.system.entities.Reason;
import com.healthcare.system.entities.Report;

public interface ReportRepository {
    void save(Report report);

    Report findById(int id);

    void deleteById(int id);

    void update(Report report);
}
