package com.healthcare.system.services;

import com.healthcare.system.entities.HealthProviders;

import java.util.List;

public interface HealthProviderService {
    void save(HealthProviders healthProviders);

    HealthProviders getById(int id);

    HealthProviders deleteById(int id);

    HealthProviders getByName(String name);

    void updateById(int id, HealthProviders healthProviders);

    List<HealthProviders> findAll();
}
