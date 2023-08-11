package de.subscripted.Unused;

import net.dv8tion.jda.api.entities.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JobSQLManager {

    private Connection connection;

    public JobSQLManager() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:varilx_jobs.db");
            createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS jobs (" + "id INTEGER PRIMARY KEY AUTOINCREMENT," + "name TEXT NOT NULL)";
        try {
            connection.createStatement().executeUpdate(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createJob(String name) {
        String insertJobSQL = "INSERT INTO jobs (name) VALUES (?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(insertJobSQL)) {
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> getAllJobs() {
        List<String> jobs = new ArrayList<>();
        String selectJobsSQL = "SELECT name FROM jobs";
        try (ResultSet resultSet = connection.createStatement().executeQuery(selectJobsSQL)) {
            while (resultSet.next()) {
                jobs.add(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jobs;
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void joinJob(User user, String jobName) {
        String selectJobSQL = "SELECT id FROM jobs WHERE name = ?";
        String insertUserJobSQL = "INSERT INTO user_jobs (user_id, job_id) VALUES (?, ?)";
        try (PreparedStatement selectJobStatement = connection.prepareStatement(selectJobSQL);
             PreparedStatement insertUserJobStatement = connection.prepareStatement(insertUserJobSQL)) {

            selectJobStatement.setString(1, jobName);
            ResultSet resultSet = selectJobStatement.executeQuery();

            if (resultSet.next()) {
                int jobId = resultSet.getInt("id");
                insertUserJobStatement.setLong(1, user.getIdLong());
                insertUserJobStatement.setInt(2, jobId);
                insertUserJobStatement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void leaveJob(User user, String jobName) {
        String selectJobSQL = "SELECT id FROM jobs WHERE name = ?";
        String deleteUserJobSQL = "DELETE FROM user_jobs WHERE user_id = ? AND job_id = ?";
        try (PreparedStatement selectJobStatement = connection.prepareStatement(selectJobSQL);
             PreparedStatement deleteUserJobStatement = connection.prepareStatement(deleteUserJobSQL)) {

            selectJobStatement.setString(1, jobName);
            ResultSet resultSet = selectJobStatement.executeQuery();

            if (resultSet.next()) {
                int jobId = resultSet.getInt("id");
                deleteUserJobStatement.setLong(1, user.getIdLong());
                deleteUserJobStatement.setInt(2, jobId);
                deleteUserJobStatement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteJob(String jobName) {
        String deleteJobSQL = "DELETE FROM jobs WHERE name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteJobSQL)) {
            preparedStatement.setString(1, jobName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
