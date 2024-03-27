package com.healthcare.system.services;

import com.healthcare.system.entities.Nurse;

import java.util.List;

public interface NurseService {
    Nurse findById(int id);
    List<Nurse> findAll();
    void saveNurse(Nurse nurse);
    void updateNurse(Nurse nurse);
    void deleteNurseById(int id);
}
