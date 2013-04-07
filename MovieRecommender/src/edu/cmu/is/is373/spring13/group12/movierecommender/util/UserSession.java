package edu.cmu.is.is373.spring13.group12.movierecommender.util;

public class UserSession {
	private String token;
	private String id;
	
	public void set(String id, String token) {
		this.id = id;
		this.token = token;
	}
	
	public String getId() {
		return id;
	}
	
	public String getToken() {
		return token;
	}
}
