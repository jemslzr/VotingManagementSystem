package com.app.controller;

import com.app.dao.ElectionDAO;
import com.app.model.ElectionModel;
import com.app.view.ElectionView;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.Period;

public class ElectionController {
    private ElectionView view;
    private ElectionModel model;
    private ElectionDAO dao;  

    public ElectionController(ElectionView view, ElectionModel model, ElectionDAO dao) {
        this.view = view;
        this.model = model;
        this.dao = dao;  // Assign DAO
    }

    public void run() {
        
        boolean running = true;

        while (running) {
            int choice = view.showMainMenu();
            switch (choice) {
                case 1:
                    adminLogin();
                    break;
                case 2:
                    voterLogin();
                    break;
                case 3:
                    running = false;
                    System.out.println("Exiting the program.");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void adminLogin() {
        String adminUsername = view.getAdminUsername();
        String adminPassword = view.getAdminPassword();
        if (model.adminLogin(adminUsername, adminPassword)) {
            adminMenu();
        } else {
            System.out.println("Invalid admin credentials. Returning to main menu.");
        }
    }

    private void voterLogin() {
        String voterName = view.getVoterName();
        voterMenu(voterName);
    }

    private void adminMenu() {
        boolean adminRunning = true;
        while (adminRunning) {
            int choice = view.showAdminMenu();
            switch (choice) {
                case 1:
                    registerVoter();
                    break;
                case 2:
                    registerCandidate();
                    break;
                case 3:
                    viewTotalVotes();
                    break;
                case 4:
                    viewTotalVoters();
                    break;
                case 5:
                    deleteInactiveVoters();
                    break;
                case 6:
                    int candidateId = view.getCandidateIdToDelete(); // prompt from ElectionView
                    deleteCandidate(candidateId);
                    break;
                case 7:
                    archiveInactiveVoters();
                    break;
                case 8:
                    archiveElectionResults();
                    break;
                case 9:
                    updateVoterInformation();
                    break;
                case 10:
                    updateCandidateInformation();
                    break;
                case 11:
                    adminRunning = false;
                    break;
                default:
                    System.out.println("Invalid option. Returning to admin menu.");
            }
        }
    }

    private void archiveInactiveVoters() {
        if (model.archiveInactiveVoters()) {
            System.out.println("Inactive voters archived successfully.");
        } else {
            System.out.println("Error archiving inactive voters.");
        }
    }

    private void archiveElectionResults() {
        if (model.archiveElectionResults()) {
            System.out.println("Election results archived successfully.");
        } else {
            System.out.println("Error archiving election results.");
        }
    }

private boolean registerVoter() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter voter name: ");
    String name = scanner.nextLine();
    System.out.print("Enter voter address: ");
    String address = scanner.nextLine();
    System.out.print("Enter voter date of birth (YYYY-MM-DD): ");
    String dateOfBirth = scanner.nextLine();

    try {
        LocalDate birthDate = LocalDate.parse(dateOfBirth); 
        LocalDate currentDate = LocalDate.now();

        // Calculate age
        int age = Period.between(birthDate, currentDate).getYears();

        // Check if the age is valid (at least 18)
        if (age < 18) {
            System.out.println("Error: Voter must be at least 18 years old to register.");
            return false; 
        }

        if (model.registerVoter(name, address, dateOfBirth)) {
            System.out.println("Voter successfully registered.");
            return true; // Successfully registered
        } else {
            System.out.println("Error registering voter.");
            return false; // Registration failed
        }

    } catch (Exception e) {
        System.out.println("Error calculating age: " + e.getMessage());
        return false; 
    }
}

    private void registerCandidate() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter candidate name: ");
        String name = scanner.nextLine();
        System.out.print("Enter candidate position: ");
        String position = scanner.nextLine();
        System.out.print("Enter candidate party: ");
        String party = scanner.nextLine();

        if (model.registerCandidate(name, position, party)) {
            System.out.println("Candidate successfully registered.");
        } else {
            System.out.println("Error registering candidate.");
        }
    }
    
    private void viewTotalVotes() {
        ResultSet rs = model.getTotalVotes();  // Get the total votes from the model
        if (rs != null) {
            view.displayTotalVotes(rs);  // Pass the ResultSet to the view
        } else {
            System.out.println("No votes found.");
        }

        try {
            if (rs != null) {
                rs.close();  // Close ResultSet once done
            }
        } catch (SQLException e) {
            System.out.println("Error closing ResultSet: " + e.getMessage());
        }
    }


    private void viewTotalVoters() {
        int totalVoters = model.getTotalVoters();
        view.displayTotalVoters(totalVoters);
    }

    private void deleteInactiveVoters() {
        if (model.deleteInactiveVoters()) {
            System.out.println("Inactive voters deleted successfully.");
        } else {
            System.out.println("Error deleting inactive voters.");
        }
    }

    public boolean deleteCandidate(int id) {
        boolean success = model.deleteCandidate(id);
        if (success) {
            System.out.println("Candidate deleted successfully.");
        } else {
            System.out.println("Failed to delete candidate. Make sure the ID exists.");
        }
        return false;
    }
    
    
    private void voterMenu(String voterName) {
        boolean running = true;
        while (running) {
            int choice = view.showVoterMenu();
            switch (choice) {
                case 1:
                    viewCandidates();
                    break;
                case 2:
                    castVote(voterName);
                    break;
                case 3:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Returning to voter menu.");
                    System.out.println("----------------------------\n");
            }
        }
    }

    private void viewCandidates() {
        ResultSet rs = model.getCandidates();  // Get the candidates from the model
        if (rs != null) {
            view.displayCandidates(rs);  // Pass the ResultSet to the view
        } else {
            System.out.println("No candidates found.");
        }

        try {
            if (rs != null) {
                rs.close();  // Close ResultSet once done
            }
        } catch (SQLException e) {
            System.out.println("Error closing ResultSet: " + e.getMessage());
        }
    }

    private void castVote(String voterName) {
        String candidateName = view.getCandidateName();
        if (model.castVote(voterName, candidateName)) {
            System.out.println("Vote successfully cast.");
        } else {
            System.out.println("Error casting vote.");
        }
    }
    
    private void updateVoterInformation() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter current voter name to update: ");
        String oldName = scanner.nextLine();

        System.out.print("Enter new name: ");
        String newName = scanner.nextLine();

        System.out.print("Enter new address: ");
        String newAddress = scanner.nextLine();

        System.out.print("Enter new date of birth (YYYY-MM-DD): ");
        String newDOB = scanner.nextLine();

        if (model.updateVoterInfo(oldName, newName, newAddress, newDOB)) {
            System.out.println("Voter information updated successfully.");
        } else {
            System.out.println("Error updating voter information.");
        }
    }

    private void updateCandidateInformation() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter current candidate name to update: ");
        String oldName = scanner.nextLine();

        System.out.print("Enter new name: ");
        String newName = scanner.nextLine();

        System.out.print("Enter new position: ");
        String newPosition = scanner.nextLine();

        System.out.print("Enter new party: ");
        String newParty = scanner.nextLine();

        if (model.updateCandidateInfo(oldName, newName, newPosition, newParty)) {
            System.out.println("Candidate information updated successfully.");
        } else {
            System.out.println("Error updating candidate information.");
        }
    }    
}
