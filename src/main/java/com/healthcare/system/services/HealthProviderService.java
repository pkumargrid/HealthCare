package com.healthcare.system.services;

import com.healthcare.system.dto.HealthProviderDTO;
import com.healthcare.system.entities.*;
import com.healthcare.system.exceptions.ValidationException;
import com.healthcare.system.exceptions.WrongCredentials;

import java.util.List;

public interface HealthProviderService {
    void save(HealthProvider healthProvider) throws ValidationException, WrongCredentials;

    HealthProvider getById(int id) throws WrongCredentials;

    void deleteById(int id) throws WrongCredentials;

    void update(HealthProvider healthProvider) throws ValidationException, WrongCredentials;


    List<HealthProvider> findAll();


    void register(HealthProviderDTO healthProviderDTO) throws ValidationException, WrongCredentials;

    void registerPatient(int healthProviderId, int patientId) throws WrongCredentials;

    List<Doctor> getDoctors(int healthProviderId) throws WrongCredentials;

    List<Nurse> getNurse(int healthProviderId) throws WrongCredentials;

    List<Complaint> getComplaints(int healthProviderId) throws WrongCredentials;

    List<HealthRecord> getHealthRecords(int healthProviderId) throws WrongCredentials;

    List<Appointment> getAppointments(int healthProviderId) throws WrongCredentials;

    List<Reason> getReasons(int healthProviderId) throws WrongCredentials;
}
