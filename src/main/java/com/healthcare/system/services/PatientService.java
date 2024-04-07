package com.healthcare.system.services;

import com.healthcare.system.entities.Appointment;
import com.healthcare.system.entities.Complaint;
import com.healthcare.system.entities.Patient;
import com.healthcare.system.exceptions.AlreadyLoggedInException;
import com.healthcare.system.exceptions.AlreadyLoggedOutException;
import com.healthcare.system.exceptions.ValidationException;
import com.healthcare.system.exceptions.AppointmentTimeException;

import java.util.List;

public interface PatientService {
    Patient findById(int id);
    List<Patient> findAll();
    void savePatient(Patient patient);
    void updatePatient(Patient patient);
    void deletePatientById(int id);

    void login(Patient patient) throws ValidationException, AlreadyLoggedInException;
    void logout(String sessionId) throws AlreadyLoggedOutException;
    void register(Patient patient) throws ValidationException;
    void bookAppointments(Appointment appointment) throws AppointmentTimeException;
    void createComplaint(Complaint complaint, String type, int id);


}
