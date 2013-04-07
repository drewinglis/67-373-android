package edu.cmu.is.is373.spring13.group12.movierecommender.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import android.os.AsyncTask;

public class GetDetailsTask extends AsyncTask<Integer, Void, String> {
	@Override
	protected String doInBackground(Integer... params) {
		try {
			return MoviesAPI.getRequest("/movies/" + params[0] + ".json");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
