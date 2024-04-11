package com.healthcare.system.repositories.implementation;

import com.healthcare.system.entities.HealthRecord;
import com.healthcare.system.entities.Nurse;
import com.healthcare.system.entities.Patient;
import com.healthcare.system.exceptions.WrongCredentials;
import com.healthcare.system.repositories.NurseRepository;

import java.util.ArrayList;
import java.util.List;

public class NurseRepositoryImpl implements NurseRepository {
    List<Nurse> nurseList;
    public NurseRepositoryImpl() {
        this.nurseList = new ArrayList<>();
    }
    @Override
    public Nurse findById(int id) {
        return nurseList.stream().filter(nurse -> nurse.getId()==id).findFirst().orElseGet(()-> null);
    }

    @Override
    public List<Nurse> findAll() {
        return nurseList;
    }

    @Override
    public void saveNurse(Nurse nurse) throws WrongCredentials {
        if(nurseList.contains(nurse)) {
            updateNurse(nurse);
            return;
        }
        nurseList.add(nurse);
    }

    @Override
    public void updateNurse(Nurse nurse) throws WrongCredentials {
        Nurse prevNurse = nurseList.stream().filter(n -> n.getId() == nurse.getId()).findFirst().orElseGet(()-> null);
        if(prevNurse == null) {
            throw new WrongCredentials("Nurse with id " + nurse.getId() + " does not exist");
        }
        prevNurse.setName(nurse.getName());
        prevNurse.setId(nurse.getId());
        prevNurse.setEmail(nurse.getEmail());
        prevNurse.setPassword(nurse.getPassword());
        prevNurse.setComplaintList(nurse.getComplaintList());
        prevNurse.setDoctorList(nurse.getDoctorList());
        prevNurse.setReasons(nurse.getReasons());
        prevNurse.setPatientList(nurse.getPatientList());
    }

    @Override
    public void deleteNurseById(int id) throws WrongCredentials {
        Nurse nurse = nurseList.stream().filter(nurse1 -> nurse1.getId() == id).findFirst().orElseGet(()-> null);
        if(nurse == null) {
            throw new WrongCredentials("Nurse with id " + id + " does not exist");
        }
        nurseList.remove(nurse);
    }

    @Override
    public HealthRecord accessPatientRecord(Patient patient) {
        return null;
    }

}
