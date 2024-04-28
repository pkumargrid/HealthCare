package com.healthcare.system.repositories;

import com.healthcare.system.entities.Reason;
import com.healthcare.system.exceptions.WrongCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

<<<<<<< HEAD
@Repository
public interface ReasonRepository extends JpaRepository<Reason,Integer> {

=======
import java.rmi.ServerException;
import java.util.List;

public interface ReasonRepository {

    void save(Reason reason) throws WrongCredentials, ServerException;

    Reason findById(int id) throws WrongCredentials,ServerException;

    void deleteById(int id) throws WrongCredentials,ServerException;

    void update(Reason reason) throws WrongCredentials,ServerException;

    List<Reason> findAll() throws WrongCredentials,ServerException;

    List<Reason> findReasonByType(int id, String tableName) throws ServerException, WrongCredentials;
>>>>>>> 0d0b0e05f28cc077e7d42a1a22f542fe7df0398b
}
