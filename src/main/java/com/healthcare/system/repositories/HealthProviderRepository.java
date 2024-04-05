package com.healthcare.system.repositories;

import com.healthcare.system.entities.HealthProviders;
import java.util.List;

public interface HealthProviderRepository {

    void save(HealthProviders healthProviders);

    HealthProviders getById(int id);

    HealthProviders deleteById(int id);

    HealthProviders getByName(String name);

    void updateById(int id, HealthProviders healthProviders);

    List<HealthProviders> findAll();

}
