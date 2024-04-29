package com.healthcare.system.repositories.implementation;

import com.healthcare.system.entities.HealthProvider;
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

public class HealthProviderRepositoryImpl implements HealthProviderRepository {

    private final DataSource dataSource;

    private final ReasonRepository reasonRepository;
    private final HealthRecordRepository healthRecordRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    private final AppointmentRepository appointmentRepository;
    private final ComplaintRepository complaintRepository;

    private final NurseRepository nurseRepository;


    public HealthProviderRepositoryImpl(DataSource dataSource, ReasonRepository reasonRepository, HealthRecordRepository healthRecordRepository, PatientRepository patientRepository, DoctorRepository doctorRepository, AppointmentRepository appointmentRepository, ComplaintRepository complaintRepository, NurseRepository nurseRepository) {

        this.dataSource = dataSource;
        this.reasonRepository = reasonRepository;
        this.healthRecordRepository = healthRecordRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentRepository;
        this.complaintRepository = complaintRepository;
        this.nurseRepository = nurseRepository;
    }

    @Override
    public void save(HealthProvider healthProvider) throws WrongCredentials, ServerException {
        try {
            getById(healthProvider.getId());
        } catch (WrongCredentials wrongCredentials ) {
            try(Connection connection = dataSource.getConnection()) {
                String sql = "insert into healh_care_provider (name, email, password, id) values(?, ?, ?, ? ,? ,?)";
                try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    configureHealthProvider(healthProvider, preparedStatement);
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
        update(healthProvider);
    }

    @Override
    public HealthProvider getById(int id) throws ServerException, WrongCredentials {
        try (Connection connection = dataSource.getConnection()) {
            try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM health_care_provider WHERE id = ?")) {
                preparedStatement.setInt(1, id);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return createHealthProvider(resultSet);
                    } else {
                        throw new WrongCredentials("Doctor with ID: " + id + " does not exist");
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
    public void deleteById(int id) throws WrongCredentials, ServerException {
        try (Connection connection = dataSource.getConnection()) {
            try(PreparedStatement preparedStatement = connection.prepareStatement("delete from health_care_provider where id = ?")){
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
    public void update(HealthProvider healthProvider) throws WrongCredentials, ServerException {
        try(Connection connection = dataSource.getConnection()) {
            String sql = "update health_care_provider set name=?, email=?, password=? where id = ?";
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                configureHealthProvider(healthProvider, preparedStatement);
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
    public List<HealthProvider> findAll() throws ServerException, WrongCredentials {
        List<HealthProvider> healthProviders = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM health_care_provider")) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    HealthProvider healthProvider = createHealthProvider(resultSet);
                    healthProviders.add(healthProvider);
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
        return healthProviders;
    }

    @Override
    public List<HealthProvider> getByName(String name) throws ServerException, WrongCredentials {
        List<HealthProvider> healthProviders = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM healh_care_provider where name = ?")) {
            preparedStatement.setString(1, name);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    HealthProvider healthProvider = createHealthProvider(resultSet);
                    healthProviders.add(healthProvider);
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
        return healthProviders;
    }

    private HealthProvider createHealthProvider(ResultSet resultSet) throws SQLException, ServerException, WrongCredentials {
        HealthProvider healthProvider = new HealthProvider();
        healthProvider.setId(resultSet.getInt("id"));
        healthProvider.setEmail(resultSet.getString("email"));
        healthProvider.setName(resultSet.getString("name"));
        healthProvider.setPassword(resultSet.getString("password"));
        healthProvider.setReasons(reasonRepository.findReasonByType(resultSet.getInt("id"), "health_care_provider"));
        healthProvider.setHealthRecords(healthRecordRepository.findByHealthProviderId());
        healthProvider.setPatientList(patientRepository.findByHealthProviderById(resultSet.getInt("id")));
        healthProvider.setDoctorList(doctorRepository.findByHealthProviderById(resultSet.getInt("id")));
        healthProvider.setAppointmentList(appointmentRepository.findAll());
        healthProvider.setComplaintList(complaintRepository.findAll());
        healthProvider.setNurseList(nurseRepository.findByHealthProviderId(resultSet.getInt("id")));
        return healthProvider;
    }

    private void configureHealthProvider(HealthProvider healthProvider, PreparedStatement preparedStatement) throws WrongCredentials, SQLException {
        preparedStatement.setString(1, healthProvider.getName());
        preparedStatement.setString(2, healthProvider.getEmail());
        preparedStatement.setString(3, healthProvider.getPassword());
        preparedStatement.setInt(4, healthProvider.getId());
        try{
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new WrongCredentials(e.getMessage());
        }
    }
}
