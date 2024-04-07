package com.healthcare.system.repositories.implementation;

import com.healthcare.system.entities.Reason;
import com.healthcare.system.entities.Report;
import com.healthcare.system.repositories.ReportRepository;

import java.util.ArrayList;
import java.util.List;

public class ReportRepositoryImpl implements ReportRepository {

    List<Report> reportList;

    public ReportRepositoryImpl() {
        reportList = new ArrayList<>();
    }
    @Override
    public void save(Report report) {
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
    public void deleteById(int id) {
        Report report = reportList.stream().filter(r -> r.getId() == id).findFirst().get();
        reportList.remove(report);
    }

    @Override
    public void update(Report report) {
        Report prevReport = reportList.stream().filter(r -> r.getId() == report.getId()).findFirst().get();
        prevReport.setAdvice(report.getAdvice());
        prevReport.setCondition(report.getCondition());
    }
}
