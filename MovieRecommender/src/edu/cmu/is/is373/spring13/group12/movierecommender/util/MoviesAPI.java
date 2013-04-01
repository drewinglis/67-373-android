package edu.cmu.is.is373.spring13.group12.movierecommender.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

public abstract class MoviesAPI {
	private static final String URL = "movierecommender.herokuapp.com";
	
	public static String sendFacebookLogin(String email, String name, String fbToken, String fbId, int age) throws IOException {
		String encodedName = URLEncoder.encode(name, "UTF-8");
		String encodedEmail = URLEncoder.encode(email, "UTF-8");
		return secureGetRequest("/mobile.json?name=" + encodedName + "&email=" + encodedEmail + "&token=" + fbToken + "&id=" + fbId + "&age=" + age);
	}
	
	protected static String secureGetRequest(String URI) throws IOException {
		URL url = new URL("https://" + URL + URI);
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		
		if (conn.getResponseCode() != 200) {
		  throw new IOException(conn.getResponseMessage());
		}

		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
		  sb.append(line);
		}
		rd.close();
		
		conn.disconnect();
		return sb.toString();
	}
	
	protected static String getRequest(String URI) throws IOException {
		URL url = new URL("http://" + URL + URI);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		
		if (conn.getResponseCode() != 200) {
		  throw new IOException(conn.getResponseMessage());
		}

		BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
		  sb.append(line);
		}
		rd.close();
		
		conn.disconnect();
		return sb.toString();
	}
}
