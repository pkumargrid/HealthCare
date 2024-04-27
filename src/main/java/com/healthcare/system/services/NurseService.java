package com.healthcare.system.services;

import com.healthcare.system.dto.NurseDTO;
import com.healthcare.system.entities.Complaint;
import com.healthcare.system.entities.Nurse;
import com.healthcare.system.entities.Reason;
import com.healthcare.system.entities.Report;
import com.healthcare.system.exceptions.AlreadyLoggedInException;
import com.healthcare.system.exceptions.ValidationException;
import com.healthcare.system.exceptions.WrongCredentials;

import java.util.List;

public interface NurseService {
    Nurse findById(int id) throws WrongCredentials;
    List<Nurse> findAll();
    void saveNurse(Nurse nurse) throws ValidationException, WrongCredentials;
    void updateNurse(Nurse nurse) throws ValidationException, WrongCredentials;
    void deleteNurseById(int id) throws WrongCredentials;

    void register(NurseDTO nurseDTO) throws ValidationException, AlreadyLoggedInException, WrongCredentials;
    void addBiometricData(int healthRecordId, Report report) throws WrongCredentials;

    List<Reason> getReasons(int nurseId) throws WrongCredentials;
    List<Complaint> getComplaints(int nurseId) throws WrongCredentials;
}
