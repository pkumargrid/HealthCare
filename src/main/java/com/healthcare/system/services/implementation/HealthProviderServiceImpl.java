package com.healthcare.system.services.implementation;

import com.healthcare.system.entities.HealthProviders;
import com.healthcare.system.repositories.HealthProviderRepository;
import com.healthcare.system.services.HealthProviderService;

import java.util.List;

public class HealthProviderServiceImpl implements HealthProviderService {

    private final HealthProviderRepository healthProviderRepository;

    public HealthProviderServiceImpl(HealthProviderRepository healthProviderRepository) {
        this.healthProviderRepository = healthProviderRepository;
    }
    @Override
    public void save(HealthProviders healthProviders) {
        healthProviderRepository.save(healthProviders);
    }

    @Override
    public HealthProviders getById(int id) {
        return healthProviderRepository.getById(id);
    }

    @Override
    public HealthProviders deleteById(int id) {
        return healthProviderRepository.deleteById(id);
    }

    @Override
    public void updateById(int id, HealthProviders healthProviders) {
        healthProviderRepository.updateById(id, healthProviders);
    }

    @Override
    public List<HealthProviders> findAll() {
        return healthProviderRepository.findAll();
    }

    @Override
    public HealthProviders getByName(String name) {
        return healthProviderRepository.getByName(name);
    }
}
