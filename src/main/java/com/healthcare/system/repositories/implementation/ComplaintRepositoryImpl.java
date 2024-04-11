package com.healthcare.system.repositories.implementation;

import com.healthcare.system.entities.Complaint;
import com.healthcare.system.exceptions.WrongCredentials;
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
        return complaintList.stream().filter(c -> c.getId() == id).findFirst().orElseGet(()-> null);
    }

    @Override
    public void deleteById(int id) throws WrongCredentials {
        Complaint complaint = complaintList.stream().filter(c -> c.getId() == id).findFirst().orElseGet(()-> null);
        if(complaint == null) {
            throw new WrongCredentials("complaint with id " + id + " does not exist");
        }
        complaintList.remove(complaint);
    }

    @Override
    public void update(Complaint complaint) throws WrongCredentials {
        Complaint prevComplaint = complaintList.stream().filter(c -> c.getId() == complaint.getId()).findFirst().orElseGet(()-> null);
        if(prevComplaint == null) {
            throw new WrongCredentials("complaint with id " + complaint.getId() + " does not exist");
        }
        prevComplaint.setPatient(complaint.getPatient());
        prevComplaint.setText(complaint.getText());
    }

    @Override
    public List<Complaint> findAll() {
        return complaintList.stream().toList();
    }

    @Override
    public void save(Complaint complaint) throws WrongCredentials {
        if (complaintList.stream().anyMatch(c -> c.getId() == complaint.getId())) {
            update(complaint);
            return;
        }
        complaintList.add(complaint);
    }
}
