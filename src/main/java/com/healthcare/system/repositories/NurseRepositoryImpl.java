package com.healthcare.system.repositories;

import com.healthcare.system.entities.Nurse;
import com.healthcare.system.services.NurseServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class NurseRepositoryImpl implements NurseRepository {
    List<Nurse> nurseList;
    public NurseRepositoryImpl() {
        this.nurseList = new ArrayList<>();
    }
    @Override
    public Nurse findById(int id) {
        return nurseList.stream().filter(nurse -> nurse.getId()==id).findFirst().get();
    }

    @Override
    public List<Nurse> findAll() {
        return nurseList;
    }

    @Override
    public void saveNurse(Nurse nurse) {
        nurseList.add(nurse);
    }

    @Override
    public void updateNurse(Nurse nurse) {
        return;
    }

    @Override
    public void deleteNurseById(int id) {
        Nurse nurse = nurseList.stream().filter(nurse1 -> nurse1.getId() == id).findFirst().get();
        nurseList.remove(nurse);
    }
}
