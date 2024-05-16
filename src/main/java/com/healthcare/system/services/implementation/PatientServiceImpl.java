package com.healthcare.system.services.implementation;

import com.healthcare.system.dto.AppointmentDTO;
import com.healthcare.system.dto.PatientDTO;
import com.healthcare.system.entities.*;
import com.healthcare.system.exceptions.*;
import com.healthcare.system.mappers.AppointmentMapper;
import com.healthcare.system.mappers.PatientMapper;
import com.healthcare.system.repositories.*;
import com.healthcare.system.services.PatientService;
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

    public PatientServiceImpl(PatientRepository patientRepository,
                              AppointmentRepository appointmentRepository,
                              DoctorRepository doctorRepository, NurseRepository nurseRepository,
                              ComplaintRepository complaintRepository,
                              HealthProviderRepository healthProviderRepository) {
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.nurseRepository = nurseRepository;
        this.complaintRepository = complaintRepository;
        this.healthProviderRepository = healthProviderRepository;
    }

    @Override
    public Patient findById(int id) throws WrongCredentials {
        if (patientRepository.findById(id).equals(Optional.empty())) {
            throw new WrongCredentials("Patient with id: " + id + " does not exist");
        }
        return patientRepository.findById(id).get();
    }

    @Override
    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    @Override
    public void savePatient(Patient patient) throws ValidationException {
        verifyCredentials(Patient.class, patient);
        patientRepository.save(patient);
    }

    @Override
    public void updatePatient(Patient patient) throws ValidationException {
        verifyCredentials(Patient.class, patient);
        patientRepository.save(patient);
    }

    @Override
    public void deletePatientById(int id) throws WrongCredentials {
        if (patientRepository.findById(id).equals(Optional.empty())) {
            throw new WrongCredentials("Patient with id: " + id + " does not exist");
        }
        patientRepository.deleteById(id);
    }

    public void bookAppointments(AppointmentDTO appointmentDTO) throws AppointmentTimeException, WrongCredentials {
        Appointment appointment = AppointmentMapper.INSTANCE.dtoToEntity(appointmentDTO);
        Patient patient = patientRepository.findById(appointmentDTO.getId()).orElse(null);
        Doctor doctor = doctorRepository.findById(appointmentDTO.getId()).orElse(null);
        if (patient == null) {
            throw new WrongCredentials("Patient with " + appointmentDTO.getPatientId() + " does not exist");
        }
        if (doctor == null) {
            throw new WrongCredentials("Doctor with " + appointmentDTO.getDoctorId() + " does not exist");
        }
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        List<Appointment> doctorAppointmentList = appointment.getPatient().getAppointmentList();
        int[] range = new int[601];
        for (var doctorAppointment : doctorAppointmentList) {
            LocalDateTime start = doctorAppointment.getStartTime();
            int startHour = start.getHour();
            int startMinute = start.getMinute();
            int difference = (startHour - 10) * 60 + startMinute;
            range[difference] += 1;
            if (difference + 15 <= 600)
                range[difference + 15] -= 1;
            else
                range[600] -= 1;
        }

        for (int i = 1; i <= 600; i++) {
            range[i] = range[i - 1] + range[i];
        }
        int startTime = -1;
        for (int i = 0; i <= 585; i++) {
            if(range[i] == 0 && range[i + 15] == 0) {
                startTime = i;
            }
        }
        if (startTime != -1) {
            LocalDate currentDate = LocalDate.now();
            LocalTime start = LocalTime.of(startTime / 60 + 10,startTime % 60);
            int endTime = startTime + 15;
            LocalTime end = LocalTime.of(endTime / 60 + 10,endTime % 60);
            appointment.setStartTime(LocalDateTime.of(currentDate, start));
            appointment.setEndTime(LocalDateTime.of(currentDate, end));
            doctor.getAppointmentList().add(appointment);
            doctor.getPatientList().add(patient);
            patient.getDoctorList().add(doctor);
            patient.getAppointmentList().add(appointment);
            patientRepository.save(patient);
            doctorRepository.save(doctor);
            doctor.getHealthProvider().getAppointmentList().add(appointment);
            doctor.getHealthProvider().getPatientList().add(patient);
            appointment.setId(appointmentRepository.findAll().stream().flatMap(a -> Stream.of(a.getId())).reduce(0, Integer::max) + 1);
            healthProviderRepository.save(appointment.getDoctor().getHealthProvider());
            appointmentRepository.save(appointment);
        }
        else {
            throw new AppointmentTimeException("Time slots not available");
        }

    }

    @Override
    public void createComplaint(Complaint complaint, String type, int id) throws WrongCredentials {
        complaint.getPatient().getComplaintList().add(complaint);
        if (type.equals("Doctor")) {
            Doctor doctor = doctorRepository.getById(id);
            doctor.getComplaintList().add(complaint);
            HealthProvider healthProvider = doctor.getHealthProvider();
            healthProvider.getComplaintList().add(complaint);
            doctorRepository.save(doctor);
            healthProviderRepository.save(healthProvider);
        }
        else if (type.equals("Nurse")) {
            Nurse nurse = nurseRepository.findById(id).get();
            nurse.getComplaintList().add(complaint);
            nurse.getHealthProvider().getComplaintList().add(complaint);
            HealthProvider healthProvider = nurse.getHealthProvider();
            healthProvider.getComplaintList().add(complaint);
            nurseRepository.save(nurse);
            healthProviderRepository.save(healthProvider);
        }
        List<Complaint> complaints = complaintRepository.findAll();
        complaint.setId(complaints.stream().flatMap(c -> Stream.of(c.getId())).reduce(0, Integer::max) + 1);
        complaintRepository.save(complaint);
    }

    @Override
    public HealthRecord accessPatientRecord(Patient patient) {
        return null;
    }


    @Override
    public void register(PatientDTO patientDTO) throws ValidationException, WrongCredentials {
        Patient patient = PatientMapper.INSTANCE.dtoToEntity(patientDTO);
        verifyPasswordWhileRegister(patient.getPassword());
        List<Patient> patients = patientRepository.findAll();
        List<String> usedEmails = patients.stream().flatMap(d -> Stream.of(d.getEmail())).toList();
        verifyEmailWhileRegister(usedEmails, patient.getEmail());
        verifyUserName(patient.getEmail());
        patient.setId(patients.stream().flatMap(d -> Stream.of(d.getId())).reduce(0, Integer::max) + 1);
        patientRepository.save(patient);
    }

    @Override
    public List<HealthProvider> getHealthProviders(int patientId) throws WrongCredentials {
        if (patientRepository.findById(patientId).equals(Optional.empty())) {
            throw new WrongCredentials("Patient with id: " + patientId + " does not exist");
        }
        return patientRepository.findById(patientId).get().getHealthProviderList();
    }

    @Override
    public List<Doctor> getDoctorList(int patientId) throws WrongCredentials {
        if (patientRepository.findById(patientId).equals(Optional.empty())) {
            throw new WrongCredentials("Patient with id: " + patientId + " does not exist");
        }
        return patientRepository.findById(patientId).get().getDoctorList();
    }

    @Override
    public List<HealthRecord> getHealthRecordList(int patientId) throws WrongCredentials {
        if (patientRepository.findById(patientId).equals(Optional.empty())) {
            throw new WrongCredentials("Patient with id: " + patientId + " does not exist");
        }
        return patientRepository.findById(patientId).get().getHealthRecordList();
    }
}
