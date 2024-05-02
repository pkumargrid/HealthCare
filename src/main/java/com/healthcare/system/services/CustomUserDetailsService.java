package com.healthcare.system.services;

import com.healthcare.system.entities.Doctor;
import com.healthcare.system.entities.HealthProvider;
import com.healthcare.system.entities.Nurse;
import com.healthcare.system.entities.Patient;
import com.healthcare.system.repositories.DoctorRepository;
import com.healthcare.system.repositories.HealthProviderRepository;
import com.healthcare.system.repositories.NurseRepository;
import com.healthcare.system.repositories.PatientRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {

    private final DoctorRepository doctorRepository;

    private final NurseRepository nurseRepository;
    private final HealthProviderRepository healthProviderRepository;
    private final PatientRepository patientRepository;

    public CustomUserDetailsService(DoctorRepository doctorRepository, NurseRepository nurseRepository, HealthProviderRepository healthProviderRepository, PatientRepository patientRepository) {
        this.doctorRepository = doctorRepository;
        this.nurseRepository = nurseRepository;
        this.healthProviderRepository = healthProviderRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Patient patient = patientRepository.findByEmail(email);
        if (patient != null) {
            return new CustomUserDetails(patient);
        }
        Doctor doctor = doctorRepository.findByEmail(email);
        if (doctor != null) {
            return new CustomUserDetails(doctor);
        }
        HealthProvider healthProvider = healthProviderRepository.findByEmail(email);
        if (healthProvider != null) {
            return new CustomUserDetails(healthProvider);
        }
        Nurse nurse = nurseRepository.findByEmail(email);
        if (nurse != null) {
            return new CustomUserDetails(nurse);
        }
        throw new UsernameNotFoundException("Cannot find User with email: " + email);
    }
}
