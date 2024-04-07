package com.healthcare.system.services;

import com.healthcare.system.entities.HealthProvider;
import com.healthcare.system.entities.Patient;
import com.healthcare.system.exceptions.AlreadyLoggedInException;
import com.healthcare.system.exceptions.AlreadyLoggedOutException;
import com.healthcare.system.exceptions.ValidationException;
import com.healthcare.system.exceptions.WrongCredentials;

import java.util.List;

public interface HealthProviderService {
    void save(HealthProvider healthProvider) throws ValidationException;

    HealthProvider getById(int id) throws WrongCredentials;

    HealthProvider deleteById(int id) throws WrongCredentials;

    List<HealthProvider> getByName(String name);


    void update(HealthProvider healthProvider) throws ValidationException;


    List<HealthProvider> findAll();

    void login(HealthProvider healthProvider) throws ValidationException, AlreadyLoggedInException;
    void logout(String sessionId) throws AlreadyLoggedOutException;
    void register(HealthProvider healthProvider) throws ValidationException;

    void registerPatient(HealthProvider healthProvider,Patient patient);
}
