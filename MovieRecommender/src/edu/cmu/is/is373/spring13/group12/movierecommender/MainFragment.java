package edu.cmu.is.is373.spring13.group12.movierecommender;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.LoginButton;

import edu.cmu.is.is373.spring13.group12.movierecommender.util.BackendLoginTask;
import edu.cmu.is.is373.spring13.group12.movierecommender.util.UserSession;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainFragment extends Fragment {
	private static final String TAG = "MainFragment";
	private UiLifecycleHelper uiHelper;
	private Activity mainActivity;
	private UserSession userSession;
	private Session.StatusCallback callback = new Session.StatusCallback() {
	    @Override
	    public void call(Session session, SessionState state, Exception exception) {
	        onSessionStateChange(session, state, exception);
	    }
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    uiHelper = new UiLifecycleHelper(getActivity(), callback);
	    uiHelper.onCreate(savedInstanceState);
	}
	
	public void setParams(Activity main, UserSession user) {
		mainActivity = main;
		userSession = user;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, 
	        ViewGroup container, 
	        Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.activity_main, container, false);

	    LoginButton authButton = (LoginButton) view.findViewById(R.id.authButton);
	    authButton.setFragment(this);
		List<String> permissions = new ArrayList<String>();
		permissions.add("email");
		permissions.add("user_birthday");
		permissions.add("user_likes");
		permissions.add("friends_likes");
		permissions.add("user_interests");
		permissions.add("friends_interests");
	    authButton.setReadPermissions(permissions);
	    
	    return view;
	}
	
	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
	    if (state.isOpened()) {
	        Log.i(TAG, "Logged in...");
	        try {
				new BackendLoginTask().execute(userSession).get();
			} catch (InterruptedException e) {
				Log.e(TAG, e.getMessage(), e);
			} catch (ExecutionException e) {
				Log.e(TAG, e.getMessage(), e);
			}
	        
	        Button recommendationButton = (Button)mainActivity.findViewById(R.id.button2);
	        recommendationButton.setVisibility(View.VISIBLE);
	    } else if (state.isClosed()) {
	        Log.i(TAG, "Logged out...");
	        Button recommendationButton = (Button)mainActivity.findViewById(R.id.button2);
	        recommendationButton.setVisibility(View.GONE);
	    }
	}
	
	@Override
	public void onResume() {
	    super.onResume();
	    
	    Session session = Session.getActiveSession();
	    if (session != null &&
	           (session.isOpened() || session.isClosed()) ) {
	        onSessionStateChange(session, session.getState(), null);
	    }
	    
	    uiHelper.onResume();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onPause() {
	    super.onPause();
	    uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
	    super.onDestroy();
	    uiHelper.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
	    super.onSaveInstanceState(outState);
	    uiHelper.onSaveInstanceState(outState);
	}
}
