package com.healthcare.system.services.implementation;

import com.healthcare.system.entities.Reason;
import com.healthcare.system.exceptions.ValidationException;
import com.healthcare.system.exceptions.WrongCredentials;
import com.healthcare.system.repositories.ReasonRepository;
import com.healthcare.system.services.ReasonService;

import java.rmi.ServerException;

import static com.healthcare.system.util.Verification.verifyCredentials;

public class ReasonServiceImpl implements ReasonService {

    private final ReasonRepository reasonRepository;

    public ReasonServiceImpl(ReasonRepository reasonRepository) {
        this.reasonRepository = reasonRepository;
    }

    @Override
    public void save(Reason reason) throws ValidationException, WrongCredentials, ServerException {
        verifyCredentials(Reason.class,reason);
        reasonRepository.save(reason);
    }

    @Override
    public Reason findById(int id) throws WrongCredentials, ServerException {
        if(reasonRepository.findById(id) == null) {
            throw new WrongCredentials("Reason with id: " + id + " does not exist");
        }
        return reasonRepository.findById(id);
    }

    @Override
    public void deleteById(int id) throws WrongCredentials, ServerException {
        if(reasonRepository.findById(id) == null) {
            throw new WrongCredentials("Reason with id: " + id + " does not exist");
        }
        reasonRepository.deleteById(id);
    }

    @Override
    public void update(Reason reason) throws ValidationException, WrongCredentials, ServerException {
        verifyCredentials(Reason.class,reason);
        reasonRepository.update(reason);
    }
}
