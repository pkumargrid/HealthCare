package com.healthcare.system.repositories;

import com.healthcare.system.entities.HealthProvider;
import java.util.List;

public interface HealthProviderRepository {

    void save(HealthProvider healthProvider);

    HealthProvider getById(int id);

    HealthProvider deleteById(int id);

    HealthProvider getByName(String name);

    void updateById(HealthProvider healthProvider);

    List<HealthProvider> findAll();

}
