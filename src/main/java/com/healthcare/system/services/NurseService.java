package com.healthcare.system.services;

import com.healthcare.system.dto.NurseDTO;
import com.healthcare.system.entities.*;
import com.healthcare.system.exceptions.AlreadyLoggedInException;
import com.healthcare.system.exceptions.AlreadyLoggedOutException;
import com.healthcare.system.exceptions.ValidationException;
import com.healthcare.system.exceptions.WrongCredentials;

import java.rmi.ServerException;
import java.util.List;

public interface NurseService {
    Nurse findById(int id) throws WrongCredentials, ServerException;
    List<Nurse> findAll() throws ServerException, WrongCredentials;
    void saveNurse(Nurse nurse) throws ValidationException, WrongCredentials, ServerException;
    void updateNurse(Nurse nurse) throws ValidationException, WrongCredentials, ServerException;
    void deleteNurseById(int id) throws WrongCredentials, ServerException;

    void login(NurseDTO nurse) throws ValidationException, AlreadyLoggedInException,ServerException,WrongCredentials;
    void logout(String sessionId) throws AlreadyLoggedOutException;
    void register(NurseDTO nurseDTO) throws ValidationException, AlreadyLoggedInException, WrongCredentials, ServerException;
    void addBiometricData(int healthRecordId, Report report) throws WrongCredentials, ServerException;
    List<HealthRecord> accessPatientRecord(Patient patient) throws ServerException, WrongCredentials;

    List<Reason> getReasons(int nurseId) throws WrongCredentials, ServerException;
    List<Complaint> getComplaints(int nurseId) throws WrongCredentials, ServerException;
}
