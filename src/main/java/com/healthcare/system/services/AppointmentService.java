package com.healthcare.system.services;

import com.healthcare.system.entities.Appointment;

import java.util.List;

public interface AppointmentService {
    Appointment findById(int id);

    void updateById(int id, Appointment appointment);

    List<Appointment> findAll();

    void deleteById(int id);

    void save(Appointment appointment);
}
