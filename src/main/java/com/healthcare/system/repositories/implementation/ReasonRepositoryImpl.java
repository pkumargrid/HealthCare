package com.healthcare.system.repositories.implementation;

import com.healthcare.system.entities.Reason;
import com.healthcare.system.repositories.ReasonRepository;

import java.util.ArrayList;
import java.util.List;

public class ReasonRepositoryImpl implements ReasonRepository {

    List<Reason> reasons;

    public ReasonRepositoryImpl() {
        reasons = new ArrayList<>();
    }

    @Override
    public void save(Reason reason) {
        if(reasons.contains(reason)) {
            update(reason);
        }
        reasons.add(reason);
    }

    @Override
    public Reason findById(int id) {
        return reasons.stream().filter(r -> r.getId() == id).findFirst().get();
    }

    @Override
    public void deleteById(int id) {
        Reason reason = reasons.stream().filter(r -> r.getId() == id).findFirst().get();
        reasons.remove(reason);
    }

    @Override
    public void update(Reason reason) {
        Reason prevRead = reasons.stream().filter(r -> r.getId() == reason.getId()).findFirst().get();
        prevRead.setComplaint(reason.getComplaint());
        prevRead.setText(reason.getText());
        prevRead.setType(reason.getType());
    }
}
