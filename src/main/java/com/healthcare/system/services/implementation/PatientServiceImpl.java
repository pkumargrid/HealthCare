package com.healthcare.system.services.implementation;

import com.healthcare.system.entities.Patient;
import com.healthcare.system.exceptions.AlreadyLoggedInException;
import com.healthcare.system.exceptions.AlreadyLoggedOutException;
import com.healthcare.system.exceptions.ValidationException;
import com.healthcare.system.repositories.PatientRepository;
import com.healthcare.system.services.PatientService;
import com.healthcare.system.session.SessionManager;

import java.util.List;
import java.util.stream.Stream;

import static com.healthcare.system.util.Verification.*;

public class PatientServiceImpl implements PatientService {
    private final PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
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
        patientRepository.update(patient);
    }

    @Override
    public void deletePatientById(int id) {
        patientRepository.deleteById(id);
    }

    @Override
    public void login(Patient patient) throws ValidationException, AlreadyLoggedInException {
        if (SessionManager.isAuthenticated(patient.getSessionId())) {
            throw new AlreadyLoggedInException("Patient: " + patient.getEmail() + " is already logged in");
        }
        List<Patient> patients = patientRepository.findAll();
        verifyEmailWhileLogin(patients, patient.getEmail());
        Patient patient1 = patients.stream().filter(p -> p.getEmail().equals(patient.getEmail())).findFirst().get();
        verifyPasswordWhileLogin(patient1.getPassword(), patient.getPassword());
        patient1.setSessionId(SessionManager.generateSessionId(patient.getEmail()));
    }

    @Override
    public void logout(String sessionId) throws AlreadyLoggedOutException {
        if(!SessionManager.isAuthenticated(sessionId)) {
            throw new AlreadyLoggedOutException("You are already logged out");
        }
        SessionManager.removeSessionId(sessionId);
    }

    @Override
    public void register(Patient patient) throws ValidationException {
        verifyPasswordWhileRegister(patient.getPassword());
        List<Patient> patients = patientRepository.findAll();
        List<String> usedEmails = patients.stream().flatMap(p -> Stream.of(p.getEmail())).toList();
        verifyEmailWhileRegister(usedEmails, patient.getEmail());
        verifyUserName(patient.getEmail());
        patientRepository.save(patient);
    }
}
