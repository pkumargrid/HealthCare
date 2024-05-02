package com.healthcare.system.services;

import com.healthcare.system.entities.Doctor;
import com.healthcare.system.entities.HealthProvider;
import com.healthcare.system.entities.Nurse;
import com.healthcare.system.entities.Patient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private String password;
    private String userName;
    private List<? extends GrantedAuthority> authorities;

    public CustomUserDetails(Object object) {
        if(object instanceof Patient patient) {
            password = patient.getPassword();
            userName = patient.getEmail();
            authorities = Arrays.stream(patient.getRole().split(",")).map(SimpleGrantedAuthority::new).toList();
        } else if(object instanceof Doctor doctor) {
            password = doctor.getPassword();
            userName = doctor.getEmail();
            authorities = Arrays.stream(doctor.getRole().split(",")).map(SimpleGrantedAuthority::new).toList();
        } else if(object instanceof Nurse nurse) {
            password = nurse.getPassword();
            userName = nurse.getEmail();
            authorities = Arrays.stream(nurse.getRole().split(",")).map(SimpleGrantedAuthority::new).toList();
        } else if(object instanceof HealthProvider healthProvider) {
            password = healthProvider.getPassword();
            userName = healthProvider.getEmail();
            authorities = Arrays.stream(healthProvider.getRole().split(",")).map(SimpleGrantedAuthority::new).toList();
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
