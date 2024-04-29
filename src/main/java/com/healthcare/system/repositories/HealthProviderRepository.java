package com.healthcare.system.repositories;

import com.healthcare.system.entities.HealthProvider;
import com.healthcare.system.exceptions.WrongCredentials;

import java.rmi.ServerException;
import java.util.List;

public interface HealthProviderRepository {

    void save(HealthProvider healthProvider) throws WrongCredentials, ServerException;

    HealthProvider getById(int id) throws ServerException, WrongCredentials;

    HealthProvider deleteById(int id) throws WrongCredentials, ServerException;

    List<HealthProvider> getByName(String name) throws ServerException, WrongCredentials;

    void update(HealthProvider healthProvider) throws WrongCredentials, ServerException;

    List<HealthProvider> findAll() throws ServerException, WrongCredentials;

}
