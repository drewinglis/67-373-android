package edu.cmu.is.is373.spring13.group12.movierecommender.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;

public abstract class MoviesAPI {
	private static final String URL = "movierecommender.herokuapp.com";
	
	public static String sendFacebookLogin(String email, String name, String fbToken, String fbId, String age) throws IOException {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("email", email));
		nameValuePairs.add(new BasicNameValuePair("age", age));
		nameValuePairs.add(new BasicNameValuePair("facebook_access_token", fbToken));
		nameValuePairs.add(new BasicNameValuePair("facebook_id", fbId));
		nameValuePairs.add(new BasicNameValuePair("name", name));
		return securePostRequest("/mobile/facebook_connect", nameValuePairs);
	}
	
	protected static String secureGetRequest(String URI) throws IOException {
		HttpClient c = new DefaultHttpClient();
		HttpGet get = new HttpGet("https://" + URL + URI);
		HttpResponse r = c.execute(get);
		
		BufferedReader rd = new BufferedReader(new InputStreamReader(r.getEntity().getContent()));
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
		  sb.append(line);
		}
		rd.close();
		
		return sb.toString();
	}
	
	protected static String securePostRequest(String URI, List<NameValuePair> data) throws IOException {
		HttpClient c = new DefaultHttpClient();
		HttpPost post = new HttpPost("https://" + URL + URI);
		post.setEntity(new UrlEncodedFormEntity(data));
		HttpResponse r = c.execute(post);
		
		BufferedReader rd = new BufferedReader(new InputStreamReader(r.getEntity().getContent()));
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
		  sb.append(line);
		}
		rd.close();
		
		return sb.toString();
	}
	
	protected static String getRequest(String URI) throws IOException {
		HttpClient c = new DefaultHttpClient();
		HttpGet get = new HttpGet("http://" + URL + URI);
		HttpResponse r = c.execute(get);
		
		BufferedReader rd = new BufferedReader(new InputStreamReader(r.getEntity().getContent()));
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
		  sb.append(line);
		}
		rd.close();
		
		return sb.toString();
	}
}
