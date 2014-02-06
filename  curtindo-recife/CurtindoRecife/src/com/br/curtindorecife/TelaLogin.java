package com.br.curtindorecife;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;

import dominio.Evento;
import dominio.Usuario;
import bd.Banco;
import persistencia.*;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class TelaLogin extends Activity implements OnClickListener{
	/**
	 * A dummy authentication store containing known user names and passwords.
	 * TODO: remove after connecting to a real authentication system.
	 */
	private static final String[] DUMMY_CREDENTIALS = new String[] {
			"foo@example.com:hello", "bar@example.com:world" };

	/**
	 * The default email to populate the email field with.
	 */
	public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";

	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	private UserLoginTask mAuthTask = null;

	// Values for email and password at the time of the login attempt.
	private String mEmail;
	private String mPassword;

	// UI references.
	private EditText mEmailView;
	private EditText mPasswordView;
	private View mLoginFormView;
	private View mLoginStatusView;
	private TextView mLoginStatusMessageView;
	
	/*private static final String APP_ID = "670005079718273";

	private static final String ACCESS_EXPIRES = "access_expires";
	private static final String ACCESS_TOKEN = "access_token";

	private Facebook facebook;
	private SharedPreferences prefs;

	private Bitmap fotoTirada;
	private ImageView imageView1;
	private File caminhoFoto;

	private Bitmap image;

	String[] salvarimg = new String[0];

	String corpo;

	private int cont = 0;*/
	
	android.content.DialogInterface.OnClickListener dialogClick;
	
	Button btnEsqueciSenha;
	Button btnSemCadastro;
	Button btnEntrarFacebook;

	private android.content.DialogInterface.OnClickListener dialogNotificacao;

	private android.content.DialogInterface.OnClickListener dialogNotificacao1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_tela_login);
		
		/*facebook = new Facebook(APP_ID);
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
		btnEntrarFacebook = (Button) findViewById(R.id.entrarFacebook);*/
		
		

		// Set up the login form.
		mEmail = getIntent().getStringExtra(EXTRA_EMAIL);
		mEmailView = (EditText) findViewById(R.id.email);
		mEmailView.setText(mEmail);
		
		btnSemCadastro = (Button)findViewById(R.id.btnSemCadastro);
		btnSemCadastro.setOnClickListener(this);
		btnEsqueciSenha = (Button)findViewById(R.id.btnEsqueciSenha);
		btnEsqueciSenha.setOnClickListener(this);
		
		mPasswordView = (EditText) findViewById(R.id.password);
		mPasswordView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login || id == EditorInfo.IME_NULL) {
							try {
								attemptLogin();
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							return true;
						}
						return false;
					}
				});

		mLoginFormView = findViewById(R.id.login_form);
		mLoginStatusView = findViewById(R.id.login_status);
		mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

		findViewById(R.id.entrar).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						try {
							attemptLogin();
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				});
	}

	/*private void loadAccessToken() {
		String access_token = prefs.getString(ACCESS_TOKEN, null);
		long expires = prefs.getLong(ACCESS_EXPIRES, 0);
		if (access_token != null) {
			facebook.setAccessToken(access_token);
		}
		if (expires != 0) {
			facebook.setAccessExpires(expires);
		}
	}*/

	@Override
	public void onClick(View v) {
		if (v.getId()==R.id.btnEsqueciSenha){
			Intent intent = new Intent(TelaLogin.this, TelaEsqueciSenha.class);
			startActivity(intent);
		}if(v.getId()==R.id.btnSemCadastro){
			Intent intent = new Intent(TelaLogin.this, TelaCadastroUsuario.class);
			startActivity(intent);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.tela_login, menu);
		return true;
	}

	
	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
////////////////////////////////////////////////////////////////////////////////////////
	
	public Integer idUsuario(String email){
		String NomeBanco = "CurtindoRecifeDB";
		SQLiteDatabase BancoDados = null;
		Cursor cursor;
		try {
			BancoDados = openOrCreateDatabase(NomeBanco, MODE_WORLD_READABLE, null);
			String sql = "SELECT _id FROM tabelaUsuarios WHERE email LIKE '"+email+"' ";
			cursor = BancoDados.rawQuery(sql, null);
			cursor.moveToFirst();
			return cursor.getInt(cursor.getPosition());
			
		} catch (Exception erro) {
			System.out.println(erro);
			return null;
			// retorna 0 caso o email não seja encontrado ou algum erro no banco.
		}finally{
			BancoDados.close();
		}	
	}
	
	public String senhaUsuario(Integer id){
		String NomeBanco = "CurtindoRecifeDB";
		SQLiteDatabase BancoDados = null;
		Cursor cursor;
		try {
			BancoDados = openOrCreateDatabase(NomeBanco, MODE_WORLD_READABLE, null);
			String sql = "SELECT senha FROM tabelaUsuarios WHERE _id LIKE '"+id+"' ";
			cursor = BancoDados.rawQuery(sql, null);
			cursor.moveToFirst();
			return cursor.getString(cursor.getPosition());
			
		} catch (Exception erro) {
			System.out.println(erro);
			return null;
		} finally {
			BancoDados.close();
		}	
	}
	public String nomeUsuario(Integer id){
		String NomeBanco = "CurtindoRecifeDB";
		SQLiteDatabase BancoDados = null;
		Cursor cursor;
		try {
			BancoDados = openOrCreateDatabase(NomeBanco, MODE_WORLD_READABLE, null);
			String sql = "SELECT nome FROM tabelaUsuarios WHERE _id LIKE '"+id+"' ";
			cursor = BancoDados.rawQuery(sql, null);
			cursor.moveToFirst();
			return cursor.getString(cursor.getPosition());
			
		} catch (Exception erro) {
			System.out.println(erro);
			return null;
		} finally {
			BancoDados.close();
		}	
	}
	
	
///////////////////////////////////////////////////////////////////////////////////////////////////	
	public void attemptLogin() throws ParseException {
		if (mAuthTask != null) {
			return;
		}

		// Reset errors.
		mEmailView.setError(null);
		mPasswordView.setError(null);

		// Store values at the time of the login attempt.
		mEmail = mEmailView.getText().toString();
		mPassword = mPasswordView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		} else if (mPassword.length() < 4) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(mEmail)) {
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			cancel = true;
		} else if (!mEmail.contains("@")) {
			mEmailView.setError(getString(R.string.error_invalid_email));
			focusView = mEmailView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			
			if(idUsuario(mEmail)!=null){
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
				if(senhaUsuario(idUsuario(mEmail)).equals(mPassword)){
					mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
					showProgress(true);
					Usuario.setId(idUsuario(mEmail));
					/*mAuthTask = new UserLoginTask();
					mAuthTask.execute((Void) null);*/
					Banco bd =new Banco(this);
					Evento.getMeusEventos().clear();
					bd.setMeusEventos(Usuario.getId());
					ArrayList<Evento> listaEventosHoje = new ArrayList<Evento>();
					
					
					
				
					for(int i=0;i<Evento.getMeusEventos().size();i++){
						Date dataHoje = new Date();
						SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
						
						String dataEvento = Evento.getMeusEventos().get(i).getData();
						
						Date data = new Date(format.parse(dataEvento).getTime());
						dataHoje.setHours(data.getHours());
						dataHoje.setMinutes(data.getMinutes());
						dataHoje.setSeconds(data.getSeconds());
						/////////////
						if (data.getDay()==(dataHoje.getDay())){
							listaEventosHoje.add(Evento.getMeusEventos().get(i));
						}
								
					}
					
					
					int numeroEventos = listaEventosHoje.size();
					if(numeroEventos>0){
						dialogNotificacao1 = new android.content.DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
							if(which==dialog.BUTTON_NEGATIVE){
								Intent intent=new Intent(TelaLogin.this, TelaPrincipal.class);
								startActivity(intent);
							}else if(which==dialog.BUTTON_POSITIVE){
								Intent intent=new Intent(TelaLogin.this, TelaPrincipal.class);
							TelaPrincipal.setNumeroTela(2);
							startActivity(intent);
							}
							}
						};
					
						AlertDialog.Builder builder = new AlertDialog.Builder(this);
						if(numeroEventos<1){
							builder.setMessage("Você tem "+numeroEventos+" eventos para hoje.").setNegativeButton("Continuar", dialogNotificacao1).setPositiveButton("Conferir", dialogNotificacao1);
						}else{
							builder.setMessage("Você tem "+numeroEventos+" evento para hoje.").setNegativeButton("Continuar", dialogNotificacao1).setPositiveButton("Conferir", dialogNotificacao1);
						}
						AlertDialog dialog = builder.create();
						dialog.show();
						
					}else{
						Intent intent=new Intent(TelaLogin.this, TelaPrincipal.class);
						startActivity(intent);
					}
					
				}else{
					mPasswordView.setError("Senha inválida");
					focusView = mPasswordView;
				
					cancel = true;
					
				}
			}
			else{
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
	
				// 2. Chain together various setter methods to set the dialog characteristics
				builder.setMessage("Usuário não cadastrado").setPositiveButton("OK", dialogClick);
	
				// 3. Get the AlertDialog from create()
				AlertDialog dialog = builder.create();
				dialog.show();
				dialogClick=new android.content.DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				};
				
				}
			}
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLoginStatusView.setVisibility(View.VISIBLE);
			mLoginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mLoginFormView.setVisibility(View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
	public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO: attempt authentication against a network service.

			try {
				// Simulate network access.
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				return false;
			}

			for (String credential : DUMMY_CREDENTIALS) {
				String[] pieces = credential.split(":");
				if (pieces[0].equals(mEmail)) {
					// Account exists, return true if the password matches.
					return pieces[1].equals(mPassword);
				}
			}

			// TODO: register the new account here.
			return true;
		}

		@Override
		protected void onPostExecute(final Boolean success) {
			mAuthTask = null;
			showProgress(false);

			if (success) {
				finish();
			} else {
				mPasswordView
						.setError(getString(R.string.error_incorrect_password));
				mPasswordView.requestFocus();
			}
		}

		@Override
		protected void onCancelled() {
			mAuthTask = null;
			showProgress(false);
		}
	}
}
