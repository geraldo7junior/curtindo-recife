package com.br.curtindorecife;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.OpenRequest;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionDefaultAudience;
import com.facebook.SessionLoginBehavior;
import com.facebook.SessionState;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.Util;
import com.facebook.model.GraphUser;

import android.os.Bundle;
import android.os.Handler;
import android.provider.OpenableColumns;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class TelaFacebook extends Activity {

	private Facebook facebook;
	private SharedPreferences prefs;
	private static final String APP_ID = "670005079718273";

	private static final String ACCESS_EXPIRES = "access_expires";
	private static final String ACCESS_TOKEN = "access_token";
	private Handler mHandler = new Handler();
	private static Bundle saveInstance;
	
	
	public static Bundle getSaveInstance() {
		return saveInstance;
	}
	public static void setSaveInstance(Bundle saveInstance) {
		TelaFacebook.saveInstance = saveInstance;
	}

	private static final String[] PERMISSIONS = new String[] {"publish_stream", 
        "read_stream", "offline_access"};

	Button btnPublicar;
	private Bitmap image;
	@SuppressWarnings("deprecation")
	private AsyncFacebookRunner mAsyncRunner;

	String[] salvarimg = new String[0];

	String corpo;

	private int cont = 0;


	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tela_facebook);
		facebook = new Facebook(APP_ID);
		prefs = getPreferences(MODE_PRIVATE);
		mAsyncRunner = new AsyncFacebookRunner(facebook);
		
		btnPublicar=(Button) findViewById(R.id.btnPublicar);
		btnPublicar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				facebook.dialog(TelaFacebook.this, "stream.publish", new WallPostDialogListener());
				
			}
		});
		chamarFacebook();
		
		
		/*try {
	        openActiveSession(this, true, fbStatusCallback, Arrays.asList(
	                new String[] { "email",  "user_location", "user_birthday", "user_likes", "publish_actions" }),savedInstanceState );
	    }
	    catch (Exception e) {
	        e.printStackTrace();
	    }*/
	}
	
		/*private  Session openActiveSession(Activity activity, boolean allowLoginUI, StatusCallback callback, List<String> permissions, Bundle savedInstanceState) {
	    OpenRequest openRequest = new OpenRequest(activity).
	setPermissions(permissions).setLoginBehavior(SessionLoginBehavior.
	SSO_WITH_FALLBACK).setCallback(callback).
	setDefaultAudience(SessionDefaultAudience.FRIENDS);

	    Session session = Session.getActiveSession();
	    Log.d("Sessâo", "" + session);
	    if (session == null) {
	        Log.d("Sessâo", "" + savedInstanceState);
	        if (savedInstanceState != null) {
	            session = Session.restoreSession(this, null, fbStatusCallback, savedInstanceState);
	        }
	        if (session == null) {
	            session = new Session(this);
	        }
	        Session.setActiveSession(session);
	        if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED) || allowLoginUI) {
	            session.openForRead(openRequest);
	            return session;
	        }
	    }
	    return null;
	  }
	
	  
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
	    Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
	}*/
	
		
		
	/*	// Carrega a accessToken pra saber se o usuário
		// já se autenticou.
		loadAccessToken();
		
		if(!facebook.isSessionValid()){
			
			facebook.authorize(this, PERMISSIONS, new LoginDialogListener());
			this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			
		}
		//facebook.dialog(TelaFacebook.this, "stream.publish", new WallPostDialogListener());
		
		
		btnPublicar=(Button) findViewById(R.id.btnPublicar);
		btnPublicar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				facebook.dialog(TelaFacebook.this, "stream.publish", new WallPostDialogListener());
				
			}
		});

	}
	*/
	public void chamarFacebook(){
		// Carrega a accessToken pra saber se o usuário
		// já se autenticou.
		loadAccessToken();
		
		if(!facebook.isSessionValid()){
			
			facebook.authorize(this, new String[] {"publish_stream","read_stream", "offline_access"}, new LoginDialogListener(){

				@Override
				public void onComplete(Bundle values) {
					// TODO Auto-generated method stub
					saveAccessToken();
					getProfileInformation();
				}

				@Override
				public void onFacebookError(FacebookError e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onError(DialogError e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onCancel() {
					// TODO Auto-generated method stub
					
				}
				
			});
			
			
		}else{
			getProfileInformation();
		}
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data, String face) {
	      super.onActivityResult(requestCode, resultCode, data);
	      Log.d("FB Sample App", "onActivityResult(): " + requestCode);
	      facebook.authorizeCallback(requestCode, resultCode, data);
	    }
	private class LoginDialogListener implements
	com.facebook.android.Facebook.DialogListener {

	/**
	* Called when the dialog has completed successfully
	*/
	public void onComplete(Bundle values) {
	// Process onComplete
		try {

            getProfileInformation();

        } catch (Exception error) {
            Toast.makeText(TelaFacebook.this, error.toString(),
                    Toast.LENGTH_SHORT).show();
        }
	Log.d("FB Sample App", "LoginDialogListener.onComplete()");
	// Dispatch on its own thread
	mHandler.post(new Runnable() {
	    public void run() {
	    }
	});
	}

	/**
	*
	*/
	public void onFacebookError(FacebookError error) {
	// Process error
	Log.d("FB Sample App", "LoginDialogListener.onFacebookError()");
	}

	/**
	*
	*/
	public void onError(DialogError error) {
	// Process error message
	Log.d("FB Sample App", "LoginDialogListener.onError()");
	}

	/**
	*
	*/
	public void onCancel() {
	// Process cancel message
	Log.d("FB Sample App", "LoginDialogListener.onCancel()");
	}
	}

	
	public class WallPostDialogListener implements
    com.facebook.android.Facebook.DialogListener {

/**
 * Called when the dialog has completed successfully
 */
public void onComplete(Bundle values) {
    final String postId = values.getString("post_id");
    if (postId != null) {
        Log.d("FB Sample App", "Dialog Success! post_id=" + postId);
        mAsyncRunner.request(postId, new WallPostRequestListener());
    } else {
        Log.d("FB Sample App", "No wall post made");
    }
}

@Override
public void onCancel() {
    // No special processing if dialog has been canceled
}

@Override
public void onError(DialogError e) {
    // No special processing if dialog has been canceled
}

@Override
public void onFacebookError(FacebookError e) {
    // No special processing if dialog has been canceled
}

public class WallPostRequestListener implements
com.facebook.android.AsyncFacebookRunner.RequestListener {

/**
* Called when the wall post request has completed
*/
public void onComplete(final String response) {
Log.d("Facebook-Example", "Got response: " + response);
}

public void onFacebookError(FacebookError e) {
// Ignore Facebook errors
}

public void onFileNotFoundException(FileNotFoundException e) {
// Ignore File not found errors
}

public void onIOException(IOException e) {
// Ignore IO Facebook errors
}

@Override
public void onComplete(String response, Object state) {
	// TODO Auto-generated method stub
	
}

@Override
public void onIOException(IOException e, Object state) {
	// TODO Auto-generated method stub
	
}

@Override
public void onFileNotFoundException(FileNotFoundException e, Object state) {
	// TODO Auto-generated method stub
	
}

@Override
public void onMalformedURLException(MalformedURLException e, Object state) {
	// TODO Auto-generated method stub
	
}

@Override
public void onFacebookError(FacebookError e, Object state) {
	// TODO Auto-generated method stub
	
}


}



/**
* Listener for logout status message
*/
@SuppressWarnings("unused")
private class LogoutRequestListener implements RequestListener {

/** Called when the request completes w/o error */
public void onComplete(String response) {

// Only the original owner thread can touch its views
TelaFacebook.this.runOnUiThread(new Runnable() {
    public void run() {
    }
});

// Dispatch on its own thread
mHandler.post(new Runnable() {
    public void run() {
    }
});
}
public void onFileNotFoundException(FileNotFoundException e) {
// Process Exception
}

public void onIOException(IOException e) {
// Process Exception
}

public void onMalformedURLException(MalformedURLException e) {
// Process Exception
}

@Override
public void onComplete(String response, Object state) {
	// TODO Auto-generated method stub
	
}

@Override
public void onIOException(IOException e, Object state) {
	// TODO Auto-generated method stub
	
}

@Override
public void onFileNotFoundException(FileNotFoundException e, Object state) {
	// TODO Auto-generated method stub
	
}

@Override
public void onMalformedURLException(MalformedURLException e, Object state) {
	// TODO Auto-generated method stub
	
}

@Override
public void onFacebookError(FacebookError e, Object state) {
	// TODO Auto-generated method stub
	
}

}

}
	private void loadAccessToken() {
		String access_token = prefs.getString(ACCESS_TOKEN, null);
		long expires=0;
		if(prefs!=null){
			expires = prefs.getLong(ACCESS_EXPIRES, 0);
			if (access_token != null) {
				facebook.setAccessToken(access_token);
			}
			if (expires != 0) {
				facebook.setAccessExpires(expires);
			}
		}
		else{
			System.out.println("Sem internet");
		}
		
		
	}
	

		@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tela_facebook, menu);
		return true;
	}
		
		String get_id, get_name, get_gender, get_email, get_birthday, get_locale, get_location;

		private Session.StatusCallback fbStatusCallback = new Session.StatusCallback() {
		    @SuppressWarnings("deprecation")
			public void call(Session session, SessionState state, Exception exception) {
		        if (state.isOpened()) {
		        	
		            Request.executeMeRequestAsync(session, new Request.GraphUserCallback() {
		                public void onCompleted(GraphUser user, Response response) {
		                    if (response != null) {
		                        // do something with <response> now
		                        try{
		                            get_id = user.getId();
		                            get_name = user.getName();
		                            get_gender = (String) user.getProperty("gender");
		                            get_email = (String) user.getProperty("email");
		                            get_birthday = user.getBirthday();
		                            get_locale = (String) user.getProperty("locale");
		                            get_location = user.getLocation().toString();   

		                        Log.d("Usuario", user.getId() + "; " +  
		                            user.getName() + "; " +
		                            (String) user.getProperty("gender") + "; " +        
		                            (String) user.getProperty("email") + "; " +
		                            user.getBirthday()+ "; " +
		                            (String) user.getProperty("locale") + "; " +
		                            user.getLocation());
		                        } catch(Exception e) {
		                             e.printStackTrace();
		                             Log.d("Exceção", "Exception e");
		                         }

		                    }
		                }

		            });
		        }
		    }

		};
		private String mUserId;
		private String mUserToken;
		private String mUserName;
		private String mUserEmail;
		public void getProfileInformation() {


		    try {

		        JSONObject profile = Util.parseJson(facebook.request("me"));
		        Log.e("Profile", "" + profile);

		        mUserId = profile.getString("id");
		        mUserToken = facebook.getAccessToken();
		        mUserName = profile.getString("name");
		        mUserEmail = profile.getString("email");

		        runOnUiThread(new Runnable() {

		            public void run() {

		                Log.e("FaceBook_Profile",""+mUserId+"\n"+mUserToken+"\n"+mUserName+"\n"+mUserEmail);

		                Toast.makeText(getApplicationContext(),
		                        "Name: " + mUserName + "\nEmail: " + mUserEmail,
		                        Toast.LENGTH_LONG).show();



		            }

		        });

		    } catch (FacebookError e) {

		        e.printStackTrace();
		    } catch (MalformedURLException e) {

		        e.printStackTrace();
		    } catch (JSONException e) {

		        e.printStackTrace();
		    } catch (IOException e) {

		        e.printStackTrace();
		    }

		}
		
		 private void saveAccessToken() {  
			    SharedPreferences.Editor editor = prefs.edit();  
			    editor.putString(  
			      ACCESS_TOKEN, facebook.getAccessToken());  
			    editor.putLong(  
			      ACCESS_EXPIRES, facebook.getAccessExpires());  
			    editor.commit();  
			  }  
}




