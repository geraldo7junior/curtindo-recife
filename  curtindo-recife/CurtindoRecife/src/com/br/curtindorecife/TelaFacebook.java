package com.br.curtindorecife;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.Facebook.DialogListener;

import android.os.Bundle;
import android.os.Handler;
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

public class TelaFacebook extends Activity {

	private Facebook facebook;
	private SharedPreferences prefs;
	private static final String APP_ID = "670005079718273";

	private static final String ACCESS_EXPIRES = "access_expires";
	private static final String ACCESS_TOKEN = "access_token";
	private Handler mHandler = new Handler();

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
		// Carrega a accessToken pra saber se o usuário
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
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	      super.onActivityResult(requestCode, resultCode, data);
	      Log.d("FB Sample App", "onActivityResult(): " + requestCode);
	      facebook.authorizeCallback(requestCode, resultCode, data);
	    }
	
	private final class LoginDialogListener implements
	com.facebook.android.Facebook.DialogListener {

	/**
	* Called when the dialog has completed successfully
	*/
	public void onComplete(Bundle values) {
	// Process onComplete
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

}




