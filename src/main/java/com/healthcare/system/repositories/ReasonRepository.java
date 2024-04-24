package com.healthcare.system.repositories;

import com.healthcare.system.entities.Reason;
import com.healthcare.system.exceptions.WrongCredentials;

import java.rmi.ServerException;
import java.util.List;

public interface ReasonRepository {

    void save(Reason reason) throws WrongCredentials, ServerException;

    Reason findById(int id) throws WrongCredentials,ServerException;

    void deleteById(int id) throws WrongCredentials,ServerException;

    void update(Reason reason) throws WrongCredentials,ServerException;

    List<Reason> findAll() throws WrongCredentials,ServerException;

    List<Reason> findReasonByType(int id, String tableName) throws ServerException, WrongCredentials;
}
