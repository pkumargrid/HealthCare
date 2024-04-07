package com.healthcare.system.services;

import com.healthcare.system.entities.Reason;
import com.healthcare.system.exceptions.ValidationException;
import com.healthcare.system.exceptions.WrongCredentials;

public interface ReasonService {

    void save(Reason reason) throws ValidationException;

    Reason findById(int id) throws WrongCredentials;

    void deleteById(int id) throws WrongCredentials;

    void update(Reason reason) throws ValidationException;

}
