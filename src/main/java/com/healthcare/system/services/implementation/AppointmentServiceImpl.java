package com.healthcare.system.services.implementation;

import com.healthcare.system.entities.Appointment;
import com.healthcare.system.exceptions.ValidationException;
import com.healthcare.system.exceptions.WrongCredentials;
import com.healthcare.system.repositories.AppointmentRepository;
import com.healthcare.system.services.AppointmentService;

import java.util.List;

import static com.healthcare.system.util.Verification.verifyCredentials;

public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }
    @Override
    public Appointment findById(int id) throws WrongCredentials {
        if(appointmentRepository.findById(id) == null) {
            throw new WrongCredentials("Appointment with id: " + id + " does not exist");
        }
        return appointmentRepository.findById(id);
    }

    @Override
    public void update(Appointment appointment) throws ValidationException {
        verifyCredentials(Appointment.class,appointment);
        appointmentRepository.update(appointment);
    }

    @Override
    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    @Override
    public void deleteById(int id) throws WrongCredentials {
        if(appointmentRepository.findById(id) == null) {
            throw new WrongCredentials("Appointment with id: " + id + " does not exist");
        }
        appointmentRepository.deleteById(id);
    }

    @Override
    public void save(Appointment appointment) throws ValidationException {
        verifyCredentials(Appointment.class,appointment);
        appointmentRepository.save(appointment);
    }
}
