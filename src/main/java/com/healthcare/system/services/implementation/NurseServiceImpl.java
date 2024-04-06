package com.healthcare.system.services.implementation;

import com.healthcare.system.entities.Nurse;
import com.healthcare.system.exceptions.AlreadyLoggedInException;
import com.healthcare.system.exceptions.AlreadyLoggedOutException;
import com.healthcare.system.exceptions.ValidationException;
import com.healthcare.system.repositories.NurseRepository;
import com.healthcare.system.services.NurseService;
import com.healthcare.system.session.SessionManager;

import java.util.List;
import java.util.stream.Stream;

import static com.healthcare.system.util.Verification.*;

public class NurseServiceImpl implements NurseService {

    private final NurseRepository nurseRepository;

    public NurseServiceImpl(NurseRepository nurseRepository) {
        this.nurseRepository = nurseRepository;
    }

    @Override
    public Nurse findById(int id) {
        return nurseRepository.findById(id);
    }

    @Override
    public List<Nurse> findAll() {
        return nurseRepository.findAll();
    }

    @Override
    public void saveNurse(Nurse nurse) {
        nurseRepository.saveNurse(nurse);
    }

    @Override
    public void updateNurse(Nurse nurse) {
        nurseRepository.updateNurse(nurse);
    }

    @Override
    public void deleteNurseById(int id) {
        nurseRepository.deleteNurseById(id);
    }

    @Override
    public void login(Nurse nurse) throws ValidationException, AlreadyLoggedInException {
        if (SessionManager.isAuthenticated(nurse.getSessionId())) {
            throw new AlreadyLoggedInException("Nurse: " + nurse.getEmail() + " is already logged in");
        }
        List<Nurse> nurses = nurseRepository.findAll();
        verifyEmailWhileLogin(nurses, nurse.getEmail());
        Nurse nurse1 = nurses.stream().filter(n -> n.getEmail().equals(nurse.getEmail())).findFirst().get();
        verifyPasswordWhileLogin(nurse1.getPassword(), nurse.getPassword());
        nurse1.setSessionId(SessionManager.generateSessionId(nurse.getEmail()));
    }

    @Override
    public void logout(String sessionId) throws AlreadyLoggedOutException {
        if(!SessionManager.isAuthenticated(sessionId)) {
            throw new AlreadyLoggedOutException("You are already logged out");
        }
        SessionManager.removeSessionId(sessionId);
    }

    @Override
    public void register(Nurse nurse) throws ValidationException {
        verifyPasswordWhileRegister(nurse.getPassword());
        List<Nurse> nurses = nurseRepository.findAll();
        List<String> usedEmails = nurses.stream().flatMap(n -> Stream.of(n.getEmail())).toList();
        verifyEmailWhileRegister(usedEmails, nurse.getEmail());
        verifyUserName(nurse.getEmail());
        nurseRepository.saveNurse(nurse);
    }
}
