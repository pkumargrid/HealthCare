package com.healthcare.system.services.implementation;

import com.healthcare.system.dto.PatientDTO;
import com.healthcare.system.entities.*;
import com.healthcare.system.exceptions.*;
import com.healthcare.system.mappers.PatientMapper;
import com.healthcare.system.repositories.*;
import com.healthcare.system.services.PatientService;
import com.healthcare.system.session.SessionManager;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.healthcare.system.util.Verification.*;

@Service
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final NurseRepository nurseRepository;
    private final ComplaintRepository complaintRepository;

    private final HealthProviderRepository healthProviderRepository;

    public PatientServiceImpl(PatientRepository patientRepository, AppointmentRepository appointmentRepository, DoctorRepository doctorRepository, NurseRepository nurseRepository, ComplaintRepository complaintRepository, HealthProviderRepository healthProviderRepository) {
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.nurseRepository = nurseRepository;
        this.complaintRepository = complaintRepository;
        this.healthProviderRepository = healthProviderRepository;
    }

    @Override
    public Patient findById(int id) throws WrongCredentials {
        if(patientRepository.findById(id).equals(Optional.empty())) {
            throw new WrongCredentials("Patient with id: " + id + " does not exist");
        }
        return patientRepository.findById(id).get();
    }

    @Override
    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    @Override
    public void savePatient(Patient patient) throws ValidationException, WrongCredentials {
        verifyCredentials(Patient.class,patient);
        patientRepository.save(patient);
    }

    @Override
    public void updatePatient(Patient patient) throws ValidationException, WrongCredentials {
        verifyCredentials(Patient.class,patient);
        patientRepository.save(patient);
    }

    @Override
    public void deletePatientById(int id) throws WrongCredentials {
        if(patientRepository.findById(id).equals(Optional.empty())) {
            throw new WrongCredentials("Patient with id: " + id + " does not exist");
        }
        patientRepository.deleteById(id);
    }

    public void bookAppointments(Appointment appointment) throws AppointmentTimeException, WrongCredentials {
        List<Appointment> doctorAppointmentList = appointment.getPatient().getAppointmentList();
        int[] range = new int[601];
        for(var doctorAppointment : doctorAppointmentList) {
            LocalDateTime start = doctorAppointment.getStartTime();
            int startHour = start.getHour();
            int startMinute = start.getMinute();
            int difference = (startHour-10)*60 + startMinute;
            range[difference] += 1;
            if(difference+15 <= 600)
                range[difference+15] -= 1;
            else
                range[600] -= 1;
        }

        for(int i=1;i<=600;i++) {
            range[i] = range[i-1]+range[i];
        }
        int startTime = -1;
        for(int i = 0;i<=585;i++) {
            if(range[i]==0&&range[i+15]==0) {
                startTime = i;
            }
        }
        if(startTime!=-1) {
            LocalDate currentDate = LocalDate.now();
            LocalTime start = LocalTime.of(startTime/60+10,startTime%60);
            int endTime = startTime+15;
            LocalTime end = LocalTime.of(endTime/60 + 10,endTime%60);
            appointment.setStartTime(LocalDateTime.of(currentDate,start));
            appointment.setEndTime(LocalDateTime.of(currentDate,end));
            appointmentRepository.save(appointment);
            appointment.getPatient().getAppointmentList().add(appointment);
            patientRepository.save(appointment.getPatient());
            appointment.getDoctor().getAppointmentList().add(appointment);
            doctorRepository.save(appointment.getDoctor());
            appointment.getDoctor().getHealthProvider().getAppointmentList().add(appointment);
            appointment.setId(appointmentRepository.findAll().stream().flatMap(a ->Stream.of(a.getId())).reduce(0, Integer::max) + 1);
            healthProviderRepository.save(appointment.getDoctor().getHealthProvider());
        }
        else {
            throw new AppointmentTimeException("Time slots not available");
        }

    }

    @Override
    public void createComplaint(Complaint complaint, String type, int id) throws WrongCredentials {
        complaint.getPatient().getComplaintList().add(complaint);
        if(type.equals("Doctor")) {
            Doctor doctor = doctorRepository.getById(id);
            doctor.getComplaintList().add(complaint);
            HealthProvider healthProvider = doctor.getHealthProvider();
            healthProvider.getComplaintList().add(complaint);
            doctorRepository.save(doctor);
            healthProviderRepository.save(healthProvider);
        }
        else if(type.equals("Nurse")) {
            Nurse nurse = nurseRepository.findById(id).get();
            nurse.getComplaintList().add(complaint);
            nurse.getHealthProvider().getComplaintList().add(complaint);
            HealthProvider healthProvider = nurse.getHealthProvider();
            healthProvider.getComplaintList().add(complaint);
            nurseRepository.save(nurse);
            healthProviderRepository.save(healthProvider);
        }
        List<Complaint> complaints = complaintRepository.findAll();
        complaint.setId(complaints.stream().flatMap(c -> Stream.of(c.getId())).reduce(0,Integer::max) + 1);
        complaintRepository.save(complaint);
    }

    @Override
<<<<<<< HEAD
    public HealthRecord accessPatientRecord(Patient patient) {
        return null;
    }

    @Override
    public void login(Patient patient) throws ValidationException, AlreadyLoggedInException {
=======
    public void login(PatientDTO patient) throws ValidationException, AlreadyLoggedInException {
>>>>>>> 0d0b0e05f28cc077e7d42a1a22f542fe7df0398b
        if (SessionManager.isAuthenticated(patient.getSessionId())) {
            throw new AlreadyLoggedInException("Patient: " + patient.getEmail() + " is already logged in");
        }
        List<Patient> patients = patientRepository.findAll();
        verifyEmailWhileLogin(patients, patient.getEmail());
        Patient patient1 = patients.stream().filter(p -> p.getEmail().equals(patient.getEmail())).findFirst().get();
        verifyPasswordWhileLogin(patient1.getPassword(), patient.getPassword());
        SessionManager.generateSessionId(patient.getEmail());
    }

    @Override
    public void logout(String sessionId) throws AlreadyLoggedOutException {
        if(!SessionManager.isAuthenticated(sessionId)) {
            throw new AlreadyLoggedOutException("You are already logged out");
        }
        SessionManager.removeSessionId(sessionId);
    }

    @Override
    public void register(PatientDTO patientDTO) throws ValidationException, WrongCredentials {
        Patient patient = PatientMapper.mapToDomain(patientDTO);
        verifyPasswordWhileRegister(patient.getPassword());
        List<Patient> patients = patientRepository.findAll();
        List<String> usedEmails = patients.stream().flatMap(d -> Stream.of(d.getEmail())).toList();
        verifyEmailWhileRegister(usedEmails, patient.getEmail());
        verifyUserName(patient.getEmail());
        patient.setId(patients.stream().flatMap(d -> Stream.of(d.getId())).reduce(0,Integer::max) + 1);
        patientRepository.save(patient);
    }

    @Override
    public List<HealthProvider> getHealthProviders(int patientId) throws WrongCredentials {
        if(patientRepository.findById(patientId).equals(Optional.empty())) {
            throw new WrongCredentials("Patient with id: " + patientId + " does not exist");
        }
        return patientRepository.findById(patientId).get().getHealthProviderList();
    }

    @Override
    public List<Doctor> getDoctorList(int patientId) throws WrongCredentials {
        if(patientRepository.findById(patientId).equals(Optional.empty())) {
            throw new WrongCredentials("Patient with id: " + patientId + " does not exist");
        }
        return patientRepository.findById(patientId).get().getDoctorList();
    }

    @Override
    public List<HealthRecord> getHealthRecordList(int patientId) throws WrongCredentials {
        if(patientRepository.findById(patientId).equals(Optional.empty())) {
            throw new WrongCredentials("Patient with id: " + patientId + " does not exist");
        }
        return patientRepository.findById(patientId).get().getHealthRecordList();
    }
}
