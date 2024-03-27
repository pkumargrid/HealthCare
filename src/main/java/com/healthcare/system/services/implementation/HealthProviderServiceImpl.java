package com.healthcare.system.services.implementation;

import com.healthcare.system.entities.HealthProvider;
import com.healthcare.system.repositories.HealthProviderRepository;
import com.healthcare.system.services.HealthProviderService;

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
    public void updateById(int id, HealthProvider healthProvider) {
        healthProviderRepository.updateById(id, healthProvider);
    }

    @Override
    public List<HealthProvider> findAll() {
        return healthProviderRepository.findAll();
    }

    @Override
    public HealthProvider getByName(String name) {
        return healthProviderRepository.getByName(name);
    }
}
