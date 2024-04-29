package com.healthcare.system.repositories.implementation;

import com.healthcare.system.entities.Doctor;
import com.healthcare.system.entities.Patient;
import com.healthcare.system.exceptions.WrongCredentials;
import com.healthcare.system.repositories.*;


import javax.sql.DataSource;
import java.rmi.ServerException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatientRepositoryImpl implements PatientRepository {

    private final DataSource dataSource;

    private final HealthProviderRepository healthProviderRepository;

    private final ComplaintRepository complaintRepository;

    private final AppointmentRepository appointmentRepository;

    private final NurseRepository nurseRepository;

    private final DoctorRepository doctorRepository;

    private final HealthRecordRepository healthRecordRepository;
    public PatientRepositoryImpl(DataSource dataSource, HealthProviderRepository healthProviderRepository, ComplaintRepository complaintRepository, AppointmentRepository appointmentRepository, NurseRepository nurseRepository, DoctorRepository doctorRepository, HealthRecordRepository healthRecordRepository){
        this.dataSource = dataSource;
        this.healthProviderRepository = healthProviderRepository;
        this.complaintRepository = complaintRepository;
        this.appointmentRepository = appointmentRepository;
        this.nurseRepository = nurseRepository;
        this.doctorRepository = doctorRepository;
        this.healthRecordRepository = healthRecordRepository;
    }
    @Override
    public Patient findById(int id) throws WrongCredentials,ServerException {
        try (Connection connection = dataSource.getConnection()) {
            try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM patient WHERE id = ?")) {
                preparedStatement.setInt(1, id);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return createPatient(resultSet);
                    } else {
                        throw new WrongCredentials("Patient with ID: " + id + " does not exist");
                    }
                }
            } catch (SQLException e) {
                throw new ServerException("Error accessing data: " + e.getMessage());
            }
        } catch (SQLException sqlException) {
            if ("08001".equals(sqlException.getSQLState())) {
                throw new ServerException("Could not connect to the postgres server.");
            } else {
                throw new ServerException("Error executing SQL query: " + sqlException.getMessage());
            }
        }
    }

    @Override
    public List<Patient> findAll() throws WrongCredentials,ServerException {
        List<Patient> patients  = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM patient")) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Patient patient = createPatient(resultSet);
                    patients.add(patient);
                }
            } catch (SQLException e) {
                throw new ServerException("Error accessing data: " + e.getMessage());
            }
        } catch (SQLException sqlException) {
            if ("08001".equals(sqlException.getSQLState())) {
                throw new ServerException("Could not connect to the postgres server.");
            } else {
                throw new ServerException("Error executing SQL query: " + sqlException.getMessage());
            }
        }
        return patients;
    }

    @Override
    public void save(Patient patient) throws WrongCredentials, ServerException {
        try {
            findById(patient.getId());
        } catch (WrongCredentials wrongCredentials ) {
            try(Connection connection = dataSource.getConnection()) {
                String sql = "insert into patient (name, email, password, nurse_id, id) values(?, ?, ?, ? ,? ,?)";
                try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    configurePatient(patient, preparedStatement);
                } catch (SQLException e) {
                    throw new ServerException("Failed to execute the query: " + e.getMessage());
                }
            } catch (SQLException sqlException) {
                if ("08001".equals(sqlException.getSQLState())) {
                    throw new ServerException("Could not connect to the postgres server.");
                } else {
                    throw new ServerException("Error executing SQL query: " + sqlException.getMessage());
                }
            }
            return;
        }
        update(patient);
    }

    @Override
    public void update(Patient patient) throws WrongCredentials,ServerException {
        try(Connection connection = dataSource.getConnection()) {
            String sql = "update patient set name=?, email=?, password=?,nurse_id=? where id = ?";
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                configurePatient(patient, preparedStatement);
            } catch (SQLException e) {
                throw new ServerException("Error accessing data: " + e.getMessage());
            }
        } catch (SQLException sqlException) {
            if ("08001".equals(sqlException.getSQLState())) {
                throw new ServerException("Could not connect to the postgres server.");
            } else {
                throw new ServerException("Error executing SQL query: " + sqlException.getMessage());
            }
        }
    }

    @Override
    public void deleteById(int id) throws WrongCredentials,ServerException {
        try (Connection connection = dataSource.getConnection()) {
            try(PreparedStatement preparedStatement = connection.prepareStatement("delete from patient where id = ?")){
                preparedStatement.setInt(1,id);
                try{
                    preparedStatement.executeUpdate();
                } catch (SQLException sqlException) {
                    throw new WrongCredentials(sqlException.getMessage());
                }
            } catch (SQLException e) {
                throw new ServerException("Error accessing data: " + e.getMessage());
            }
        } catch (SQLException sqlException) {
            if ("08001".equals(sqlException.getSQLState())) {
                throw new ServerException("Could not connect to the postgres server.");
            } else {
                throw new ServerException("Error executing SQL query: " + sqlException.getMessage());
            }
        }
    }

    @Override
    public List<Patient> findPatientByDoctorId(int id) throws WrongCredentials,ServerException {
        List<Patient> patients = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT patient.* from patient inner join patient_doctor on patient_doctor.patient_id = patient.id and patient_doctor.doctor_id = ?")) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Patient patient = createPatient(resultSet);
                    patients.add(patient);
                }
            } catch (SQLException e) {
                throw new ServerException("Error accessing data: " + e.getMessage());
            }
        } catch (SQLException sqlException) {
            if ("08001".equals(sqlException.getSQLState())) {
                throw new ServerException("Could not connect to the postgres server.");
            } else {
                throw new ServerException("Error executing SQL query: " + sqlException.getMessage());
            }
        }
        return patients;
    }

    @Override
    public List<Patient> findByNurseId(int id) throws WrongCredentials,ServerException {
        List<Patient> patients = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * from patient where nurse_id = ?")) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Patient patient = createPatient(resultSet);
                    patients.add(patient);
                }
            } catch (SQLException e) {
                throw new ServerException("Error accessing data: " + e.getMessage());
            }
        } catch (SQLException sqlException) {
            if ("08001".equals(sqlException.getSQLState())) {
                throw new ServerException("Could not connect to the postgres server.");
            } else {
                throw new ServerException("Error executing SQL query: " + sqlException.getMessage());
            }
        }
        return patients;
    }

    @Override
    public List<Patient> findByHealthProviderById(int id) throws WrongCredentials,ServerException{
        List<Patient> patients = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT patient.* from patient inner join patient_health_care_provider on patient_health_care_provider.patient_id = patient.id and patient_health_care_provider.health_care_provider_id = ?")) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Patient patient = createPatient(resultSet);
                    patients.add(patient);
                }
            } catch (SQLException e) {
                throw new ServerException("Error accessing data: " + e.getMessage());
            }
        } catch (SQLException sqlException) {
            if ("08001".equals(sqlException.getSQLState())) {
                throw new ServerException("Could not connect to the postgres server.");
            } else {
                throw new ServerException("Error executing SQL query: " + sqlException.getMessage());
            }
        }
        return patients;
    }


    private Patient createPatient(ResultSet resultSet) throws SQLException, ServerException, WrongCredentials {
        Patient patient = new Patient();
        patient.setId(resultSet.getInt("id"));
        patient.setName(resultSet.getString("name"));
        patient.setEmail(resultSet.getString("email"));
        patient.setPassword(resultSet.getString("password"));
        patient.setNurse(nurseRepository.findById(resultSet.getInt("nurse_id")));
        patient.setComplaintList(complaintRepository.findComplainant(resultSet.getInt("id"), "patient"));
        patient.setAppointmentList(appointmentRepository.findByPatientId(resultSet.getInt("id")));
        patient.setDoctorList(doctorRepository.findByPatientId(resultSet.getInt("id")));
        patient.setHealthProviderList(healthProviderRepository.findByPatientId(resultSet.getInt("id")));
        patient.setHealthRecordList(healthRecordRepository.findByPatientId(resultSet.getInt("id")));
        return patient;
    }

    private void configurePatient(Patient patient, PreparedStatement preparedStatement) throws WrongCredentials, SQLException {
        preparedStatement.setString(1, patient.getName());
        preparedStatement.setString(2, patient.getEmail());
        preparedStatement.setString(3, patient.getPassword());
        preparedStatement.setInt(4, patient.getNurse().getId());
        preparedStatement.setInt(5, patient.getId());
        try{
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new WrongCredentials(e.getMessage());
        }
    }
}
