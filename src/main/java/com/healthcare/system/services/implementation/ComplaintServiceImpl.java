package com.healthcare.system.services.implementation;

import com.healthcare.system.entities.Complaint;
import com.healthcare.system.repositories.ComplaintRepository;
import com.healthcare.system.repositories.implementation.ComplaintRepositoryImpl;
import com.healthcare.system.services.ComplaintService;
import java.util.List;

public class ComplaintServiceImpl implements ComplaintService {

    private final ComplaintRepository complaintRepository;

    public ComplaintServiceImpl(ComplaintRepositoryImpl complaintRepository) {
        this.complaintRepository = complaintRepository;
    }
    @Override
    public Complaint findById(int id) {
        return complaintRepository.findById(id);
    }

    @Override
    public void deleteById(int id) {
        complaintRepository.deleteById(id);
    }

    @Override
    public void updateId(int id, Complaint complaint) {
        complaintRepository.updateId(id, complaint);
    }

    @Override
    public List<Complaint> findAll() {
        return complaintRepository.findAll();
    }

    @Override
    public void save(Complaint complaint) {
        complaintRepository.save(complaint);
    }
}
