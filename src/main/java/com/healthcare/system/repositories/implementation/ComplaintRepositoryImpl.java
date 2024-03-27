package com.healthcare.system.repositories.implementation;

import com.healthcare.system.entities.Complaint;
import com.healthcare.system.repositories.ComplaintRepository;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
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
        complaintList = complaintList.stream().filter(c -> c.getId() != id).toList();
    }

    @Override
    public void updateId(int id, Complaint complaint) {
        Complaint prevComplaint = complaintList.stream().filter(c -> c.getId() == id).findFirst().get();
        prevComplaint.setPatient(complaint.getPatient());
        prevComplaint.setText(complaint.getText());
    }

    @Override
    public List<Complaint> findAll() {
        return complaintList.stream().toList();
    }

    @Override
    public void save(Complaint complaint) {
        complaintList.add(complaint);
    }
}
