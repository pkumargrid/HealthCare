package com.healthcare.system.services;

import com.healthcare.system.entities.Reason;
import com.healthcare.system.exceptions.ValidationException;
import com.healthcare.system.exceptions.WrongCredentials;

import java.rmi.ServerException;

public interface ReasonService {

    void save(Reason reason) throws ValidationException, WrongCredentials, ServerException;

    Reason findById(int id) throws WrongCredentials, ServerException;

    void deleteById(int id) throws WrongCredentials, ServerException;

    void update(Reason reason) throws ValidationException, WrongCredentials, ServerException;

}
