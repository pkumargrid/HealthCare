package com.healthcare.system.repositories;

import com.healthcare.system.entities.Appointment;
import com.healthcare.system.exceptions.WrongCredentials;

import java.util.List;

public interface AppointmentRepository {

    Appointment findById(int id);

    void update(Appointment appointment) throws WrongCredentials;

    List<Appointment> findAll();

    void deleteById(int id) throws WrongCredentials;

    void save(Appointment appointment) throws WrongCredentials;

}
