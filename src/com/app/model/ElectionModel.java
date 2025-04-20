package com.app.model;

import com.app.dao.ElectionDAO;
import java.sql.ResultSet;

public class ElectionModel {
    private ElectionDAO dao;

    public ElectionModel() {
        dao = new ElectionDAO();
    }

    public boolean adminLogin(String username, String password) {
        return dao.adminLogin(username, password);
    }

    public boolean registerVoter(String name, String address, String dateOfBirth) {
        return dao.registerVoter(name, address, dateOfBirth);
    }

    public boolean registerCandidate(String name, String position, String partyList) {
        return dao.registerCandidate(name, position, partyList);
    }

    public ResultSet getCandidates() {
        return dao.getCandidates();
    }

    public boolean castVote(String voterName, String candidateName) {
        return dao.castVote(voterName, candidateName);
    }

    public ResultSet getTotalVotes() {
        return dao.getTotalVotes();
    }

    public int getTotalVoters() {
        return dao.getTotalVoters();
    }

    public boolean deleteInactiveVoters() {
        return dao.deleteInactiveVoters();
    }

    public boolean archiveInactiveVoters() {
        return dao.archiveInactiveVoters();
    }

    public boolean archiveElectionResults() {
        return dao.archiveElectionResults();
    }
    
    public boolean updateVoterInfo(String oldName, String newName, String newAddress, String newDOB) {
        return dao.updateVoterInfo(oldName, newName, newAddress, newDOB);
    }
    
    public boolean updateCandidateInfo(String oldName, String newName, String newPosition, String newParty) {
        return dao.updateCandidateInfo(oldName, newName, newPosition, newParty);
    }

    public boolean deleteCandidate(int id) {
        return dao.deleteCandidate(id);
    }
}
