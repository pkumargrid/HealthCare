package com.healthcare.system.repositories;

import com.healthcare.system.entities.Complaint;
import java.util.List;

public interface ComplaintRepository {
    Complaint findById(int id);

    void deleteById(int id);

    void updateId(int id, Complaint complaint);

    List<Complaint> findAll();

    void save(Complaint complaint);
}
