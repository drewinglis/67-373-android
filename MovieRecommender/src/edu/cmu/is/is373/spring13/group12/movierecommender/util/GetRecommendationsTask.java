package edu.cmu.is.is373.spring13.group12.movierecommender.util;

import java.io.IOException;

import android.os.AsyncTask;
import android.util.Log;
public class GetRecommendationsTask extends AsyncTask<String, Void, String> {
	@Override
	protected String doInBackground(String... params) {
		try {
			return MoviesAPI.secureGetRequest("/movies.json?user_id=" + params[0] + "&token=" + params[1]);
		} catch (IOException e) {
			Log.e("Fail", e.getMessage(), e);
		}
		return null;
	}
}

