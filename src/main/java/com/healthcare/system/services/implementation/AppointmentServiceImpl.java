package com.healthcare.system.services.implementation;

import com.healthcare.system.entities.Appointment;
import com.healthcare.system.repositories.AppointmentRepository;
import com.healthcare.system.services.AppointmentService;
import java.util.List;
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }
    @Override
    public Appointment findById(int id) {
        return appointmentRepository.findById(id);
    }

    @Override
    public void updateById(int id, Appointment appointment) {
        appointmentRepository.updateById(id, appointment);
    }

    @Override
    public List<Appointment> findAll() {
        return appointmentRepository.findAll();
    }

    @Override
    public void deleteById(int id) {
        appointmentRepository.deleteById(id);
    }

    @Override
    public void save(Appointment appointment) {
        appointmentRepository.save(appointment);
    }
}
