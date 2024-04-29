package com.healthcare.system.services;

import com.healthcare.system.entities.Complaint;
import com.healthcare.system.exceptions.ValidationException;
import com.healthcare.system.exceptions.WrongCredentials;

import java.rmi.ServerException;
import java.util.List;

public interface ComplaintService {
    Complaint findById(int id) throws WrongCredentials, ServerException;

    void deleteById(int id) throws WrongCredentials, ServerException;

    void update(Complaint complaint) throws ValidationException, WrongCredentials, ServerException;

    List<Complaint> findAll() throws ServerException, WrongCredentials;

    void save(Complaint complaint) throws ValidationException, WrongCredentials, ServerException;
}
