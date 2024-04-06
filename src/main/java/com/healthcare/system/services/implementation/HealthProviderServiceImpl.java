package com.healthcare.system.services.implementation;

import com.healthcare.system.entities.HealthProvider;
import com.healthcare.system.exceptions.AlreadyLoggedInException;
import com.healthcare.system.exceptions.AlreadyLoggedOutException;
import com.healthcare.system.exceptions.ValidationException;
import com.healthcare.system.repositories.HealthProviderRepository;
import com.healthcare.system.services.HealthProviderService;
import com.healthcare.system.session.SessionManager;

import java.util.List;
import java.util.stream.Stream;

import static com.healthcare.system.util.Verification.*;

public class HealthProviderServiceImpl implements HealthProviderService {

    private final HealthProviderRepository healthProviderRepository;

    public HealthProviderServiceImpl(HealthProviderRepository healthProviderRepository) {
        this.healthProviderRepository = healthProviderRepository;
    }
    @Override
    public void save(HealthProvider healthProvider) {
        healthProviderRepository.save(healthProvider);
    }

    @Override
    public HealthProvider getById(int id) {
        return healthProviderRepository.getById(id);
    }

    @Override
    public HealthProvider deleteById(int id) {
        return healthProviderRepository.deleteById(id);
    }

    @Override
    public void update(HealthProvider healthProvider) {
        healthProviderRepository.update(healthProvider);
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
    public void register(HealthProvider healthProvider) throws ValidationException {
        verifyPasswordWhileRegister(healthProvider.getPassword());
        List<HealthProvider> healthProviders = healthProviderRepository.findAll();
        List<String> usedEmails = healthProviders.stream().flatMap(d -> Stream.of(d.getEmail())).toList();
        verifyEmailWhileRegister(usedEmails, healthProvider.getEmail());
        verifyUserName(healthProvider.getEmail());
        healthProviderRepository.save(healthProvider);
    }

    @Override
    public List<HealthProvider> getByName(String name) {
        return healthProviderRepository.getByName(name);
    }
}
