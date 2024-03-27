package com.healthcare.system.services;

import com.healthcare.system.entities.HealthProvider;

public interface HealthProviderService {
    void save(HealthProvider healthProvider);

    HealthProvider getById(int id);

    HealthProvider deleteById(int id);

    HealthProvider getByName(String name);

    void updateById(int id, HealthProvider healthProvider);

    List<HealthProvider> findAll();
}
