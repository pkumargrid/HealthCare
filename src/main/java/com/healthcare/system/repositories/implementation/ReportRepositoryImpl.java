package com.healthcare.system.repositories.implementation;

import com.healthcare.system.entities.Report;
import com.healthcare.system.exceptions.WrongCredentials;
import com.healthcare.system.repositories.ReportRepository;

import java.util.ArrayList;
import java.util.List;

public class ReportRepositoryImpl implements ReportRepository {

    List<Report> reportList;

    public ReportRepositoryImpl() {
        reportList = new ArrayList<>();
    }
    @Override
    public void save(Report report) throws WrongCredentials {
        if(reportList.contains(report)) {
            update(report);
        }
        reportList.add(report);
    }

    @Override
    public Report findById(int id) {
        return reportList.stream().filter(r -> r.getId() == id).findFirst().get();
    }

    @Override
    public void deleteById(int id) throws WrongCredentials {
        Report report = reportList.stream().filter(r -> r.getId() == id).findFirst().orElse(null);
        if(report == null) {
            throw new WrongCredentials("Reason with id " + id + " does not exist");
        }
        reportList.remove(report);
    }

    @Override
    public void update(Report report) throws WrongCredentials {
        Report prevReport = reportList.stream().filter(r -> r.getId() == report.getId()).findFirst().orElse(null);
        if(prevReport == null) {
            throw new WrongCredentials("Reason with id " + report.getId() + " does not exist");
        }
        prevReport.setAdvice(report.getAdvice());
        prevReport.setCondition(report.getCondition());
    }

    @Override
    public List<Report> findAll() {
        return reportList.stream().toList();
    }
}
