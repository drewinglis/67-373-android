package edu.cmu.is.is373.spring13.group12.movierecommender.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import android.app.Activity;

public abstract class MoviesAPI {
	private static final String URL = "127.0.0.1:3000";
	
	public static String getDummyMoviesFromServer(String searchName, int page, Activity activity) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(activity.getAssets().open("toy"))); 
		String json = "";
		String line;
		while((line = reader.readLine()) != null) {
			json += line + "\n";
		}
		reader.close();
		
		return json;
	}
	
	public static String getMoviesFromServer(String searchName, int page) throws IOException {
		String encodedQuery = URLEncoder.encode(searchName, "UTF-8");
		URL url = new URL(URL + "/movies.json?query=" + encodedQuery + "&page=" + page);
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
