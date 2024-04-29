package com.healthcare.system.repositories;

import com.healthcare.system.entities.Appointment;
import com.healthcare.system.exceptions.WrongCredentials;

import java.rmi.ServerException;
import java.util.List;

public interface AppointmentRepository {

    Appointment findById(int id) throws WrongCredentials, ServerException;

    void update(Appointment appointment) throws WrongCredentials, ServerException;

    List<Appointment> findAll() throws ServerException, WrongCredentials;

    void deleteById(int id) throws WrongCredentials, ServerException;

    void save(Appointment appointment) throws ServerException, WrongCredentials;

    List<Appointment> findByDoctorId(int id) throws ServerException, WrongCredentials;

    List<Appointment> findByPatientId(int id) throws ServerException, WrongCredentials;
}
