package com.healthcare.system.repositories;

import com.healthcare.system.entities.Doctor;
import com.healthcare.system.exceptions.WrongCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.rmi.ServerException;
import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor,Integer> {


<<<<<<< HEAD
=======
    void save(Doctor doctor) throws WrongCredentials, ServerException;

    Doctor getById(int id) throws ServerException, WrongCredentials;
    void deleteById(int id) throws WrongCredentials, ServerException;

    List<Doctor> getByName(String name) throws ServerException, WrongCredentials;

    void update(Doctor doctor) throws WrongCredentials, ServerException;

    List<Doctor> findAll() throws ServerException, WrongCredentials;

>>>>>>> 0d0b0e05f28cc077e7d42a1a22f542fe7df0398b
}
