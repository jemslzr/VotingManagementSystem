package com.app.view;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ElectionView {
    // ANSI escape codes for colors
    public static final String RESET = "\u001B[0m";
    public static final String BLACK_TEXT = "\u001B[30m";
    public static final String RED_TEXT = "\u001B[31m";
    public static final String BLUE_TEXT = "\u001B[34m";
    public static final String WHITE_BACKGROUND = "\u001B[47m";
    private static Scanner scanner = new Scanner(System.in);

    /*public ElectionView() {
        this.scanner = new Scanner(System.in);
    }8/

      /*Show Main Menu
     Welcome to the Election System
     1. Admin Login
     2. Voter Login
     3. Exit */ 
    public int showMainMenu() {
        System.out.println("");
        System.out.println(WHITE_BACKGROUND + BLUE_TEXT + "*****************************************************" + RESET);
        System.out.println(WHITE_BACKGROUND + BLUE_TEXT + "*                                                   *" + RESET);
        System.out.println(WHITE_BACKGROUND + BLUE_TEXT + "*" + BLUE_TEXT + "                   WELCOME!" + RED_TEXT + " 🙂                     " + BLUE_TEXT + "*" + RESET);
        System.out.println(WHITE_BACKGROUND + BLUE_TEXT + "*                                                   *" + RESET);
        System.out.println(WHITE_BACKGROUND + BLUE_TEXT + "*            " + BLUE_TEXT + "V" + BLACK_TEXT + "oting " + BLUE_TEXT + "M" + BLACK_TEXT + "anagement " + BLUE_TEXT + "S" + BLACK_TEXT + "ystem               " + BLUE_TEXT + "*" + RESET);
        System.out.println(WHITE_BACKGROUND + BLUE_TEXT + "*                                                   *" + RESET);
        System.out.println(WHITE_BACKGROUND + BLUE_TEXT + "*                                                   *" + RESET);
        System.out.println(WHITE_BACKGROUND + BLUE_TEXT + "*****************************************************" + RESET);
        System.out.println(WHITE_BACKGROUND + BLUE_TEXT + "*  " + BLUE_TEXT + "[1] " + BLACK_TEXT + "Admin Login" + BLUE_TEXT + "                                  *" + RESET);
        System.out.println(WHITE_BACKGROUND + BLUE_TEXT + "*  " + BLUE_TEXT + "[2] " + BLACK_TEXT + "Voter Login" + BLUE_TEXT + "                                  *" + RESET);
        System.out.println(WHITE_BACKGROUND + BLUE_TEXT + "*  " + BLUE_TEXT + "[3] " + BLACK_TEXT + "Exit" + BLUE_TEXT + "                                         *" + RESET);
        System.out.println(WHITE_BACKGROUND + BLUE_TEXT + "*****************************************************" + RESET);
        System.out.print(BLUE_TEXT + "Enter Choice: " + RESET);
        return scanner.nextInt();
    }

    // Admin login inputs
    //       view.getAdminUsername()
    public String getAdminUsername() {
        scanner.nextLine(); // Consume newline
        System.out.println("");
        System.out.print(BLUE_TEXT + "Enter admin username: " + RESET);
        return scanner.nextLine();
    }
    //       view.getAdminPassword()
    public String getAdminPassword() {
        System.out.print(BLUE_TEXT + "Enter admin password: " + RESET);
        return scanner.nextLine();
    }

    /*Show Admin Menu
      Admin Menu:
      1. Register Voter
      2. Register Candidate
      3. Total Votes for Candidates
      4. Total Number of Voters
      5. Delete Voter
      6. Delete Candidate
      7. Update Voter Information
      8. Exit */
    public int showAdminMenu() {
        System.out.println("");
        System.out.println(WHITE_BACKGROUND + BLUE_TEXT + "*****************************************************" + RESET);
        System.out.println(WHITE_BACKGROUND + BLUE_TEXT + "*                                                   *" + RESET);
        System.out.println(WHITE_BACKGROUND + BLUE_TEXT + "*                                                   *" + RESET);
        System.out.println(WHITE_BACKGROUND + BLUE_TEXT + "*           " + BLACK_TEXT + "      You are now in         " + BLUE_TEXT + "           *" + RESET);
        System.out.println(WHITE_BACKGROUND + BLUE_TEXT + "*           " + BLACK_TEXT + "        Admin Menu            " + BLUE_TEXT + "          *" + RESET);
        System.out.println(WHITE_BACKGROUND + BLUE_TEXT + "*                                                   *" + RESET);
        System.out.println(WHITE_BACKGROUND + BLUE_TEXT + "*                                                   *" + RESET);
        System.out.println(WHITE_BACKGROUND + BLUE_TEXT + "*****************************************************" + RESET);
        System.out.println(WHITE_BACKGROUND + BLUE_TEXT + "*  " + BLUE_TEXT + "[1]  " + BLACK_TEXT + "Register Voter" + BLUE_TEXT + "                              *" + RESET);
        System.out.println(WHITE_BACKGROUND + BLUE_TEXT + "*  " + BLUE_TEXT + "[2]  " + BLACK_TEXT + "Register Candidate" + BLUE_TEXT + "                          *" + RESET);
        System.out.println(WHITE_BACKGROUND + BLUE_TEXT + "*  " + BLUE_TEXT + "[3]  " + BLACK_TEXT + "Total Votes for Candidates" + BLUE_TEXT + "                  *" + RESET);
        System.out.println(WHITE_BACKGROUND + BLUE_TEXT + "*  " + BLUE_TEXT + "[4]  " + BLACK_TEXT + "Total Number of Voters" + BLUE_TEXT + "                      *" + RESET);
        System.out.println(WHITE_BACKGROUND + BLUE_TEXT + "*  " + BLUE_TEXT + "[5]  " + BLACK_TEXT + "Delete Voter" + BLUE_TEXT + "                                *" + RESET);
        System.out.println(WHITE_BACKGROUND + BLUE_TEXT + "*  " + BLUE_TEXT + "[6]  " + BLACK_TEXT + "Delete Candidate" + BLUE_TEXT + "                            *" + RESET);
        System.out.println(WHITE_BACKGROUND + BLUE_TEXT + "*  " + BLUE_TEXT + "[7]  " + BLACK_TEXT + "Update Voter Information" + BLUE_TEXT + "                    *" + RESET);
        System.out.println(WHITE_BACKGROUND + BLUE_TEXT + "*  " + BLUE_TEXT + "[8] " + BLACK_TEXT + "Exit" + BLUE_TEXT + "                                         *" + RESET);
        System.out.println(WHITE_BACKGROUND + BLUE_TEXT + "*****************************************************" + RESET);
        System.out.print(BLUE_TEXT + "Enter Choice: " + RESET);
        return scanner.nextInt();
    }

    // In ElectionView.java
    public void displayTotalVotes(ResultSet rs) {
        try {
            while (rs.next()) {
                String candidateName = rs.getString("candidate_name");
                int totalVotes = rs.getInt("total_votes");
                System.out.println("");       
                System.out.println(BLUE_TEXT + "*****************************************************" + RESET);
                System.out.println(BLACK_TEXT + "Candidate: " + BLUE_TEXT + candidateName + BLACK_TEXT +  " , Total Votes: " + BLUE_TEXT + totalVotes + RESET);
                System.out.println(BLUE_TEXT + "*****************************************************" + RESET);
            }
        } catch (SQLException e) {
            System.out.println(BLUE_TEXT +"Error displaying total votes: " + e.getMessage());
        }
    }
    
     // Display total voters
    public void displayTotalVoters(int totalVoters) {
        System.out.println("");       
        System.out.println(WHITE_BACKGROUND + BLUE_TEXT + "*****************************************************" + RESET);
        System.out.println(WHITE_BACKGROUND + BLUE_TEXT + "*" + BLACK_TEXT + "          Total number of voters:       " + BLUE_TEXT + totalVoters + BLUE_TEXT +"          *" + RESET);
        System.out.println(WHITE_BACKGROUND + BLUE_TEXT + "*****************************************************" + RESET);
    }   
    //Get voter ID to delete
    public int getVoterIdToDelete() {
        System.out.print("");
        System.out.print(BLUE_TEXT + "Enter Voter ID to delete: " + RESET);
        return scanner.nextInt();
    }    
    
    // Get candidate ID to delete
    public int getCandidateIdToDelete() {
        System.out.print("");
        System.out.print(BLUE_TEXT + "Enter Candidate ID to delete: " + RESET);
        return scanner.nextInt();
    }
     
    // Show Voter Menu
    public int showVoterMenu() {
        System.out.println("");
        System.out.println(WHITE_BACKGROUND + BLUE_TEXT + "*****************************************************" + RESET);
        System.out.println(WHITE_BACKGROUND + BLUE_TEXT + "*                                                   *" + RESET);
        System.out.println(WHITE_BACKGROUND + BLUE_TEXT + "*                                                   *" + RESET);
        System.out.println(WHITE_BACKGROUND + BLUE_TEXT + "*           " + BLACK_TEXT + "      You are now in         " + BLUE_TEXT + "           *" + RESET);
        System.out.println(WHITE_BACKGROUND + BLUE_TEXT + "*           " + BLACK_TEXT + "        Voter Menu            " + BLUE_TEXT + "          *" + RESET);
        System.out.println(WHITE_BACKGROUND + BLUE_TEXT + "*                                                   *" + RESET);
        System.out.println(WHITE_BACKGROUND + BLUE_TEXT + "*                                                   *" + RESET);
        System.out.println(WHITE_BACKGROUND + BLUE_TEXT + "*****************************************************" + RESET);
        System.out.println(WHITE_BACKGROUND + BLUE_TEXT + "*  " + BLUE_TEXT + "[1] " + BLACK_TEXT + "View Candidates" + BLUE_TEXT + "                              *" + RESET);
        System.out.println(WHITE_BACKGROUND + BLUE_TEXT + "*  " + BLUE_TEXT + "[2] " + BLACK_TEXT + "Cast Vote" + BLUE_TEXT + "                                    *" + RESET);
        System.out.println(WHITE_BACKGROUND + BLUE_TEXT + "*  " + BLUE_TEXT + "[3] " + BLACK_TEXT + "Exit" + BLUE_TEXT + "                                         *" + RESET);
        System.out.println(WHITE_BACKGROUND + BLUE_TEXT + "*****************************************************" + RESET);
        System.out.print(BLUE_TEXT + "Enter Choice: " + RESET);
        return scanner.nextInt();
        
    }

    // Get voter name
    public String getVoterName() {
        scanner.nextLine(); // Consume newline
        System.out.println("");
        System.out.print(BLUE_TEXT + "Enter your name to vote: " + RESET);
        return scanner.nextLine();
    }

    // Display candidates
    // In ElectionView.java
    public void displayCandidates(ResultSet rs) {
        try {
            System.out.println("");
            System.out.println(BLUE_TEXT + "------------------------------------------------------------------------------" + RESET);
            System.out.printf(BLACK_TEXT + "%-21s | %-16s | %-21s%n" + RESET, "Candidate Name", "Position", "Party List");
            System.out.println(BLUE_TEXT + "------------------------------------------------------------------------------" + RESET);

            while (rs.next()) {
                String name = rs.getString("name");
                String position = rs.getString("position");
                String party = rs.getString("party_list");

                System.out.printf("%-30s | %-25s | %-25s%n", BLACK_TEXT + name + RESET, BLACK_TEXT + position + RESET, BLACK_TEXT + party + RESET);
            }

            System.out.println(BLUE_TEXT + "------------------------------------------------------------------------------" + RESET);

        } catch (SQLException e) {
            System.out.println("Error displaying candidates: " + e.getMessage());
        }
    }

    // Get candidate name
    public String getCandidateName() {
        scanner.nextLine();
        System.out.print(BLUE_TEXT + "Enter the name of the candidate you want to vote for: " + RESET);
        return scanner.nextLine();
    }
   
    public void clearScannerBuffer() {
    scanner.nextLine(); // consume the invalid input (e.g., letters)
    }
}
