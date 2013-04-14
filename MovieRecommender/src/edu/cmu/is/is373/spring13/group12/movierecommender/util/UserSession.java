package edu.cmu.is.is373.spring13.group12.movierecommender.util;

import java.io.Serializable;

public class UserSession implements Serializable {
	private static final long serialVersionUID = -1079902575813321695L;
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
