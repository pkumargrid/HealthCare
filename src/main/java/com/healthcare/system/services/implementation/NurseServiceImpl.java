package com.healthcare.system.services.implementation;

import com.healthcare.system.dto.NurseDTO;
import com.healthcare.system.entities.*;


import com.healthcare.system.exceptions.ValidationException;
import com.healthcare.system.exceptions.WrongCredentials;
import com.healthcare.system.mappers.NurseMapper;
import com.healthcare.system.repositories.*;
import com.healthcare.system.services.NurseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.healthcare.system.util.Verification.*;

@Service
public class NurseServiceImpl implements NurseService {

    private final NurseRepository nurseRepository;
    public final HealthRecordRepository healthRecordRepository;
    public final HealthProviderRepository healthProviderRepository;
    public final ReportRepository reportRepository;

    public final PatientRepository patientRepository;

    public NurseServiceImpl(NurseRepository nurseRepository, HealthRecordRepository healthRecordRepository, HealthProviderRepository healthProviderRepository, ReportRepository reportRepository, PatientRepository patientRepository) {
        this.nurseRepository = nurseRepository;
        this.healthRecordRepository = healthRecordRepository;
        this.healthProviderRepository = healthProviderRepository;
        this.reportRepository = reportRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    public Nurse findById(int id) throws WrongCredentials {
        if(nurseRepository.findById(id).equals(Optional.empty())) {
            throw new WrongCredentials("Nurse with id: " + id + " does not exist");
        }
        return nurseRepository.findById(id).get();
    }

    @Override
    public List<Nurse> findAll() {
        return nurseRepository.findAll();
    }

    @Override
    public void saveNurse(Nurse nurse) throws ValidationException, WrongCredentials {
        verifyCredentials(Nurse.class,nurse);
        nurseRepository.save(nurse);
    }

    @Override
    public void updateNurse(Nurse nurse) throws ValidationException, WrongCredentials {
        verifyCredentials(Nurse.class,nurse);
        nurseRepository.save(nurse);
    }

    @Override
    public void deleteNurseById(int id) throws WrongCredentials {
        if(nurseRepository.findById(id).equals(Optional.empty())) {
            throw new WrongCredentials("Nurse with id: " + id + " does not exist");
        }
        nurseRepository.deleteById(id);
    }
    @Override
    public void register(NurseDTO nurseDTO) throws ValidationException, WrongCredentials {
        Nurse nurse = NurseMapper.INSTANCE.dtoToEntity(nurseDTO);
        verifyPasswordWhileRegister(nurse.getPassword());
        List<Nurse> nurses = nurseRepository.findAll();
        List<String> usedEmails = nurses.stream().flatMap(d -> Stream.of(d.getEmail())).toList();
        verifyEmailWhileRegister(usedEmails, nurse.getEmail());
        verifyUserName(nurse.getEmail());
        nurse.setId(nurses.stream().flatMap(d -> Stream.of(d.getId())).reduce(0,Integer::max) + 1);
        nurseRepository.save(nurse);
    }
    @Override
    public void addBiometricData(int healthRecordId, Report report) throws WrongCredentials {
        if(healthRecordRepository.findById(healthRecordId).equals(Optional.empty())) {
            throw new WrongCredentials("HealthRecord with id: " + healthRecordId + " does not exist");
        }
        healthRecordRepository.getById(healthRecordId).setReport(report);
        healthRecordRepository.save(healthRecordRepository.getById(healthRecordId));
        report.setId(reportRepository.findAll().stream().flatMap(r -> Stream.of(r.getId())).reduce(0, Integer::max) + 1);
        reportRepository.save(report);
    }

    public HealthRecord accessPatientRecord(Integer id) {
        return patientRepository.findHealthRecordById(id);
    }

    @Override
    public List<Reason> getReasons(int nurseId) throws WrongCredentials {
        if(nurseRepository.findById(nurseId).equals(Optional.empty())) {
            throw new WrongCredentials("Nurse with id: " + nurseId + " does not exist");
        }
        return nurseRepository.findById(nurseId).get().getReasons();
    }

    @Override
    public List<Complaint> getComplaints(int nurseId) throws WrongCredentials {
        if(nurseRepository.findById(nurseId).equals(Optional.empty())) {
            throw new WrongCredentials("Nurse with id: " + nurseId + " does not exist");
        }
        return nurseRepository.findById(nurseId).get().getComplaintList();
    }
}
