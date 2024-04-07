package com.healthcare.system.services.implementation;

import com.healthcare.system.entities.Appointment;
import com.healthcare.system.entities.Complaint;
import com.healthcare.system.exceptions.ValidationException;
import com.healthcare.system.exceptions.WrongCredentials;
import com.healthcare.system.repositories.ComplaintRepository;
import com.healthcare.system.repositories.implementation.ComplaintRepositoryImpl;
import com.healthcare.system.services.ComplaintService;
import java.util.List;

import static com.healthcare.system.util.Verification.verifyCredentials;

public class ComplaintServiceImpl implements ComplaintService {

    private final ComplaintRepository complaintRepository;

    public ComplaintServiceImpl(ComplaintRepositoryImpl complaintRepository) {
        this.complaintRepository = complaintRepository;
    }
    @Override
    public Complaint findById(int id) throws WrongCredentials {
        if(complaintRepository.findById(id) == null) {
            throw new WrongCredentials("Complaint with id: " + id + " does not exist");
        }
        return complaintRepository.findById(id);
    }

    @Override
    public void deleteById(int id) throws WrongCredentials {
        if(complaintRepository.findById(id) == null) {
            throw new WrongCredentials("Complaint with id: " + id + " does not exist");
        }
        complaintRepository.deleteById(id);
    }

    @Override
    public void update(Complaint complaint) throws ValidationException {
        verifyCredentials(Complaint.class,complaint);
        complaintRepository.update(complaint);
    }

    @Override
    public List<Complaint> findAll() {
        return complaintRepository.findAll();
    }

    @Override
    public void save(Complaint complaint) throws ValidationException {
        verifyCredentials(Complaint.class,complaint);
        complaintRepository.save(complaint);
    }
}
