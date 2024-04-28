package com.healthcare.system.repositories;

import com.healthcare.system.entities.Appointment;
import com.healthcare.system.exceptions.WrongCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.rmi.ServerException;
import java.util.List;

<<<<<<< HEAD
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Integer> {
=======
public interface AppointmentRepository {

    Appointment findById(int id) throws WrongCredentials, ServerException;

    void update(Appointment appointment) throws WrongCredentials, ServerException;

    List<Appointment> findAll() throws ServerException, WrongCredentials;

    void deleteById(int id) throws WrongCredentials, ServerException;

    void save(Appointment appointment) throws ServerException, WrongCredentials;
>>>>>>> 0d0b0e05f28cc077e7d42a1a22f542fe7df0398b

    List<Appointment> findByDoctorId(int id) throws ServerException, WrongCredentials;
}
