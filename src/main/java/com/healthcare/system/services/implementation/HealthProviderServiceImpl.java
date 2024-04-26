package com.healthcare.system.services.implementation;


import com.healthcare.system.dto.HealthProviderDTO;
import com.healthcare.system.entities.*;
import com.healthcare.system.entities.Complaint;
import com.healthcare.system.entities.HealthProvider;
import com.healthcare.system.entities.Patient;
import com.healthcare.system.exceptions.AlreadyLoggedInException;
import com.healthcare.system.exceptions.AlreadyLoggedOutException;
import com.healthcare.system.exceptions.ValidationException;
import com.healthcare.system.exceptions.WrongCredentials;

import com.healthcare.system.mappers.HealthProviderMapper;
import com.healthcare.system.repositories.HealthProviderRepository;
import com.healthcare.system.repositories.PatientRepository;
import com.healthcare.system.services.HealthProviderService;
import com.healthcare.system.session.SessionManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.healthcare.system.util.Verification.*;

@Service
public class HealthProviderServiceImpl implements HealthProviderService {

    private final HealthProviderRepository healthProviderRepository;
    private final PatientRepository patientRepository;


    public HealthProviderServiceImpl(HealthProviderRepository healthProviderRepository, PatientRepository patientRepository) {
        this.healthProviderRepository = healthProviderRepository;
        this.patientRepository = patientRepository;
    }
    @Override
    public void save(HealthProvider healthProvider) throws ValidationException, WrongCredentials {
        verifyCredentials(HealthProvider.class,healthProvider);
        healthProviderRepository.save(healthProvider);
    }

    @Override
    public HealthProvider getById(int id) throws WrongCredentials {
        if(healthProviderRepository.findById(id).equals(Optional.empty())) {
            throw new WrongCredentials("HealthProvider with id: " + id + " does not exist");
        }
        return healthProviderRepository.findById(id).get();
    }

    @Override
    public void deleteById(int id) throws WrongCredentials {
        if(healthProviderRepository.findById(id).equals(Optional.empty())) {
            throw new WrongCredentials("HealthProvider with id: " + id + " does not exist");
        }
        healthProviderRepository.deleteById(id);
    }

    @Override
    public void update(HealthProvider healthProvider) throws ValidationException, WrongCredentials {
        verifyCredentials(HealthProvider.class,healthProvider);
        healthProviderRepository.save(healthProvider);
    }

    @Override
    public List<HealthProvider> findAll() {
        return healthProviderRepository.findAll();
    }

    @Override
    public void login(HealthProvider healthProvider) throws ValidationException, AlreadyLoggedInException {
        if (SessionManager.isAuthenticated(healthProvider.getSessionId())) {
            throw new AlreadyLoggedInException("HealthProvider: " + healthProvider.getEmail() + " is already logged in");
        }
        List<HealthProvider> healthProviders = healthProviderRepository.findAll();
        verifyEmailWhileLogin(healthProviders, healthProvider.getEmail());
        HealthProvider healthProvider1 = healthProviders.stream().filter(h -> h.getEmail().equals(healthProvider.getEmail())).findFirst().get();
        verifyPasswordWhileLogin(healthProvider1.getPassword(), healthProvider.getPassword());
        healthProvider1.setSessionId(SessionManager.generateSessionId(healthProvider.getEmail()));
    }

    @Override
    public void logout(String sessionId) throws AlreadyLoggedOutException {
        if(!SessionManager.isAuthenticated(sessionId)) {
            throw new AlreadyLoggedOutException("You are already logged out");
        }
        SessionManager.removeSessionId(sessionId);
    }

    @Override
    public void register(HealthProviderDTO healthProviderDTO) throws ValidationException, WrongCredentials {
        HealthProvider healthProvider = HealthProviderMapper.mapToDomain(healthProviderDTO);
        verifyPasswordWhileRegister(healthProvider.getPassword());
        List<HealthProvider> healthProviders = healthProviderRepository.findAll();
        List<String> usedEmails = healthProviders.stream().flatMap(h -> Stream.of(h.getEmail())).toList();
        verifyEmailWhileRegister(usedEmails, healthProvider.getEmail());
        verifyUserName(healthProvider.getEmail());
        healthProvider.setId(healthProviders.stream().flatMap(h -> Stream.of(h.getId())).reduce(0,Integer::max) + 1);
        healthProviderRepository.save(healthProvider);
    }

    @Override
    public void registerPatient(int healthProviderId, int patientId) throws WrongCredentials {
        HealthProvider healthProvider = healthProviderRepository.findById(healthProviderId).orElse(null);
        if (healthProvider == null) {
            throw new WrongCredentials("Provided id for Health provider is wrong");
        }
        Patient patient = patientRepository.findById(patientId).orElse(null);
        if(patient == null) {
            throw new WrongCredentials("Provided id for Patient is wrong");
        }
        healthProvider.getPatientList().add(patient);
        healthProviderRepository.save(healthProvider);
    }

    @Override
    public List<Doctor> getDoctors(int healthProviderId) throws WrongCredentials {
        if(healthProviderRepository.findById(healthProviderId).equals(Optional.empty())) {
            throw new WrongCredentials("HealthProvider with id: " + healthProviderId + " does not exist");
        }
        return healthProviderRepository.findById(healthProviderId).get().getDoctorList();
    }

    @Override
    public List<Nurse> getNurse(int healthProviderId) throws WrongCredentials {
        if(healthProviderRepository.findById(healthProviderId).equals(Optional.empty())) {
            throw new WrongCredentials("HealthProvider with id: " + healthProviderId + " does not exist");
        }
        return healthProviderRepository.findById(healthProviderId).get().getNurseList();
    }

    @Override
    public List<Complaint> getComplaints(int healthProviderId) throws WrongCredentials {
        if(healthProviderRepository.findById(healthProviderId).equals(Optional.empty())) {
            throw new WrongCredentials("HealthProvider with id: " + healthProviderId + " does not exist");
        }
        return healthProviderRepository.findById(healthProviderId).get().getComplaintList();
    }

    @Override
    public List<HealthRecord> getHealthRecords(int healthProviderId) throws WrongCredentials {
        if(healthProviderRepository.findById(healthProviderId).equals(Optional.empty())) {
            throw new WrongCredentials("HealthProvider with id: " + healthProviderId + " does not exist");
        }
        return healthProviderRepository.findById(healthProviderId).get().getHealthRecords();
    }

    @Override
    public List<Appointment> getAppointments(int healthProviderId) throws WrongCredentials {
        if(healthProviderRepository.findById(healthProviderId).equals(Optional.empty())) {
            throw new WrongCredentials("HealthProvider with id: " + healthProviderId + " does not exist");
        }
        return healthProviderRepository.findById(healthProviderId).get().getAppointmentList();
    }

    @Override
    public List<Reason> getReasons(int healthProviderId) throws WrongCredentials {
        if(healthProviderRepository.findById(healthProviderId).equals(Optional.empty())) {
            throw new WrongCredentials("HealthProvider with id: " + healthProviderId + " does not exist");
        }
        return healthProviderRepository.findById(healthProviderId).get().getReasons();
    }

}
