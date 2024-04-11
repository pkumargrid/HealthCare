package com.healthcare.system.repositories.implementation;

import com.healthcare.system.entities.Reason;
import com.healthcare.system.exceptions.WrongCredentials;
import com.healthcare.system.repositories.ReasonRepository;

import java.util.ArrayList;
import java.util.List;

public class ReasonRepositoryImpl implements ReasonRepository {

    List<Reason> reasons;

    public ReasonRepositoryImpl() {
        reasons = new ArrayList<>();
    }

    @Override
    public void save(Reason reason) throws WrongCredentials {
        if(reasons.contains(reason)) {
            update(reason);
        }
        reasons.add(reason);
    }

    @Override
    public Reason findById(int id) {
        return reasons.stream().filter(r -> r.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void deleteById(int id) throws WrongCredentials {
        Reason reason = reasons.stream().filter(r -> r.getId() == id).findFirst().orElse(null);
        if(reason == null) {
            throw new WrongCredentials("Reason with id " + reason.getId() + " does not exist");
        }
        reasons.remove(reason);
    }

    @Override
    public void update(Reason reason) throws WrongCredentials {
        Reason prevRead = reasons.stream().filter(r -> r.getId() == reason.getId()).findFirst().orElse(null);
        if(prevRead == null) {
            throw new WrongCredentials("Reason with id " + reason.getId() + " does not exist");
        }
        prevRead.setComplaint(reason.getComplaint());
        prevRead.setText(reason.getText());
        prevRead.setType(reason.getType());
    }
}
