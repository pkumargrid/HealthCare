package com.healthcare.system.repositories.implementation;

import com.healthcare.system.entities.Appointment;
import com.healthcare.system.repositories.AppointmentRepository;

import java.util.List;
import java.util.ArrayList;

public class AppointmentRepositoryImpl implements AppointmentRepository {

    private List<Appointment> appointmentList;

    public AppointmentRepositoryImpl() {
        this.appointmentList = new ArrayList<>();
    }
    @Override
    public Appointment findById(int id) {
        return appointmentList.stream().filter(a -> a.getId() == id).findFirst().get();
    }

    @Override
    public void update(Appointment appointment) {
        Appointment prevAppointment = appointmentList.stream().filter(a -> a.getId() == appointment.getId()).findFirst().get();
        prevAppointment.setDoctor(appointment.getDoctor());
        prevAppointment.setEndTime(appointment.getEndTime());
        prevAppointment.setStartTime(appointment.getStartTime());
        prevAppointment.setDoctor(appointment.getDoctor());
        prevAppointment.setPatient(appointment.getPatient());
    }

    @Override
    public List<Appointment> findAll() {
        return appointmentList.stream().toList();
    }

    @Override
    public void deleteById(int id) {
        appointmentList = appointmentList.stream().filter(a -> a.getId() != id).toList();
    }

    @Override
    public void save(Appointment appointment) {
        if(appointmentList.stream().anyMatch(app -> app.getId() == appointment.getId())) {
            update(appointment);
        }
        appointmentList.add(appointment);
    }
}
