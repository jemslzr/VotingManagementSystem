package com.app.view;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ElectionView {
    private Scanner scanner;

    public ElectionView() {
        this.scanner = new Scanner(System.in);
    }

    // Show Main Menu
    public int showMainMenu() {
        System.out.println("\n***Welcome to the Election System***");
        System.out.println("1. Admin Login");
        System.out.println("2. Voter Login");
        System.out.println("3. Exit");
        System.out.print("Enter Choice: ");
        return scanner.nextInt();
    }

    // Admin login inputs
    //       view.getAdminUsername()
    public String getAdminUsername() {
        scanner.nextLine(); // Consume newline
        System.out.print("Enter admin username: ");
        return scanner.nextLine();
    }
    
    //       view.getAdminPassword()
    public String getAdminPassword() {
        System.out.print("Enter admin password: ");
        return scanner.nextLine();
    }

    //Show Admin Menu
    public int showAdminMenu() {
        System.out.print("\n------------------------\n");        
        System.out.println("Admin Menu:");
        System.out.println("1. Register Voter");
        System.out.println("2. Register Candidate");
        System.out.println("3. Total Votes for Candidates");
        System.out.println("4. Total Number of Voters");
        System.out.println("5. Delete Inactive Voters");
        System.out.println("6. Delete Candidate");        
        System.out.println("7. Archive Inactive Voters");
        System.out.println("8. Archive Election Results");
        System.out.println("9. Update Voter Information");
        System.out.println("10. Update Candidate Information");
        System.out.println("11. Exit");       
        System.out.print("Enter Choice: ");
        return scanner.nextInt();
    }

    // Show Voter Menu
    public int showVoterMenu() {
        System.out.print("\n------------------------\n");         
        System.out.println("Voter Menu:");
        System.out.println("1. View Candidates");
        System.out.println("2. Cast Vote");
        System.out.println("3. Exit");
        System.out.print("Enter Choice: ");
        return scanner.nextInt();
    }

    // Get voter name
    public String getVoterName() {
        scanner.nextLine(); // Consume newline
        System.out.print("Enter your name to vote: ");
        return scanner.nextLine();
    }

    // Get candidate name
    public String getCandidateName() {
        scanner.nextLine();
        System.out.print("Enter the name of the candidate you want to vote for: ");
        return scanner.nextLine();
    }

    // Display candidates
    public void displayCandidates(ResultSet rs) {
        try {
            while (rs.next()) {
                String name = rs.getString("name");
                String position = rs.getString("position");
                String party = rs.getString("party_list");

                System.out.println("Candidate: " + name + ", Position: " + position + ", Party: " + party);
            }
        } catch (SQLException e) {
            System.out.println("Error displaying candidates: " + e.getMessage());
        }
    }

    // Display total votes
    public void displayTotalVotes(ResultSet rs) {
        try {
            while (rs.next()) {
                String candidateName = rs.getString("candidate_name"); //
                int totalVotes = rs.getInt("total_votes");

                System.out.println("Candidate: " + candidateName + ", Total Votes: " + totalVotes);
            }
        } catch (SQLException e) {
            System.out.println("Error displaying total votes: " + e.getMessage());
        }
    }
    
    // Prompt for candidate ID to delete
    public int getCandidateIdToDelete() {
        System.out.print("Enter Candidate ID to delete: ");
        return scanner.nextInt();
    }

    // Display total voters
    public void displayTotalVoters(int totalVoters) {
        System.out.println("Total number of voters: " + totalVoters);
    }
  
}
