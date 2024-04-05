package com.healthcare.system.repositories.implementation;

import com.healthcare.system.entities.Doctor;
import com.healthcare.system.repositories.DoctorRepository;

import java.util.ArrayList;
import java.util.List;

public class DoctorRepositoryImpl implements DoctorRepository {

    List<Doctor> doctorList;

    public DoctorRepositoryImpl() {
        doctorList = new ArrayList<>();
    }

    @Override
    public void save(Doctor doctor) {
        if(doctorList.contains(doctor)) {
            update(doctor);
            return;
        }
        doctorList.add(doctor);
    }

    @Override
    public Doctor getById(int id) {
        return doctorList.stream().filter(doctor -> doctor.getId() == id).findFirst().get();
    }

    @Override
    public Doctor deleteById(int id) {
        Doctor doctor = doctorList.stream().filter(d -> d.getId() == id).findFirst().get();
        doctorList.remove(doctor);
        return doctor;
    }

    @Override
    public List<Doctor> getByName(String name) {
        return doctorList.stream().filter(d -> d.getName().equals(name)).toList();
    }

    @Override
    public void update(Doctor doctor) {
        Doctor prevDoctor = doctorList.stream().filter(d -> d.getId() == doctor.getId()).findFirst().get();
        prevDoctor.setEmail(doctor.getEmail());
        prevDoctor.setName(doctor.getName());
        prevDoctor.setAppointmentList(doctor.getAppointmentList());
        prevDoctor.setPassword(doctor.getPassword());
        prevDoctor.setComplaintList(doctor.getComplaintList());
        prevDoctor.setHealthProvider(doctor.getHealthProvider());
        prevDoctor.setPatientList(doctor.getPatientList());
    }

    @Override
    public List<Doctor> findAll() {
        return doctorList.stream().toList();
    }
}
