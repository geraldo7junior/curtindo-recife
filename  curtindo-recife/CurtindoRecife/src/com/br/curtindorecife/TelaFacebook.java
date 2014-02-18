package com.br.curtindorecife;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import bd.Banco;

import com.facebook.Request;
import com.facebook.RequestBatch;
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
import com.facebook.model.GraphObject;
import com.facebook.model.GraphUser;

import dominio.FragmentEventos;
import dominio.Usuario;

import android.os.Bundle;
import android.os.Handler;
import android.provider.OpenableColumns;
import android.app.Activity;
import android.content.Context;
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

@SuppressWarnings("deprecation")
public class TelaFacebook extends Activity {

	private Facebook facebook;
	private SharedPreferences prefs;
	private static final String APP_ID = "670005079718273";

	private static final String ACCESS_EXPIRES = "access_expires";
	private static final String ACCESS_TOKEN = "access_token";
	private Handler mHandler = new Handler();
	private static Bundle saveInstance;
	
	public static Session sessaoIniciada; 
	
	public static Bundle getSaveInstance() {
		return saveInstance;
	}
	public static void setSaveInstance(Bundle saveInstance) {
		TelaFacebook.saveInstance = saveInstance;
	}

	/*public static void openSession(Activity activity){
		Session session=new Session(activity);
        Session.setActiveSession(session);
        sessaoIniciada=session;
        FacebookSessionStatusCallback statusCallback = new FacebookSessionStatusCallback();
        session.openForRead(new Session.OpenRequest(activity).setCallback(statusCallback));
	}
	*/
	private static final String[] PERMISSIONS = new String[] {"publish_stream", 
        "read_stream", "offline_access"};

	
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
		chamarFacebook();
	}
	
	public class FacebookSessionStatusCallback implements Session.StatusCallback {
	    @Override
	    public void call(Session session, SessionState state, Exception exception) {
	            String s=session.getAccessToken();
	    }
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    Log.d("FB Sample App", "onActivityResult(): " + requestCode);
    System.out.println("Pegar usuario do Onactivity"); 
    facebook.authorizeCallback(requestCode, resultCode, data);
    Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
    pegarUsuario();
   } 
		
	public void chamarFacebook(){
		// Carrega a accessToken pra saber se o usuário
		// já se autenticou.
		loadAccessToken();
		
		if(!facebook.isSessionValid()){
			
			facebook.authorize(this, PERMISSIONS, new LoginDialogListener(){

				@Override
				public void onComplete(Bundle values) {
					// TODO Auto-generated method stub
					saveAccessToken();
					//getProfileInformation();
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
			
			
		}
	}
	
	
	private class LoginDialogListener implements
	com.facebook.android.Facebook.DialogListener {

	/**
	* Called when the dialog has completed successfully
	*/
	public void onComplete(Bundle values) {
	// Process onComplete
		try {
			System.out.println("Pegar Usuario do on completed");
			pegarUsuario();
            //getProfileInformation();

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

}

/**
* Listener for logout status message
*/
public class LogoutRequestListener implements RequestListener {

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


	private void loadAccessToken() {
		String access_token = prefs.getString(ACCESS_TOKEN, null);
		long expires=0;
		if(prefs!=null){
			expires = prefs.getLong(ACCESS_EXPIRES, 0);
			if (access_token != null) {
				facebook.setAccessToken(access_token);
				 Session session = new Session(getApplicationContext());
		         Session.setActiveSession(session);
		         FacebookSessionStatusCallback statusCallback = new FacebookSessionStatusCallback();
		         session.openForRead(new Session.OpenRequest(TelaFacebook.this).setCallback(statusCallback));
				pegarUsuario();
				
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
		
		
		 private void saveAccessToken() {  
			 System.out.println("Entrou no saveAcessToken");
			    SharedPreferences.Editor editor = prefs.edit();  
			    editor.putString(  
			      ACCESS_TOKEN, facebook.getAccessToken());  
			    editor.putLong(  
			      ACCESS_EXPIRES, facebook.getAccessExpires());  
			    FragmentEventos.saveAccessToken();
			    editor.commit();  
			    
			  }
		 
		 static String nome;
		 static String email;
		 static String aniversario;
		 
		 private void pegarUsuario() {

		     String usuario="me";
		     System.out.println("Entrou no pegar Usuario");
		     RequestBatch requestBatch = new RequestBatch();
		    
		     System.out.println(Session.getActiveSession());
		         requestBatch.add(new Request(Session.getActiveSession(),usuario, null, null, new Request.Callback() {
		             public void onCompleted(Response response) {
		                 GraphObject graphObject = response.getGraphObject();
		                 System.out.println("Entrou no oncompleted do PegarUsuario");
		                 if (graphObject != null) {
		                	 System.out.println("Entrou no if do graphObject");
		                     if (graphObject.getProperty("id") != null) {
		                    	 Banco banco=new Banco(TelaFacebook.this);
		                    	     TelaFacebook.nome=(String)graphObject.getProperty("name");
		                             TelaFacebook.email=(String)graphObject.getProperty("email");
		                             TelaFacebook.aniversario=(String) graphObject.getProperty("birthday");
		                             System.out.println("Nome Usuário : "+ nome+" Email: "+ email+ " Aniversário: "+ aniversario);
		                             if(email==null){
		                            	 email=((String) graphObject.getProperty("first_name"))+"@";
		                             }
		                             if(aniversario==null){
		                            	 aniversario="01/01/2014";
		                             }
		                             if(banco.idUsuario(email)==null){
		                            	 banco.cadastrarUsuario(nome, aniversario, email, "12345", "Homem", "Show", "Esporte", "Teatro");
		                             }
		                            	 Usuario.setId(banco.idUsuario(email));
		                            	 System.out.println("Logado");
		                            	
		                            	 Toast.makeText(TelaFacebook.this, "Usuário Logado", Toast.LENGTH_SHORT).show();
		                            	 Intent intent=new Intent(TelaFacebook.this, TelaPrincipal.class);
		                            	 startActivity(intent);
		                             
		                                
		                     }
		                 }
		             }
		         
		     }));
		     requestBatch.executeAsync();
		 }
}




