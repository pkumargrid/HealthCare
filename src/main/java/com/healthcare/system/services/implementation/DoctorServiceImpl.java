package com.healthcare.system.services.implementation;

import com.healthcare.system.entities.*;
import com.healthcare.system.exceptions.ReasonTypeException;
import com.healthcare.system.exceptions.ResourceNotFoundException;
import com.healthcare.system.repositories.*;
import com.healthcare.system.services.DoctorService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final NurseRepository nurseRepository;

    private final AppointmentRepository appointmentRepository;

    private final ComplaintRepository complaintRepository;

    private HealthProviderRepository healthProviderRepository;

    private final ReasonRepository reasonRepository;

    public DoctorServiceImpl(DoctorRepository doctorRepository, NurseRepository nurseRepository, AppointmentRepository appointmentRepository,
                             ComplaintRepository complaintRepository, ReasonRepository reasonRepository) {
        this.doctorRepository = doctorRepository;
        this.nurseRepository = nurseRepository;
        this.appointmentRepository = appointmentRepository;
        this.complaintRepository = complaintRepository;
        this.reasonRepository = reasonRepository;
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
    public void update(Doctor doctor) {
        doctorRepository.update(doctor);
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
        Optional<Patient> patient_assigned_to_doctor =
                doctor.getPatientList().stream().filter(p -> p.getId() == patient.getId()).findFirst();

        if (patient_assigned_to_doctor.isEmpty()) {
            throw new IllegalArgumentException("Patient is not handled by doctor");
        }

        List<Nurse> nurseList = nurseRepository.findAll();

        if(nurseList == null || nurseList.isEmpty()) {
            throw new IllegalStateException("No nurse to assign");
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
    public void notifyReasonForComplaint(Reason reason) throws ReasonTypeException, ResourceNotFoundException {
        reasonRepository.save(reason);
        int id = -1;
        if (reason.getType() instanceof Doctor doctor) {
            id = doctor.getId();
        }
        else if (reason.getType() instanceof Nurse nurse) {
            id = nurse.getId();
        }
        else {
            throw new ReasonTypeException(400, "Holder of reason " + reason.getType() + " does not exist");
        }
        HealthProvider healthProvider = healthProviderRepository.getById(id);
        if (healthProvider == null) {
            throw new ResourceNotFoundException(HealthProvider.class.toString(), 404);
        }
    }


    @Override
    public List<Doctor> getByName(String name) {
        return doctorRepository.getByName(name);
    }
}
