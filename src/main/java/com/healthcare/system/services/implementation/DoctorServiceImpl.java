package com.healthcare.system.services.implementation;

import com.healthcare.system.entities.*;
import com.healthcare.system.exceptions.*;
import com.healthcare.system.repositories.*;
import com.healthcare.system.services.DoctorService;
import com.healthcare.system.session.SessionManager;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.healthcare.system.util.Verification.*;

public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final NurseRepository nurseRepository;

    private final AppointmentRepository appointmentRepository;

    private final HealthProviderRepository healthProviderRepository;

    private final ReasonRepository reasonRepository;

    private final PatientRepository patientRepository;

    public DoctorServiceImpl(DoctorRepository doctorRepository, NurseRepository nurseRepository, AppointmentRepository appointmentRepository, HealthProviderRepository healthProviderRepository,
                             ReasonRepository reasonRepository, PatientRepository patientRepository) {
        this.doctorRepository = doctorRepository;
        this.nurseRepository = nurseRepository;
        this.appointmentRepository = appointmentRepository;
        this.healthProviderRepository = healthProviderRepository;
        this.reasonRepository = reasonRepository;
        this.patientRepository = patientRepository;
    }
    @Override
    public void register(Doctor doctor) throws ValidationException {
        verifyPasswordWhileRegister(doctor.getPassword());
        List<Doctor> doctors = doctorRepository.findAll();
        List<String> usedEmails = doctors.stream().flatMap(d -> Stream.of(d.getEmail())).toList();
        verifyEmailWhileRegister(usedEmails, doctor.getEmail());
        verifyUserName(doctor.getEmail());
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
        patient.setNurse(nurse);
        patientRepository.save(patient);
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
            doctorRepository.getById(id).getReasons().add(reason);
        }
        else if (reason.getType() instanceof Nurse nurse) {
            id = nurse.getId();
            nurseRepository.findById(id).getReasons().add(reason);
        }
        else {
            throw new ReasonTypeException(400, "Holder of reason " + reason.getType() + " does not exist");
        }
        HealthProvider healthProvider = healthProviderRepository.getById(id);
        if (healthProvider == null) {
            throw new ResourceNotFoundException(HealthProvider.class.toString());
        }
        healthProvider.getReasons().add(reason);
    }

    @Override
    public void login(Doctor doctor) throws ValidationException, AlreadyLoggedInException {
        if (SessionManager.isAuthenticated(doctor.getSessionId())) {
            throw new AlreadyLoggedInException("Doctor: " + doctor.getEmail() + " is already logged in");
        }
        List<Doctor> doctors = doctorRepository.findAll();
        verifyEmailWhileLogin(doctors, doctor.getEmail());
        Doctor doctor1 = doctors.stream().filter(d -> d.getEmail().equals(doctor.getEmail())).findFirst().get();
        verifyPasswordWhileLogin(doctor1.getPassword(), doctor.getPassword());
        doctor1.setSessionId(SessionManager.generateSessionId(doctor.getEmail()));
    }


    @Override
    public void logout(String sessionId) throws AlreadyLoggedOutException {
        if(!SessionManager.isAuthenticated(sessionId)) {
            throw new AlreadyLoggedOutException("You are already logged out");
        }
        SessionManager.removeSessionId(sessionId);
    }

    @Override
    public List<Appointment> getAppointments(int doctorId) throws WrongCredentials {
        if(doctorRepository.getById(doctorId) == null) {
            throw new WrongCredentials("Doctor with id: " + doctorId + " does not exist");
        }
        return doctorRepository.getById(doctorId).getAppointmentList();
    }

    @Override
    public List<Complaint> getComplaints(int doctorId) throws WrongCredentials {
        if(doctorRepository.getById(doctorId) == null) {
            throw new WrongCredentials("Doctor with id: " + doctorId + " does not exist");
        }
        return doctorRepository.getById(doctorId).getComplaintList();
    }

    public List<Reason> getReasons(int doctorId) throws WrongCredentials {
        if(doctorRepository.getById(doctorId) == null) {
            throw new WrongCredentials("Doctor with id: " + doctorId + " does not exist");
        }
        return doctorRepository.getById(doctorId).getReasons();
    }

    @Override
    public List<Patient> getPatients(int doctorId) throws WrongCredentials {
        if(doctorRepository.getById(doctorId) == null) {
            throw new WrongCredentials("Doctor with id: " + doctorId + " does not exist");
        }
        return doctorRepository.getById(doctorId).getPatientList();
    }

    @Override
    public void save(Doctor doctor) {
        doctorRepository.save(doctor);
    }


    @Override
    public List<Doctor> getByName(String name) {
        return doctorRepository.getByName(name);
    }


}
