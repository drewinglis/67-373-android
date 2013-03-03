package edu.cmu.is.is373.spring13.group12.movierecommender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {
	private final String serverURL = "127.0.0.1:3000";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void searchMovies(View button) {
		final EditText movieField = (EditText) findViewById(R.id.moviesSearchBox);  
		String name = movieField.getText().toString();
		
		try {
			String json = getMoviesFromServer(name);
		}
		catch(IOException e) {
			return;
		}
	}
	
	public String getMoviesFromServer(String query) throws IOException {
		String encodedQuery = URLEncoder.encode(query, "UTF-8");
		URL url = new URL(serverURL + "/movies.json/query=" + encodedQuery);
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
