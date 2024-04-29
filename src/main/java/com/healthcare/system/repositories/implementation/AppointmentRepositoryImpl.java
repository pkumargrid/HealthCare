package com.healthcare.system.repositories.implementation;

import com.healthcare.system.entities.Appointment;
import com.healthcare.system.exceptions.WrongCredentials;
import com.healthcare.system.repositories.AppointmentRepository;
import com.healthcare.system.repositories.DoctorRepository;
import com.healthcare.system.repositories.PatientRepository;

import javax.sql.DataSource;
import java.rmi.ServerException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentRepositoryImpl implements AppointmentRepository {

    private final DataSource dataSource;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    public AppointmentRepositoryImpl(DataSource dataSource, DoctorRepository doctorRepository, PatientRepository patientRepository) {
        this.dataSource = dataSource;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
    }
    @Override
    public Appointment findById(int id) throws WrongCredentials, ServerException {
        try (Connection connection = dataSource.getConnection()) {
            try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM appointment WHERE id = ?")) {
                preparedStatement.setInt(1, id);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return createAppointment(resultSet);
                    } else {
                        throw new WrongCredentials("Appointment with ID: " + id + " does not exist");
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
    public void update(Appointment appointment) throws ServerException, WrongCredentials {
        try(Connection connection = dataSource.getConnection()) {
            String sql = "update appointment set status=?, startTime=?, endTime=?, doctor_id=?, patient_id=? where id = ?";
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                configureAppointment(appointment, preparedStatement);
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
    public List<Appointment> findAll() throws ServerException, WrongCredentials {
        List<Appointment> appointments = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM appointment")) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Appointment appointment = createAppointment(resultSet);
                    appointments.add(appointment);
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
        return appointments;
    }

    private Appointment createAppointment(ResultSet resultSet) throws SQLException, ServerException, WrongCredentials {
        Appointment appointment = new Appointment();
        appointment.setStatus(resultSet.getBoolean("status"));
        appointment.setDoctor(doctorRepository.getById(resultSet.getInt("doctor_id")));
        appointment.setPatient(patientRepository.findById(resultSet.getInt("patient_id")));
        appointment.setStartTime(resultSet.getObject("startTime", LocalDateTime.class));
        appointment.setEndTime(resultSet.getObject("endTime", LocalDateTime.class));
        return appointment;
    }

    @Override
    public void deleteById(int id) throws ServerException, WrongCredentials {
        try (Connection connection = dataSource.getConnection()) {
             try(PreparedStatement preparedStatement = connection.prepareStatement("delete from appointment where id = ?")){
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
    public void save(Appointment appointment) throws ServerException, WrongCredentials {
        try {
            findById(appointment.getId());
        } catch (WrongCredentials wrongCredentials ) {
            try(Connection connection = dataSource.getConnection()) {
                String sql = "insert into appointment (status, startTime, endTime, doctor_id, patient_id, id) values(?, ?, ?, ? ,? ,?)";
                try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    configureAppointment(appointment, preparedStatement);
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
        update(appointment);
    }

    @Override
    public List<Appointment> findByDoctorId(int id) throws ServerException, WrongCredentials {
        List<Appointment> appointments = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM appointment where doctor_id = ?")) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Appointment appointment = createAppointment(resultSet);
                    appointments.add(appointment);
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
        return appointments;
    }

    private void configureAppointment(Appointment appointment, PreparedStatement preparedStatement) throws SQLException, WrongCredentials {
        preparedStatement.setBoolean(1, appointment.isStatus());
        preparedStatement.setObject(2, appointment.getStartTime());
        preparedStatement.setObject(3, appointment.getEndTime());
        preparedStatement.setInt(4, appointment.getDoctor().getId());
        preparedStatement.setInt(5, appointment.getPatient().getId());
        preparedStatement.setInt(6, appointment.getId());
        try{
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new WrongCredentials(e.getMessage());
        }
    }
}
