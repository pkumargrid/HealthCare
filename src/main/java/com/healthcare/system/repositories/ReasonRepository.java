package com.healthcare.system.repositories;

import com.healthcare.system.entities.Reason;
import com.healthcare.system.exceptions.WrongCredentials;

public interface ReasonRepository {

    void save(Reason reason) throws WrongCredentials;

    Reason findById(int id);

    void deleteById(int id) throws WrongCredentials;

    void update(Reason reason) throws WrongCredentials;
}
