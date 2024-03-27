package com.healthcare.system.services;

import com.healthcare.system.entities.Complaint;

public interface ComplaintService {
    Complaint findById(int id);

    void deleteById(int id);

    void updateId(int id, Complaint complaint);

    List<Complaint> findAll();

    void save(Complaint complaint);
}
