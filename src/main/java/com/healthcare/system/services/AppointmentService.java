package com.healthcare.system.services;

import com.healthcare.system.entities.Appointment;
import com.healthcare.system.exceptions.ValidationException;
import com.healthcare.system.exceptions.WrongCredentials;

import java.util.List;

public interface AppointmentService {
    Appointment findById(int id) throws WrongCredentials;

    void update(Appointment appointment) throws ValidationException, WrongCredentials;

    List<Appointment> findAll();

    void deleteById(int id) throws WrongCredentials;

    void save(Appointment appointment) throws ValidationException, WrongCredentials;
}
