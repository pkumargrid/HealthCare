package com.healthcare.system.repositories;

import com.healthcare.system.entities.Doctor;
import com.healthcare.system.exceptions.WrongCredentials;

import java.rmi.ServerException;
import java.util.List;

public interface DoctorRepository {


    void save(Doctor doctor) throws WrongCredentials, ServerException;

    Doctor getById(int id) throws ServerException, WrongCredentials;
    void deleteById(int id) throws WrongCredentials, ServerException;

    List<Doctor> getByName(String name) throws ServerException, WrongCredentials;

    void update(Doctor doctor) throws WrongCredentials, ServerException;

    List<Doctor> findAll() throws ServerException, WrongCredentials;

    List<Doctor> findByNurseId(int id) throws ServerException, WrongCredentials;

    List<Doctor> findByHealthProviderById(int id) throws ServerException, WrongCredentials;

    List<Doctor> findByPatientId(int id) throws ServerException,WrongCredentials;
}
