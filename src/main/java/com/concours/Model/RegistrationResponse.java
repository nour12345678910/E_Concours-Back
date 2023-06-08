package com.concours.Model;

public class RegistrationResponse {
    private User user;
    private String token;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public RegistrationResponse(User user, String token) {
		super();
		this.user = user;
		this.token = token;
	}
	public RegistrationResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
    

}
