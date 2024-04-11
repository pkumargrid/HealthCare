package com.healthcare.system.repositories.implementation;

import com.healthcare.system.entities.Appointment;
import com.healthcare.system.exceptions.WrongCredentials;
import com.healthcare.system.repositories.AppointmentRepository;

import java.util.ArrayList;
import java.util.List;

public class AppointmentRepositoryImpl implements AppointmentRepository {

    private List<Appointment> appointmentList;

    public AppointmentRepositoryImpl() {
        this.appointmentList = new ArrayList<>();
    }
    @Override
    public Appointment findById(int id) {
        return appointmentList.stream().filter(a -> a.getId() == id).findFirst().orElseGet(()-> null);
    }

    @Override
    public void update(Appointment appointment) throws WrongCredentials {
        Appointment prevAppointment = appointmentList.stream().filter(a -> a.getId() == appointment.getId()).findFirst().orElseGet(() -> null);
        if(prevAppointment == null) throw new WrongCredentials("appointment with id " + appointment.getId() + " does not exist");
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
    public void deleteById(int id) throws WrongCredentials {
        if(appointmentList.get(id) == null) {
            throw new WrongCredentials("appointment with id " + id + " does not exist");
        }
        appointmentList = appointmentList.stream().filter(a -> a.getId() != id).toList();
    }

    @Override
    public void save(Appointment appointment) throws WrongCredentials {
        if(appointmentList.stream().anyMatch(app -> app.getId() == appointment.getId())) {
            update(appointment);
        }
        appointmentList.add(appointment);
    }
}
