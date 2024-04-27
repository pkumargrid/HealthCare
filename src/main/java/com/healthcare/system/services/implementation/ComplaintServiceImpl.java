package com.healthcare.system.services.implementation;

import com.healthcare.system.entities.Complaint;
import com.healthcare.system.exceptions.ValidationException;
import com.healthcare.system.exceptions.WrongCredentials;
import com.healthcare.system.repositories.ComplaintRepository;
import com.healthcare.system.services.ComplaintService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.healthcare.system.util.Verification.verifyCredentials;


@Service
public class ComplaintServiceImpl implements ComplaintService {

    private final ComplaintRepository complaintRepository;

    public ComplaintServiceImpl(ComplaintRepository complaintRepository) {
        this.complaintRepository = complaintRepository;
    }
    @Override
    public Complaint findById(int id) throws WrongCredentials {
        if(complaintRepository.findById(id).equals(Optional.empty())) {
            throw new WrongCredentials("Complaint with id: " + id + " does not exist");
        }
        return complaintRepository.findById(id).get();
    }

    @Override
    public void deleteById(int id) throws WrongCredentials {
        if(complaintRepository.findById(id).equals(Optional.empty())) {
            throw new WrongCredentials("Complaint with id: " + id + " does not exist");
        }
        complaintRepository.deleteById(id);
    }

    @Override
    public void update(Complaint complaint) throws ValidationException, WrongCredentials {
        verifyCredentials(Complaint.class,complaint);
        complaintRepository.save(complaint);
    }

    @Override
    public List<Complaint> findAll() {
        return complaintRepository.findAll();
    }

    @Override
    public void save(Complaint complaint) throws ValidationException, WrongCredentials {
        verifyCredentials(Complaint.class,complaint);
        complaintRepository.save(complaint);
    }
}
