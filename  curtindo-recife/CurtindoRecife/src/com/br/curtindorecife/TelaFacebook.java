package com.br.curtindorecife;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.Facebook.DialogListener;

import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.view.Menu;

public class TelaFacebook extends Activity {

	private Facebook facebook;
	private SharedPreferences prefs;
	private static final String APP_ID = "670005079718273";

	private static final String ACCESS_EXPIRES = "access_expires";
	private static final String ACCESS_TOKEN = "access_token";


	private Bitmap image;

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
		// Carrega a accessToken pra saber se o usuário
		// já se autenticou.
		loadAccessToken();
		
		if(!facebook.isSessionValid()){
			
			facebook.authorize(this, new String[] {"publish_stream"}, new DialogListener(){

				@Override
				public void onComplete(Bundle values) {
					// TODO Auto-generated method stub
					
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
			this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			
		}

	}
	
	
	
	private void loadAccessToken() {
		String access_token = prefs.getString(ACCESS_TOKEN, null);
		long expires = prefs.getLong(ACCESS_EXPIRES, 0);
		if (access_token != null) {
			facebook.setAccessToken(access_token);
		}
		if (expires != 0) {
			facebook.setAccessExpires(expires);
		}
	}

		@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tela_facebook, menu);
		return true;
	}

}
