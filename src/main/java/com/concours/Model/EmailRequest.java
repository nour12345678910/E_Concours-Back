package com.concours.Model;

import java.util.List;

public class EmailRequest {
	
	
    private List<String> candidateEmails;
    private List<CandidatInfo> candidats;
    private List<User> users;
   

    public List<CandidatInfo> getCandidats() {
    	return candidats;
}

	public void setCandidats(List<CandidatInfo> candidats) {
	this.candidats = candidats;
	}
	
	public List<User> getUsers() {
	return users;
	}
	
	public void setUsers(List<User> users) {
	this.users = users;
	}
	
	public List<String> getCandidateEmails() {
	        return candidateEmails;
	    }
	
	    public void setCandidateEmails(List<String> candidateEmails) {
	        this.candidateEmails = candidateEmails;
	    }
	}
	
