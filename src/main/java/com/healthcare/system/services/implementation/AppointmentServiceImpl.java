package com.healthcare.system.services.implementation;

import com.healthcare.system.entities.Appointment;
import com.healthcare.system.exceptions.ValidationException;
import com.healthcare.system.exceptions.WrongCredentials;
import com.healthcare.system.repositories.AppointmentRepository;
import com.healthcare.system.services.AppointmentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.healthcare.system.util.Verification.verifyCredentials;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }
    @Override
    public Appointment findById(int id) throws WrongCredentials {
        if(appointmentRepository.findById(id).equals(Optional.empty())) {
            throw new WrongCredentials("Appointment with id: " + id + " does not exist");
        }
        return appointmentRepository.findById(id).get();
    }

    @Override
    public void update(Appointment appointment) throws ValidationException, WrongCredentials {
        verifyCredentials(Appointment.class,appointment);
        appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    @Override
    public void deleteById(int id) throws WrongCredentials {
        if(appointmentRepository.findById(id).equals(Optional.empty())) {
            throw new WrongCredentials("Appointment with id: " + id + " does not exist");
        }
        appointmentRepository.deleteById(id);
    }

    @Override
    public void save(Appointment appointment) throws ValidationException, WrongCredentials {
        verifyCredentials(Appointment.class,appointment);
        appointmentRepository.save(appointment);
    }
}
