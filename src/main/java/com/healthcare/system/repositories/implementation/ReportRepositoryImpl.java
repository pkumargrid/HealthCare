package com.healthcare.system.repositories.implementation;

import com.healthcare.system.entities.Appointment;
import com.healthcare.system.entities.Reason;
import com.healthcare.system.entities.Report;
import com.healthcare.system.entities.Status;
import com.healthcare.system.exceptions.WrongCredentials;
import com.healthcare.system.repositories.ReportRepository;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.rmi.ServerException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReportRepositoryImpl implements ReportRepository {


    private final DataSource dataSource;
    public ReportRepositoryImpl(DataSource dataSource) {
        this.dataSource = dataSource;

    }
    @Override
    public void save(Report report) throws WrongCredentials,ServerException {
        try {
            findById(report.getId());
        } catch (WrongCredentials wrongCredentials ) {
            try(Connection connection = dataSource.getConnection()) {
                String sql = "insert into vital_report (advice,status,id) values(?, ?, ?)";
                try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                    configureReport(report, preparedStatement);
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
    }

    @Override
    public Report findById(int id) throws WrongCredentials,ServerException{
        try (Connection connection = dataSource.getConnection()) {
            try(PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM vital_report WHERE id = ?")) {
                preparedStatement.setInt(1, id);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return createReport(resultSet);
                    } else {
                        throw new WrongCredentials("Report with ID: " + id + " does not exist");
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
            try(PreparedStatement preparedStatement = connection.prepareStatement("delete from vital_report where id = ?")){
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
    public void update(Report report) throws WrongCredentials,ServerException {
        try(Connection connection = dataSource.getConnection()) {
            String sql = "update vital_report set advice=?, status=? where id = ?";
            try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                configureReport(report, preparedStatement);
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
    public List<Report> findAll() throws WrongCredentials,ServerException {
        List<Report> reportList = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM vital_report")) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Report report = createReport(resultSet);
                    reportList.add(report);
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
        return reportList;
    }

    private Report createReport(ResultSet resultSet) throws SQLException, WrongCredentials, ServerException {
        Report report = new Report();
        report.setAdvice(resultSet.getString("advice"));
        report.setStatus(Status.valueOf(resultSet.getString("status")));
        report.setId(resultSet.getInt("id"));
        return report;
    }

    private void configureReport(Report report, PreparedStatement preparedStatement) throws WrongCredentials, SQLException {
        preparedStatement.setString(1, report.getAdvice());
        preparedStatement.setString(2,report.getStatus().toString());
        preparedStatement.setInt(3,report.getId());
        try{
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new WrongCredentials(e.getMessage());
        }
    }
}
