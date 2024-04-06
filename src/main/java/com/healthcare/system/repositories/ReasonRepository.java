package com.healthcare.system.repositories;

import com.healthcare.system.entities.Reason;

public interface ReasonRepository {

    void save(Reason reason);

    Reason findById(int id);

    void deleteById(int id);

    void update(Reason reason);
}
