package com.healthcare.system.services;

import com.healthcare.system.entities.HealthProvider;
import com.healthcare.system.exceptions.AlreadyLoggedInException;
import com.healthcare.system.exceptions.AlreadyLoggedOutException;
import com.healthcare.system.exceptions.ValidationException;

import java.util.List;

public interface HealthProviderService {
    void save(HealthProvider healthProvider);

    HealthProvider getById(int id);

    HealthProvider deleteById(int id);

    List<HealthProvider> getByName(String name);

    void update(HealthProvider healthProvider);

    List<HealthProvider> findAll();

    void login(HealthProvider healthProvider) throws ValidationException, AlreadyLoggedInException;
    void logout(String sessionId) throws AlreadyLoggedOutException;
    void register(HealthProvider healthProvider) throws ValidationException;
}
