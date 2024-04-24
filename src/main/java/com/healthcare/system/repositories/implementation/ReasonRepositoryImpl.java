package com.healthcare.system.repositories.implementation;

import com.healthcare.system.entities.Complaint;
import com.healthcare.system.entities.Reason;
import com.healthcare.system.entities.Report;
import com.healthcare.system.exceptions.WrongCredentials;
import com.healthcare.system.repositories.ComplaintRepository;
import com.healthcare.system.repositories.ReasonRepository;

import javax.sql.DataSource;
import java.rmi.ServerException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReasonRepositoryImpl implements ReasonRepository {

    private final DataSource dataSource;

    private final ComplaintRepository complaintRepository;

    public ReasonRepositoryImpl(DataSource dataSource, ComplaintRepository complaintRepository) {
        this.dataSource = dataSource;
        this.complaintRepository = complaintRepository;
    }

    @Override
    public void save(Reason reason) throws WrongCredentials,ServerException {
        try {
            findById(reason.getId());
        } catch (WrongCredentials wrongCredentials ) {
            try(Connection connection = dataSource.getConnection()) {
                String sql = "insert into reason (text,table_name,type,complaint_id, id,) values(?, ?, ?,?,?)";
                try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    configureReason(reason, preparedStatement);
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
        update(reason);
    }

    @Override
    public Reason findById(int id) throws WrongCredentials,ServerException {
        try (Connection connection = dataSource.getConnection()) {
            try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM reason WHERE id = ?")) {
                preparedStatement.setInt(1, id);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return createReason(resultSet);
                    } else {
                        throw new WrongCredentials("Reason with ID: " + id + " does not exist");
                    }
                }
            } catch (SQLException e) {
                throw new ServerException("Error accessing data: " + e.getMessage());
            }
        } catch (SQLException sqlException) {
            if ("08001".equals(sqlException.getSQLState())) {
                throw new ServerException("Could not connect to the server.");
            } else {
                throw new ServerException("Error executing SQL query: " + sqlException.getMessage());
            }
        }
    }

    @Override
    public void deleteById(int id) throws WrongCredentials,ServerException {
        try (Connection connection = dataSource.getConnection()) {
            try(PreparedStatement preparedStatement = connection.prepareStatement("delete from reason where id = ?")){
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
    public void update(Reason reason) throws WrongCredentials,ServerException {
        try(Connection connection = dataSource.getConnection()) {
            String sql = "update reason set text=?, table_name=?, type=?, complaint_id=?,where id = ?";
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                configureReason(reason, preparedStatement);
            } catch (SQLException e) {
                throw new ServerException("Error accessing data: " + e.getMessage());
            }
        } catch (SQLException sqlException) {
            if ("08001".equals(sqlException.getSQLState())) {
                throw new ServerException("Could not connect to the server.");
            } else {
                throw new ServerException("Error executing SQL query: " + sqlException.getMessage());
            }
        }
    }

    @Override
    public List<Reason> findAll() throws WrongCredentials,ServerException {
        List<Reason> reasons = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM reason")) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Reason reason = createReason(resultSet);
                    reasons.add(reason);
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
        return reasons;
    }

    private Reason createReason(ResultSet resultSet) throws SQLException, WrongCredentials, ServerException {
        Reason reason = new Reason();
        reason.setText(resultSet.getString("text"));
        reason.setTableName(resultSet.getString("table_name"));
        reason.setType(resultSet.getInt("type"));
        reason.setComplaint(complaintRepository.findById(resultSet.getInt("complaint_id")));
        reason.setId(resultSet.getInt("id"));
        return reason;
    }

    private void configureReason(Reason reason, PreparedStatement preparedStatement) throws WrongCredentials, SQLException {
        preparedStatement.setString(1, reason.getText());
        preparedStatement.setString(2,reason.getTableName());
        preparedStatement.setInt(3,reason.getType());
        preparedStatement.setInt(4, reason.getComplaint().getId());
        preparedStatement.setInt(5, reason.getId());
        try{
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new WrongCredentials(e.getMessage());
        }
    }
}
