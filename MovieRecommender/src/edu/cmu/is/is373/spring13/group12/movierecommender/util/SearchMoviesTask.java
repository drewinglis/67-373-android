package edu.cmu.is.is373.spring13.group12.movierecommender.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import android.os.AsyncTask;

public class SearchMoviesTask extends AsyncTask<String, Void, String> {
	@Override
	protected String doInBackground(String... params) {
		try {
			String encodedQuery = URLEncoder.encode(params[0], "UTF-8");
			return MoviesAPI.getRequest("/movies.json?query=" + encodedQuery + "&page=" + params[1]);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
