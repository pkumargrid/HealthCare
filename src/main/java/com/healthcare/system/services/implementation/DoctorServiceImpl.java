package com.healthcare.system.services.implementation;

import com.healthcare.system.entities.*;
import com.healthcare.system.repositories.AppointmentRepository;
import com.healthcare.system.repositories.ComplaintRepository;
import com.healthcare.system.repositories.DoctorRepository;
import com.healthcare.system.repositories.NurseRepository;
import com.healthcare.system.services.DoctorService;

import java.util.Collections;
import java.util.List;

public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final NurseRepository nurseRepository;

    private final AppointmentRepository appointmentRepository;

    private final ComplaintRepository complaintRepository;

    public DoctorServiceImpl(DoctorRepository doctorRepository, NurseRepository nurseRepository, AppointmentRepository appointmentRepository,
                             ComplaintRepository complaintRepository) {
        this.doctorRepository = doctorRepository;
        this.nurseRepository = nurseRepository;
        this.appointmentRepository = appointmentRepository;
        this.complaintRepository = complaintRepository;
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
        Patient patient_assigned_to_doctor =
                doctor.getPatientList().stream().filter(p -> p.getId() == patient.getId()).findFirst().get();

        if (patient_assigned_to_doctor == null) {
            throw new IllegalArgumentException("Patient is not handled by doctor");
        }

        List<Nurse> nurseList = nurseRepository.findAll();

        if(nurseList == null || nurseList.isEmpty()) {
            throw new IllegalArgumentException("No nurse to assign");
        }
        Collections.shuffle(nurseList);
        Nurse nurse = nurseList.get(0);
        nurse.getPatientList().add(patient);
    }

    @Override
    public void confirmAppointment(int id) {
        Appointment appointment = appointmentRepository.findById(id);
        if (appointment == null) {
            throw new IllegalArgumentException("Appointment with " + id + " not found");
        }
        appointment.setStatus(true);
    }

    @Override
    public void notifyReasonForComplaint(int id, String text) {


    }


    @Override
    public List<Doctor> getByName(String name) {
        return doctorRepository.getByName(name);
    }
}
