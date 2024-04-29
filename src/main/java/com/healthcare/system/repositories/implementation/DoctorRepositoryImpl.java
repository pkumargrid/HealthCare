package com.healthcare.system.repositories.implementation;

import com.healthcare.system.entities.Doctor;
import com.healthcare.system.entities.Patient;
import com.healthcare.system.exceptions.WrongCredentials;
import com.healthcare.system.repositories.*;

import javax.print.Doc;
import javax.sql.DataSource;
import java.rmi.ServerException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DoctorRepositoryImpl implements DoctorRepository {

    private final DataSource dataSource;

    private final HealthProviderRepository healthProviderRepository;

    private final ComplaintRepository complaintRepository;

    private final AppointmentRepository appointmentRepository;

    private final PatientRepository patientRepository;

    private final ReasonRepository reasonRepository;

    public DoctorRepositoryImpl(DataSource dataSource, HealthProviderRepository healthProviderRepository, ComplaintRepository complaintRepository, AppointmentRepository appointmentRepository, PatientRepository patientRepository, ReasonRepository reasonRepository) {
        this.dataSource = dataSource;
        this.healthProviderRepository = healthProviderRepository;
        this.complaintRepository = complaintRepository;
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.reasonRepository = reasonRepository;
    }

    @Override
    public void save(Doctor doctor) throws WrongCredentials, ServerException {
        try {
            getById(doctor.getId());
        } catch (WrongCredentials wrongCredentials ) {
            try(Connection connection = dataSource.getConnection()) {
                String sql = "insert into doctor (name, email, password, health_care_provider_id, id) values(?, ?, ?, ? ,? ,?)";
                try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    configureDoctor(doctor, preparedStatement);
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
        update(doctor);
    }

    @Override
    public Doctor getById(int id) throws ServerException, WrongCredentials {
        try (Connection connection = dataSource.getConnection()) {
            try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM doctor WHERE id = ?")) {
                preparedStatement.setInt(1, id);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return createDoctor(resultSet);
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
            try(PreparedStatement preparedStatement = connection.prepareStatement("delete from doctor where id = ?")){
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
    public List<Doctor> getByName(String name) throws ServerException, WrongCredentials {
        List<Doctor> doctors = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM doctor where name = ?")) {
            preparedStatement.setString(1, name);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Doctor doctor = createDoctor(resultSet);
                    doctors.add(doctor);
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
        return doctors;
    }

    @Override
    public void update(Doctor doctor) throws WrongCredentials, ServerException {
        try(Connection connection = dataSource.getConnection()) {
            String sql = "update doctor set name=?, email=?, password=?,health_care_provider_id=? where id = ?";
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                configureDoctor(doctor, preparedStatement);
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
    public List<Doctor> findAll() throws ServerException, WrongCredentials {
        List<Doctor> doctors = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM doctor")) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Doctor doctor = createDoctor(resultSet);
                    doctors.add(doctor);
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
        return doctors;
    }

    @Override
    public List<Doctor> findByNurseId(int id) throws ServerException, WrongCredentials {
        List<Doctor> doctors = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT doctor.* from doctor inner join nurse_doctor on nurse.doctor_id = doctor.id and nurse.nurse_id = ?")) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Doctor doctor = createDoctor(resultSet);
                    doctors.add(doctor);
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
        return doctors;
    }

    @Override
    public List<Doctor> findByHealthProviderById(int id) throws ServerException, WrongCredentials {
        List<Doctor> doctors = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT doctor.* from doctor where doctor.health_care_provider.id = ?")) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Doctor doctor = createDoctor(resultSet);
                    doctors.add(doctor);
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
        return doctors;
    }

    @Override
    public List<Doctor> findByPatientId(int id) throws ServerException, WrongCredentials {
        List<Doctor> doctors = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT doctor.* from doctor inner join patient_doctor on patient_doctor.doctor_id = doctor.id and patient_doctor.patient_id = ?")) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Doctor doctor = createDoctor(resultSet);
                    doctors.add(doctor);
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
        return doctors;
    }


    private Doctor createDoctor(ResultSet resultSet) throws SQLException, ServerException, WrongCredentials {
        Doctor doctor = new Doctor();
        doctor.setId(resultSet.getInt("id"));
        doctor.setName(resultSet.getString("name"));
        doctor.setEmail(resultSet.getString("email"));
        doctor.setPassword(resultSet.getString("password"));
        doctor.setHealthProvider(healthProviderRepository.getById(resultSet.getInt("health_care_provider_id")));
        doctor.setComplaintList(complaintRepository.findComplainant(resultSet.getInt("id"), "doctor"));
        doctor.setAppointmentList(appointmentRepository.findByDoctorId(resultSet.getInt("id")));
        doctor.setPatientList(patientRepository.findPatientByDoctorId(resultSet.getInt("id")));
        doctor.setReasons(reasonRepository.findReasonByType(resultSet.getInt("id"), "doctor"));
        return doctor;
    }

    private void configureDoctor(Doctor doctor, PreparedStatement preparedStatement) throws WrongCredentials, SQLException {
        preparedStatement.setString(1, doctor.getName());
        preparedStatement.setString(2, doctor.getEmail());
        preparedStatement.setString(3, doctor.getPassword());
        preparedStatement.setInt(4, doctor.getHealthProvider().getId());
        preparedStatement.setInt(5, doctor.getId());
        try{
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new WrongCredentials(e.getMessage());
        }
    }
}
