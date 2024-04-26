package com.healthcare.system.repositories;

import com.healthcare.system.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {


}
