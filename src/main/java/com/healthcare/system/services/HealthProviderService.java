package com.healthcare.system.services;

import com.healthcare.system.dto.HealthProviderDTO;
import com.healthcare.system.entities.*;
import com.healthcare.system.exceptions.AlreadyLoggedInException;
import com.healthcare.system.exceptions.AlreadyLoggedOutException;
import com.healthcare.system.exceptions.ValidationException;
import com.healthcare.system.exceptions.WrongCredentials;

import java.rmi.ServerException;
import java.util.List;

public interface HealthProviderService {
    void save(HealthProvider healthProvider) throws ValidationException, WrongCredentials, ServerException;

    HealthProvider getById(int id) throws WrongCredentials;

    HealthProvider deleteById(int id) throws WrongCredentials;

    List<HealthProvider> getByName(String name);


    void update(HealthProvider healthProvider) throws ValidationException, WrongCredentials;


    List<HealthProvider> findAll();

    void login(HealthProviderDTO healthProviderDTO) throws ValidationException, AlreadyLoggedInException;
    void logout(String sessionId) throws AlreadyLoggedOutException;
    void register(HealthProviderDTO healthProviderDTO) throws ValidationException, WrongCredentials;

    void registerPatient(int healthProviderId, int patientId) throws WrongCredentials;

    List<Doctor> getDoctors(int healthProviderId) throws WrongCredentials;

    List<Nurse> getNurse(int healthProviderId) throws WrongCredentials;

    List<Complaint> getComplaints(int healthProviderId) throws WrongCredentials;

    List<HealthRecord> getHealthRecords(int healthProviderId) throws WrongCredentials;

    List<Appointment> getAppointments(int healthProviderId) throws WrongCredentials;

    List<Reason> getReasons(int healthProviderId) throws WrongCredentials;
}
