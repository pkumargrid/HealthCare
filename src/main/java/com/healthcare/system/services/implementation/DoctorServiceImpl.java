package com.healthcare.system.services.implementation;

import com.healthcare.system.entities.Appointment;
import com.healthcare.system.entities.Doctor;
import com.healthcare.system.entities.Patient;
import com.healthcare.system.repositories.AppointmentRepository;
import com.healthcare.system.repositories.DoctorRepository;
import com.healthcare.system.services.DoctorService;

import java.util.List;

public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    private final AppointmentRepository appointmentRepository;

    public DoctorServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }
    @Override
    public void save(Doctor doctor) {
        doctorRepository.save(doctor);
    }

    @Override
    public Doctor getById(int id) {
        return doctorRepository.getById(id);
    }

    @Override
    public Doctor deleteById(int id) {
        return doctorRepository.deleteById(id);
    }

    @Override
    public void updateById(int id, Doctor doctor) {
        doctorRepository.updateById(id, doctor);
    }

    @Override
    public List<Doctor> findAll() {
        return doctorRepository.findAll();
    }

    @Override
    public void assignNurse(int id, Patient patient) {
        Doctor doctor = doctorRepository.getById(id);
        if (doctor == null ) {
            throw new IllegalArgumentException("Id is not correct");
        }
        doctor.getPatientList().add(patient);
    }

    @Override
    public void attendTeleMedConsultation() {
        Doctor doctor = doctorRepository.getById(id);
        if (doctor == null ) {
            throw new IllegalArgumentException("Id is not correct");
        }
        Appointment appointment = new Appointment();
        doctor.getAppointmentList().add();
    }

    @Override
    public List<Doctor> getByName(String name) {
        return doctorRepository.getByName(name);
    }
}
