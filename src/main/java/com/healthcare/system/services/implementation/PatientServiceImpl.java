package com.healthcare.system.services.implementation;

import com.healthcare.system.entities.Appointment;
import com.healthcare.system.entities.Complaint;
import com.healthcare.system.entities.Patient;
import com.healthcare.system.exceptions.AppointmentTimeException;
import com.healthcare.system.repositories.AppointmentRepository;
import com.healthcare.system.repositories.DoctorRepository;
import com.healthcare.system.repositories.NurseRepository;
import com.healthcare.system.repositories.PatientRepository;
import com.healthcare.system.services.PatientService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final NurseRepository nurseRepository;

    public PatientServiceImpl(PatientRepository patientRepository, AppointmentRepository appointmentRepository, DoctorRepository doctorRepository, NurseRepository nurseRepository) {
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.nurseRepository = nurseRepository;
    }

    @Override
    public Patient findById(int id) {
        return patientRepository.findById(id);
    }

    @Override
    public List<Patient> findAll() {
        return patientRepository.findAll();
    }

    @Override
    public void savePatient(Patient patient) {
        patientRepository.save(patient);
    }

    @Override
    public void updatePatient(Patient patient) {
        patientRepository.update(patient.getId(),patient);
    }

    @Override
    public void deletePatientById(int id) {
        patientRepository.deleteById(id);
    }

    @Override
    public void bookAppointments(Appointment appointment) throws AppointmentTimeException {

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
            appointment.getDoctor().getAppointmentList().add(appointment);
        }
        else {
            throw new AppointmentTimeException("Time slots not available");
        }



    }

    @Override
    public void createComplaints(Complaint complaint, String type, int id) {
        complaint.getPatient().getComplaintList().add(complaint);
        if(type.equals("Doctor")) {
            doctorRepository.getById(id).getComplaintList().add(complaint);
        }
        else if(type.equals("Nurse")) {
            nurseRepository.findById(id).getComplaintList().add(complaint);
        }
    }
}
