package com.healthcare.system.repositories.implementation;

import com.healthcare.system.entities.Doctor;
import com.healthcare.system.entities.HealthRecord;
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

public class HealthRecordRepositoryImpl implements HealthRecordRepository {

    private final DataSource dataSource;
    private final HealthProviderRepository healthProviderRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final ReportRepository reportRepository;


    public HealthRecordRepositoryImpl(DataSource dataSource, HealthProviderRepository healthProviderRepository, DoctorRepository doctorRepository, PatientRepository patientRepository, ReportRepository reportRepository) {
        this.dataSource = dataSource;
        this.healthProviderRepository = healthProviderRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.reportRepository = reportRepository;
    }

    @Override
    public void save(HealthRecord healthRecord) throws WrongCredentials,ServerException {
        try {
            getById(healthRecord.getId());
        } catch (WrongCredentials wrongCredentials ) {
            try(Connection connection = dataSource.getConnection()) {
                String sql = "insert into health_record (health_care_provider_id, doctor_id, patient_id, prescription_id,vital_record_id, id) values(?, ?, ?, ? ,? ,?)";
                try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    configureHealthRecord(healthRecord, preparedStatement);
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
        update(healthRecord);
    }

    @Override
    public HealthRecord getById(int id) throws WrongCredentials,ServerException {
        try (Connection connection = dataSource.getConnection()) {
            try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM health_record WHERE id = ?")) {
                preparedStatement.setInt(1, id);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return createHealthRecord(resultSet);
                    } else {
                        throw new WrongCredentials("Health Record with ID: " + id + " does not exist");
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
    public void deleteById(int id) throws WrongCredentials,ServerException {
        try (Connection connection = dataSource.getConnection()) {
            try(PreparedStatement preparedStatement = connection.prepareStatement("delete from health_record where id = ?")){
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
    public void update(HealthRecord healthRecord) throws WrongCredentials,ServerException {
        try(Connection connection = dataSource.getConnection()) {
            String sql = "update health_record set health_care_provider_id=?, doctor_id=?, patient_id=?,prescription_id=?,vital_record_id=? where id = ?";
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                configureHealthRecord(healthRecord, preparedStatement);
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
    public List<HealthRecord> findAll() throws ServerException,WrongCredentials {
        List<HealthRecord> healthRecords = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM health_record")) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    HealthRecord healthRecord = createHealthRecord(resultSet);
                    healthRecords.add(healthRecord);
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
        return healthRecords;
    }

    private HealthRecord createHealthRecord(ResultSet resultSet) throws SQLException, ServerException, WrongCredentials {
        HealthRecord healthRecord = new HealthRecord();
        healthRecord.setHealthProvider(healthProviderRepository.getById(resultSet.getInt("health_care_provider_id")));
        healthRecord.setDoctor(doctorRepository.getById(resultSet.getInt("doctor_id")));
        healthRecord.setPatient(patientRepository.findById(resultSet.getInt("patient_id")));
        healthRecord.setReport(reportRepository.findById(resultSet.getInt("vital_record_id")));
        healthRecord.setPrescription(resultSet.getString("prescription"));
        return healthRecord;
    }

    private void configureHealthRecord(HealthRecord healthRecord, PreparedStatement preparedStatement) throws WrongCredentials, SQLException {
        preparedStatement.setInt(1, healthRecord.getHealthProvider().getId());
        preparedStatement.setInt(2, healthRecord.getDoctor().getId());
        preparedStatement.setInt(3, healthRecord.getPatient().getId());
        preparedStatement.setInt(4, healthRecord.getReport().getId());
        preparedStatement.setString(5, healthRecord.getPrescription());
        preparedStatement.setInt(6,healthRecord.getId());
        try{
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new WrongCredentials(e.getMessage());
        }
    }
}
