package com.app.dao;

import com.app.util.DBConnection;
import java.sql.*;

public class ElectionDAO { //implementation

    public boolean adminLogin(String username, String password) {
        return "admin".equals(username) && "admin".equals(password);
    }

    public boolean registerVoter(String name, String address, String dateOfBirth) {
        String query = "INSERT INTO Voters (name, address, date_of_birth) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, name);
            stmt.setString(2, address);
            stmt.setString(3, dateOfBirth);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error registering voter: " + e.getMessage());
        }
        return false;
    }

    public boolean registerCandidate(String name, String position, String partyList) {
        String query = "INSERT INTO Candidates (name, position, party_list) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, name);
            stmt.setString(2, position);
            stmt.setString(3, partyList);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error registering candidate: " + e.getMessage());
        }
        return false;
    }

    public ResultSet getCandidates() {
        String query = "SELECT * FROM Candidates";
        try {
            Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            return stmt.executeQuery(query);
        } catch (SQLException e) {
            System.out.println("Error fetching candidates: " + e.getMessage());
        }
        return null;
    }

    public boolean castVote(String voterName, String candidateName) {
        String checkQuery = "SELECT * FROM Votes WHERE voter_name = ?";
        String insertQuery = "INSERT INTO Votes (voter_name, candidate_name) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {

            checkStmt.setString(1, voterName);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                System.out.println("You have already voted.");
                return false;
            }

            try (PreparedStatement voteStmt = conn.prepareStatement(insertQuery)) {
                voteStmt.setString(1, voterName);
                voteStmt.setString(2, candidateName);
                return voteStmt.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            System.out.println("Error casting vote: " + e.getMessage());
        }
        return false;
    }

    public ResultSet getTotalVotes() {
        String query = "SELECT candidate_name, COUNT(*) AS total_votes FROM Votes GROUP BY candidate_name"; //
        try {
            Connection conn = DBConnection.getConnection();
            Statement stmt = conn.createStatement();
            return stmt.executeQuery(query);
        } catch (SQLException e) {
            System.out.println("Error fetching total votes: " + e.getMessage());
        }
        return null;
    }

    public int getTotalVoters() {
        String query = "SELECT COUNT(*) AS total_voters FROM Voters";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                return rs.getInt("total_voters");
            }

        } catch (SQLException e) {
            System.out.println("Error fetching total voters: " + e.getMessage());
        }
        return 0;
    }

    public boolean deleteVoter(int id) {
        String query = "DELETE FROM Voters WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0; // true if deletion was successful
        } catch (SQLException e) {
            System.out.println("Error deleting voter: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteCandidate(int id) {
        String query = "DELETE FROM Candidates WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0; // true if deletion was successful
        } catch (SQLException e) {
            System.out.println("Error deleting candidate: " + e.getMessage());
            return false;
        }
    }    
    
    public boolean updateVoterInfo(String oldName, String newName, String newAddress, String newDOB) {
        String query = "UPDATE Voters SET name = ?, address = ?, date_of_birth = ? WHERE name = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, newName);
            stmt.setString(2, newAddress);
            stmt.setString(3, newDOB);
            stmt.setString(4, oldName);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error updating voter info: " + e.getMessage());
        }
        return false;
    }

}
