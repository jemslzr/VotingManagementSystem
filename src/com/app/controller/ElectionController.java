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
            try {
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
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                view.clearScannerBuffer(); 
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

//ADMIN MENU    
    private void adminMenu() {
        boolean adminRunning = true;
        while (adminRunning) {
            try {
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
                        int voterID = view.getVoterIdToDelete();
                        deleteVoter(voterID);
                        break;
                    case 6:
                        int candidateId = view.getCandidateIdToDelete();
                        deleteCandidate(candidateId);
                        break;
                    case 7:
                        updateVoterInformation();
                        break;
                    case 8:
                        adminRunning = false;
                        break;
                    default:
                        System.out.println("Invalid option. Please enter a valid number.");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                view.clearScannerBuffer(); 
            }
        }
    }

    private boolean registerVoter() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter voter name: ");
        String name = scanner.nextLine();
        if (!name.matches("[a-zA-Z\\s]+")) { //Check if name is not a number
            System.out.println("Error: Name should not contain numbers or special characters.");
            return false;
        }
        
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
        if (!name.matches("[a-zA-Z\\s]+")) { //Check if name is not a number
            System.out.println("Error: Name should not contain numbers or special characters.");
            return;
        }   
        
        System.out.print("Enter candidate position: ");
        String position = scanner.nextLine();
        if (!position.matches("[a-zA-Z\\s]+")) { //Check if candidate position is not a number
            System.out.println("Error: Position should not contain numbers or special characters.");
            return;
        }        
        
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

    public boolean deleteVoter(int id) {
        boolean success = model.deleteVoter(id);
        if (success) {
            System.out.println("Voter deleted successfully.");
        } else {
            System.out.println("Failed to delete voter. Make sure the ID exists.");
        }
        return false;
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
    
//VOTERS MENU    
    private void voterMenu(String voterName) {
        boolean running = true;
        while (running) {
            try {
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
                        System.out.println("Invalid option. Please enter a valid number.");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                view.clearScannerBuffer(); 
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

        // Check if the candidate exists
        try (ResultSet rs = model.getCandidates()) {
            boolean candidateExists = false;
            while (rs != null && rs.next()) {
                if (candidateName.equalsIgnoreCase(rs.getString("name"))) {
                    candidateExists = true;
                    break;
                }
            }
            if (!candidateExists) {
                System.out.println("Error: The candidate \"" + candidateName + "\" is not registered.");
                return;
            }
        } catch (SQLException e) {
            System.out.println("Error validating candidate: " + e.getMessage());
            return;
        }
        // If candidate exists, proceed to vote
        if (model.castVote(voterName, candidateName)) {
            System.out.println("Vote successfully cast.");
        } else {
            System.out.println("Error casting vote. You may have already voted.");
        }
    }

    private boolean updateVoterInformation() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter current voter name to update: ");
        String oldName = scanner.nextLine();
        if (!oldName.matches("[a-zA-Z\\s]+")) { //Check if name is not a number
            System.out.println("Error: Name should not contain numbers or special characters.");
            return false;
        }
        System.out.print("Enter new name: ");
        String newName = scanner.nextLine();
        if (!newName.matches("[a-zA-Z\\s]+")) { //Check if name is not a number
            System.out.println("Error: Name should not contain numbers or special characters.");
            return false;
        }
        
        System.out.print("Enter new address: ");
        String newAddress = scanner.nextLine();

        System.out.print("Enter new date of birth (YYYY-MM-DD): ");
        String newDOB = scanner.nextLine();
 
        try {
            LocalDate birthDate = LocalDate.parse(newDOB); 
            LocalDate currentDate = LocalDate.now();
            // Calculate age
            int age = Period.between(birthDate, currentDate).getYears();
            // Check if the age is valid (at least 18)
            if (age < 18) {
                System.out.println("Error: Voter must be at least 18 years old to register.");
                return false;               
        }
            if (model.updateVoterInfo(oldName, newName, newAddress, newDOB)) {
                System.out.println("Voter information updated successfully.");
                return true;
            } else {
                System.out.println("Error updating voter information.");
                return false;
            }
        } catch (Exception e) {
            System.out.println("Error calculating age: " + e.getMessage());
            return false; 
        }
    } 
}
