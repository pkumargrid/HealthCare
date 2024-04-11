package com.healthcare.system.services;

import com.healthcare.system.entities.Complaint;
import com.healthcare.system.exceptions.ValidationException;
import com.healthcare.system.exceptions.WrongCredentials;

import java.util.List;

public interface ComplaintService {
    Complaint findById(int id) throws WrongCredentials;

    void deleteById(int id) throws WrongCredentials;

    void update(Complaint complaint) throws ValidationException, WrongCredentials;

    List<Complaint> findAll();

    void save(Complaint complaint) throws ValidationException, WrongCredentials;
}
