package com.healthcare.system.services;

import com.healthcare.system.entities.*;
import com.healthcare.system.exceptions.AlreadyLoggedInException;
import com.healthcare.system.exceptions.AlreadyLoggedOutException;
import com.healthcare.system.exceptions.ValidationException;
import com.healthcare.system.exceptions.WrongCredentials;

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

    void registerPatient(HealthProvider healthProvider,Patient patient);

    List<Doctor> getDoctors(int healthProviderId) throws WrongCredentials;

    List<Nurse> getNurse(int healthProviderId) throws WrongCredentials;

    List<Complaint> getComplaints(int healthProviderId) throws WrongCredentials;

    List<HealthRecord> getHealthRecords(int healthProviderId) throws WrongCredentials;

    List<Appointment> getAppointments(int healthProviderId);

    List<Reason> getReasons(int healthProviderId) throws WrongCredentials;
}
