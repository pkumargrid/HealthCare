package com.healthcare.system.repositories.implementation;

import com.healthcare.system.entities.HealthRecord;
import com.healthcare.system.entities.Nurse;
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

public class NurseRepositoryImpl implements NurseRepository {
    private final DataSource dataSource;

    private final HealthProviderRepository healthProviderRepository;

    private final ComplaintRepository complaintRepository;


    private final PatientRepository patientRepository;

    private final ReasonRepository reasonRepository;

    private final DoctorRepository doctorRepository;
    private final HealthRecordRepository healthRecordRepository;
    public NurseRepositoryImpl(DataSource dataSource, HealthProviderRepository healthProviderRepository, ComplaintRepository complaintRepository, PatientRepository patientRepository, ReasonRepository reasonRepository, DoctorRepository doctorRepository, HealthRecordRepository healthRecordRepository) {
        this.dataSource = dataSource;
        this.healthProviderRepository = healthProviderRepository;
        this.complaintRepository = complaintRepository;
        this.patientRepository = patientRepository;
        this.reasonRepository = reasonRepository;
        this.doctorRepository = doctorRepository;
        this.healthRecordRepository = healthRecordRepository;
    }
    @Override
    public Nurse findById(int id) throws ServerException, WrongCredentials {
        try (Connection connection = dataSource.getConnection()) {
            try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM nurse WHERE id = ?")) {
                preparedStatement.setInt(1, id);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return createNurse(resultSet);
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
    public List<Nurse> findAll() throws ServerException, WrongCredentials {
        List<Nurse> nurses = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM nurse")) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Nurse nurse = createNurse(resultSet);
                    nurses.add(nurse);
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
        return nurses;
    }

    @Override
    public void saveNurse(Nurse nurse) throws WrongCredentials, ServerException {
        try {
            findById(nurse.getId());
        } catch (WrongCredentials wrongCredentials ) {
            try(Connection connection = dataSource.getConnection()) {
                String sql = "insert into nurse (name, email, password, health_care_provider_id, id) values(?, ?, ?, ? ,? ,?)";
                try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    configureNurse(nurse, preparedStatement);
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
        updateNurse(nurse);
    }

    @Override
    public void updateNurse(Nurse nurse) throws WrongCredentials, ServerException {
        try(Connection connection = dataSource.getConnection()) {
            String sql = "update nurse set name=?, email=?, password=?,health_care_provider_id=? where id = ?";
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                configureNurse(nurse, preparedStatement);
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
    public void deleteNurseById(int id) throws WrongCredentials, ServerException {
        try (Connection connection = dataSource.getConnection()) {
            try(PreparedStatement preparedStatement = connection.prepareStatement("delete from nurse where id = ?")){
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
    public HealthRecord accessPatientRecord(Integer patientId) {
        return healthRecordRepository.findByPatientId(patientId);
    }

    @Override
    public List<Nurse> findByHealthProviderId(int id) throws ServerException, WrongCredentials {
        List<Nurse> nurses = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from nurse where nurse.health_care_provider_id = ?")) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Nurse nurse = createNurse(resultSet);
                    nurses.add(nurse);
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
        return nurses;
    }

    private Nurse createNurse(ResultSet resultSet) throws SQLException, ServerException, WrongCredentials {
        Nurse nurse = new Nurse();
        int id = resultSet.getInt("id");
        nurse.setId(id);
        nurse.setName(resultSet.getString("name"));
        nurse.setEmail(resultSet.getString("email"));
        nurse.setPassword(resultSet.getString("password"));
        nurse.setComplaintList(complaintRepository.findComplainant(id, "nurse"));
        nurse.setDoctorList(doctorRepository.findByNurseId(id));
        nurse.setHealthProvider(healthProviderRepository.getById(resultSet.getInt("health_care_provider_id")));
        nurse.setPatientList(patientRepository.findByNurseId(id));
        nurse.setReasons(reasonRepository.findReasonByType(id, "nurse"));
        return nurse;
    }

    private void configureNurse(Nurse nurse, PreparedStatement preparedStatement) throws WrongCredentials, SQLException {
        preparedStatement.setString(1, nurse.getName());
        preparedStatement.setString(2, nurse.getEmail());
        preparedStatement.setString(3, nurse.getPassword());
        preparedStatement.setInt(4, nurse.getHealthProvider().getId());
        preparedStatement.setInt(5, nurse.getId());
        try{
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new WrongCredentials(e.getMessage());
        }
    }
}
