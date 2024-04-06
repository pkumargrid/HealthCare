package com.healthcare.system.services;

import com.healthcare.system.entities.Reason;

public interface ReasonService {

    void save(Reason reason);

    Reason findById(int id);

    void deleteById(int id);

    void update(Reason reason);

}
