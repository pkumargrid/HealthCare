package com.healthcare.system.services.implementation;

import com.healthcare.system.entities.Reason;
import com.healthcare.system.repositories.ReasonRepository;
import com.healthcare.system.services.ReasonService;

public class ReasonServiceImpl implements ReasonService {

    private final ReasonRepository reasonRepository;

    public ReasonServiceImpl(ReasonRepository reasonRepository) {
        this.reasonRepository = reasonRepository;
    }

    @Override
    public void save(Reason reason) {
        reasonRepository.save(reason);
    }

    @Override
    public Reason findById(int id) {
        return reasonRepository.findById(id);
    }

    @Override
    public void deleteById(int id) {
        reasonRepository.deleteById(id);
    }

    @Override
    public void update(Reason reason) {
        reasonRepository.update(reason);
    }
}
