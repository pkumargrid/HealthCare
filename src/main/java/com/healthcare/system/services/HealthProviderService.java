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

    HealthProvider getById(int id) throws WrongCredentials, ServerException;

    void deleteById(int id) throws WrongCredentials, ServerException;

    List<HealthProvider> getByName(String name) throws ServerException, WrongCredentials;


    void update(HealthProvider healthProvider) throws ValidationException, WrongCredentials, ServerException;


    List<HealthProvider> findAll() throws ServerException, WrongCredentials;

    void login(HealthProviderDTO healthProvider) throws ValidationException, AlreadyLoggedInException, ServerException, WrongCredentials;
    void logout(String sessionId) throws AlreadyLoggedOutException;
    void register(HealthProviderDTO healthProviderDTO) throws ValidationException, WrongCredentials, ServerException;

    void registerPatient(int healthProviderId, int patientId) throws WrongCredentials, ServerException;

    List<Doctor> getDoctors(int healthProviderId) throws WrongCredentials, ServerException;

    List<Nurse> getNurse(int healthProviderId) throws WrongCredentials, ServerException;

    List<Complaint> getComplaints(int healthProviderId) throws WrongCredentials, ServerException;

    List<HealthRecord> getHealthRecords(int healthProviderId) throws WrongCredentials, ServerException;

    List<Appointment> getAppointments(int healthProviderId) throws WrongCredentials, ServerException;

    List<Reason> getReasons(int healthProviderId) throws WrongCredentials, ServerException;
}
