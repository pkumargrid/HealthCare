package com.healthcare.system.repositories.implementation;

import com.healthcare.system.entities.Complaint;
import com.healthcare.system.repositories.ComplaintRepository;

import java.util.ArrayList;
import java.util.List;
public class ComplaintRepositoryImpl implements ComplaintRepository {

    private final List<Complaint> complaintList;

    public ComplaintRepositoryImpl() {
        complaintList = new ArrayList<>();
    }

    @Override
    public Complaint findById(int id) {
        return complaintList.stream().filter(c -> c.getId() == id).findFirst().get();
    }

    @Override
    public void deleteById(int id) {
        Complaint complaint = complaintList.stream().filter(c -> c.getId() == id).findFirst().get();
        complaintList.remove(complaint);
    }

    @Override
    public void update(Complaint complaint) {
        Complaint prevComplaint = complaintList.stream().filter(c -> c.getId() == complaint.getId()).findFirst().get();
        prevComplaint.setPatient(complaint.getPatient());
        prevComplaint.setText(complaint.getText());
    }

    @Override
    public List<Complaint> findAll() {
        return complaintList.stream().toList();
    }

    @Override
    public void save(Complaint complaint) {
        if (complaintList.stream().anyMatch(c -> c.getId() == complaint.getId())) {
            update(complaint);
            return;
        }
        complaintList.add(complaint);
    }
}
