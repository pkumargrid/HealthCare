package com.healthcare.system.repositories;

import com.healthcare.system.entities.Nurse;

import java.util.List;

public interface NurseRepository {
    Nurse findById(int id);
    List<Nurse> findAll();
    void saveNurse(Nurse nurse);
    void updateNurse(Nurse nurse);
    void deleteNurseById(int id);
}
