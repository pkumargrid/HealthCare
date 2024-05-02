package com.healthcare.system.services;

import com.healthcare.system.entities.Appointment;
import com.healthcare.system.exceptions.ValidationException;
import com.healthcare.system.exceptions.WrongCredentials;

import java.rmi.ServerException;
import java.util.List;

public interface AppointmentService {
    Appointment findById(int id) throws WrongCredentials, ServerException;

    void update(Appointment appointment) throws ValidationException, WrongCredentials, ServerException;

    List<Appointment> findAll() throws ServerException;

    void deleteById(int id) throws WrongCredentials, ServerException;

    void save(Appointment appointment) throws ValidationException, WrongCredentials, ServerException;
}
