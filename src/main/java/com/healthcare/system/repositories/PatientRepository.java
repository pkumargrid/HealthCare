package com.healthcare.system.repositories;

import com.healthcare.system.entities.Patient;
import com.healthcare.system.exceptions.WrongCredentials;

import java.rmi.ServerException;
import java.util.List;

public interface PatientRepository {
    Patient findById(int id) throws WrongCredentials,ServerException;
    List<Patient> findAll() throws WrongCredentials,ServerException;
    void save(Patient patient) throws WrongCredentials, ServerException;
    void update(Patient patient) throws WrongCredentials,ServerException;
    void deleteById(int id) throws WrongCredentials,ServerException;

    List<Patient> findPatientByDoctorId(int id) throws WrongCredentials,ServerException;

    List<Patient> findByNurseId(int id) throws WrongCredentials,ServerException;

    List<Patient> findByHealthProviderById(int id) throws WrongCredentials,ServerException;
}
