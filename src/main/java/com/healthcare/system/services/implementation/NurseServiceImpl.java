package com.healthcare.system.services.implementation;

import com.healthcare.system.dto.NurseDTO;
import com.healthcare.system.entities.*;

import com.healthcare.system.exceptions.AlreadyLoggedInException;
import com.healthcare.system.exceptions.AlreadyLoggedOutException;
import com.healthcare.system.exceptions.ValidationException;
import com.healthcare.system.exceptions.WrongCredentials;
import com.healthcare.system.mappers.NurseMapper;
import com.healthcare.system.repositories.HealthProviderRepository;
import com.healthcare.system.repositories.HealthRecordRepository;
import com.healthcare.system.repositories.NurseRepository;
import com.healthcare.system.repositories.ReportRepository;
import com.healthcare.system.services.NurseService;
import com.healthcare.system.session.SessionManager;

import java.rmi.ServerException;
import java.util.List;
import java.util.stream.Stream;

import static com.healthcare.system.util.Verification.*;

public class NurseServiceImpl implements NurseService {

    private final NurseRepository nurseRepository;
    public final HealthRecordRepository healthRecordRepository;
    public final HealthProviderRepository healthProviderRepository;
    public final ReportRepository reportRepository;

    public NurseServiceImpl(NurseRepository nurseRepository, HealthRecordRepository healthRecordRepository, HealthProviderRepository healthProviderRepository, ReportRepository reportRepository) {
        this.nurseRepository = nurseRepository;
        this.healthRecordRepository = healthRecordRepository;
        this.healthProviderRepository = healthProviderRepository;
        this.reportRepository = reportRepository;
    }

    @Override
    public Nurse findById(int id) throws WrongCredentials, ServerException {
        if(nurseRepository.findById(id) == null) {
            throw new WrongCredentials("Nurse with id: " + id + " does not exist");
        }
        return nurseRepository.findById(id);
    }

    @Override
    public List<Nurse> findAll() throws ServerException, WrongCredentials {
        return nurseRepository.findAll();
    }

    @Override
    public void saveNurse(Nurse nurse) throws ValidationException, WrongCredentials, ServerException {
        verifyCredentials(Nurse.class,nurse);
        nurseRepository.saveNurse(nurse);
    }

    @Override
    public void updateNurse(Nurse nurse) throws ValidationException, WrongCredentials, ServerException {
        verifyCredentials(Nurse.class,nurse);
        nurseRepository.updateNurse(nurse);
    }

    @Override
    public void deleteNurseById(int id) throws WrongCredentials, ServerException {
        if(nurseRepository.findById(id) == null) {
            throw new WrongCredentials("Nurse with id: " + id + " does not exist");
        }
        nurseRepository.deleteNurseById(id);
    }
    @Override
    public void login(NurseDTO nurse) throws ValidationException, AlreadyLoggedInException, ServerException, WrongCredentials {
        if (SessionManager.isAuthenticated(nurse.getSessionId())) {
            throw new AlreadyLoggedInException("Nurse: " + nurse.getEmail() + " is already logged in");
        }
        List<Nurse> nurses = nurseRepository.findAll();
        verifyEmailWhileLogin(nurses, nurse.getEmail());
        Nurse nurse1 = nurses.stream().filter(n -> n.getEmail().equals(nurse.getEmail())).findFirst().get();
        verifyPasswordWhileLogin(nurse1.getPassword(), nurse.getPassword());
        SessionManager.generateSessionId(nurse.getEmail());
    }

    @Override
    public void logout(String sessionId) throws AlreadyLoggedOutException {
        if(!SessionManager.isAuthenticated(sessionId)) {
            throw new AlreadyLoggedOutException("You are already logged out");
        }
        SessionManager.removeSessionId(sessionId);
    }

    @Override
    public void register(NurseDTO nurseDTO) throws ValidationException, WrongCredentials, ServerException {
        Nurse nurse = NurseMapper.mapToDomain(nurseDTO,healthProviderRepository);
        verifyPasswordWhileRegister(nurse.getPassword());
        List<Nurse> nurses = nurseRepository.findAll();
        List<String> usedEmails = nurses.stream().flatMap(d -> Stream.of(d.getEmail())).toList();
        verifyEmailWhileRegister(usedEmails, nurse.getEmail());
        verifyUserName(nurse.getEmail());
        nurse.setId(nurses.stream().flatMap(d -> Stream.of(d.getId())).reduce(0,Integer::max) + 1);
        nurseRepository.saveNurse(nurse);
    }
    @Override
    public void addBiometricData(int healthRecordId, Report report) throws WrongCredentials, ServerException {
        if(healthRecordRepository.getById(healthRecordId) == null) {
            throw new WrongCredentials("HealthRecord with id: " + healthRecordId + " does not exist");
        }
        healthRecordRepository.getById(healthRecordId).setReport(report);
        healthRecordRepository.save(healthRecordRepository.getById(healthRecordId));
        report.setId(reportRepository.findAll().stream().flatMap(r -> Stream.of(r.getId())).reduce(0, Integer::max) + 1);
        reportRepository.save(report);
    }


    public List<HealthRecord> accessPatientRecord(Patient patient) throws ServerException, WrongCredentials {
        return nurseRepository.accessPatientRecord(patient.getId());
    }

    @Override
    public List<Reason> getReasons(int nurseId) throws WrongCredentials, ServerException {
        if(nurseRepository.findById(nurseId) == null) {
            throw new WrongCredentials("Nurse with id: " + nurseId + " does not exist");
        }
        return nurseRepository.findById(nurseId).getReasons();
    }

    @Override
    public List<Complaint> getComplaints(int nurseId) throws WrongCredentials, ServerException {
        if(nurseRepository.findById(nurseId) == null) {
            throw new WrongCredentials("Nurse with id: " + nurseId + " does not exist");
        }
        return nurseRepository.findById(nurseId).getComplaintList();
    }
}
