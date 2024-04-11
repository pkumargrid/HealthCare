package com.healthcare.system.repositories;

import com.healthcare.system.entities.Complaint;
import com.healthcare.system.exceptions.WrongCredentials;

import java.util.List;

public interface ComplaintRepository {
    Complaint findById(int id);

    void deleteById(int id) throws WrongCredentials;

    void update(Complaint complaint) throws WrongCredentials;

    List<Complaint> findAll();

    void save(Complaint complaint) throws WrongCredentials;
}
