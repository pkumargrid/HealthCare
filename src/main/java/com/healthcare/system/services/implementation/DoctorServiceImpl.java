package com.healthcare.system.services.implementation;

import com.healthcare.system.dto.DoctorDTO;
import com.healthcare.system.entities.*;
import com.healthcare.system.exceptions.*;
import com.healthcare.system.mappers.DoctorMapper;
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

    private final HealthRecordRepository healthRecordRepository;

    public DoctorServiceImpl(DoctorRepository doctorRepository, NurseRepository nurseRepository, AppointmentRepository appointmentRepository, HealthProviderRepository healthProviderRepository,
                             ReasonRepository reasonRepository, PatientRepository patientRepository, HealthRecordRepository healthRecordRepository) {
        this.doctorRepository = doctorRepository;
        this.nurseRepository = nurseRepository;
        this.appointmentRepository = appointmentRepository;
        this.healthProviderRepository = healthProviderRepository;
        this.reasonRepository = reasonRepository;
        this.patientRepository = patientRepository;
        this.healthRecordRepository = healthRecordRepository;
    }
    @Override
    public void register(DoctorDTO doctorDTO) throws ValidationException, WrongCredentials {
        Doctor doctor = DoctorMapper.mapToDomain(doctorDTO,
                healthProviderRepository);
        verifyPasswordWhileRegister(doctor.getPassword());
        List<Doctor> doctors = doctorRepository.findAll();
        List<String> usedEmails = doctors.stream().flatMap(d -> Stream.of(d.getEmail())).toList();
        verifyEmailWhileRegister(usedEmails, doctor.getEmail());
        verifyUserName(doctor.getEmail());
        doctor.setId(doctors.stream().flatMap(d -> Stream.of(d.getId())).reduce(0,Integer::max) + 1);
        doctorRepository.save(doctor);
    }

    @Override
    public Doctor getById(int id) throws WrongCredentials {
        if(doctorRepository.getById(id) == null) {
            throw new WrongCredentials("Doctor with id: " + id + " does not exist");
        }
        return doctorRepository.getById(id);
    }

    @Override
    public Doctor deleteById(int id) throws WrongCredentials {
        if(doctorRepository.getById(id) == null) {
            throw new WrongCredentials("Doctor with id: " + id + " does not exist");
        }
        return doctorRepository.deleteById(id);
    }

    @Override
    public void update(Doctor doctor) throws ValidationException, WrongCredentials {
        verifyCredentials(Doctor.class,doctor);
        doctorRepository.update(doctor);
    }

    @Override
    public List<Doctor> findAll() {
        return doctorRepository.findAll();
    }

    @Override
    public void assignNurse(int id, Patient patient) throws WrongCredentials {
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
    public void notifyReasonForComplaint(Reason reason) throws ReasonTypeException, ResourceNotFoundException, WrongCredentials {
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
            throw new ReasonTypeException("Holder of reason " + reason.getType() + " does not exist");
        }
        HealthProvider healthProvider = healthProviderRepository.getById(id);
        if (healthProvider == null) {
            throw new ResourceNotFoundException(HealthProvider.class.toString());
        }
        healthProvider.getReasons().add(reason);
    }

    @Override
    public void login(DoctorDTO doctor) throws ValidationException, AlreadyLoggedInException {
        if (SessionManager.isAuthenticated(doctor.getSessionId())) {
            throw new AlreadyLoggedInException("Doctor: " + doctor.getEmail() + " is already logged in");
        }
        List<Doctor> doctors = doctorRepository.findAll();
        verifyEmailWhileLogin(doctors, doctor.getEmail());
        Doctor doctor1 = doctors.stream().filter(d -> d.getEmail().equals(doctor.getEmail())).findFirst().get();
        verifyPasswordWhileLogin(doctor1.getPassword(), doctor.getPassword());
        SessionManager.generateSessionId(doctor.getEmail());
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
    public void prescribePrescription(int doctorId, int patientId,  Prescription prescription) throws ValidationException, WrongCredentials {
        verifyCredentials(prescription.getClass(), prescription);
        HealthRecord healthRecord = new HealthRecord();
        List<HealthRecord> healthRecords = healthRecordRepository.findAll();
        int id = healthRecords.stream().flatMap(h -> Stream.of(h.getId())).mapToInt(i->i).max().orElseGet(()->1) + 1;
        healthRecord.setId(id);
        Doctor doctor = doctorRepository.getById(doctorId);
        if(doctor == null) throw new WrongCredentials("Doctor with id: " + id + " does not exist");
        healthRecord.setDoctor(doctor);
        healthRecord.setPrescription(prescription);
        Patient patient = patientRepository.findById(patientId);
        if(patient == null) throw new WrongCredentials("Patient with id: " + id + " does not exist");
        healthRecord.setPatient(patient);
        HealthProvider healthProvider = doctor.getHealthProvider();
        healthRecord.setHealthProvider(healthProvider);
        healthProvider.getHealthRecords().add(healthRecord);
        healthRecordRepository.save(healthRecord);
        doctorRepository.save(doctor);
        patientRepository.save(patient);
        healthProviderRepository.save(healthProvider);
    }

    @Override
    public void updatePrescription(int healthRecordId, Prescription prescription) throws ValidationException, WrongCredentials {
        verifyCredentials(prescription.getClass(), prescription);
        HealthRecord healthRecord = healthRecordRepository.getById(healthRecordId);
        if(healthRecord == null) {
            throw new WrongCredentials("HealthRecord with id: " + healthRecordId + " does not exist");
        }
        healthRecord.setPrescription(prescription);
        healthRecordRepository.save(healthRecord);
    }

    @Override
    public void save(Doctor doctor) throws ValidationException, WrongCredentials {
        verifyCredentials(Doctor.class,doctor);
        doctorRepository.save(doctor);
    }


    @Override
    public List<Doctor> getByName(String name) {
        return doctorRepository.getByName(name);
    }



}
