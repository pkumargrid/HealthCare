package com.healthcare.system.repositories;

import com.healthcare.system.entities.Complaint;
import com.healthcare.system.exceptions.WrongCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.rmi.ServerException;
import java.util.List;

<<<<<<< HEAD
@Repository
public interface ComplaintRepository extends JpaRepository<Complaint,Integer> {

=======
public interface ComplaintRepository {
    Complaint findById(int id) throws WrongCredentials, ServerException;

    void deleteById(int id) throws WrongCredentials, ServerException;

    void update(Complaint complaint) throws WrongCredentials, ServerException;

    List<Complaint> findAll() throws ServerException;

    void save(Complaint complaint) throws WrongCredentials, ServerException;

    List<Complaint> findComplainant(int id, String tableName) throws ServerException;
>>>>>>> 0d0b0e05f28cc077e7d42a1a22f542fe7df0398b
}
