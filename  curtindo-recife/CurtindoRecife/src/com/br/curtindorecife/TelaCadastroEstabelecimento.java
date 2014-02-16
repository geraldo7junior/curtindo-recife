package com.br.curtindorecife;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import com.br.curtindorecife.TelaCadastroEvento.WallPostDialogListener;
import com.br.curtindorecife.TelaCadastroEvento.WallPostDialogListener.WallPostRequestListener;
import com.facebook.Session;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;

import bd.Banco;
import dominio.Estabelecimento;
import dominio.Mask;
import dominio.Usuario;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class TelaCadastroEstabelecimento extends Activity implements OnClickListener {
	EditText txtNome;
	EditText txtEndereco;
	EditText txtHoraInicio;
	EditText txtHoraTermino;
	EditText txtNumero;
	EditText txtDescricao;
	EditText txtTelefone;
	
	Button btnCriar;
	Spinner spDataFuncionamento, spTipoEstabelecimento;
	
	android.content.DialogInterface.OnClickListener facebookClick;
	private static Facebook facebook;
	private static SharedPreferences prefs;
	private static final String APP_ID = "670005079718273";
	private AsyncFacebookRunner mAsyncRunner;
	private static final String ACCESS_EXPIRES = "access_expires";
	private static final String ACCESS_TOKEN = "access_token";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		facebook = new Facebook(APP_ID);
		setContentView(R.layout.activity_cadastrar_estabelecimento);
		ArrayAdapter<CharSequence> ar = ArrayAdapter.createFromResource(this,R.array.Categorias3,android.R.layout.simple_list_item_1);
		ar.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		spDataFuncionamento =(Spinner) findViewById(R.id.spDataFuncionamento);
		spDataFuncionamento.setAdapter(ar);
		Session.openActiveSessionFromCache(TelaCadastroEstabelecimento.this);
		ArrayAdapter<CharSequence> spTipo = ArrayAdapter.createFromResource(this,R.array.Categorias4,android.R.layout.simple_list_item_1);
		spTipo.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		spTipoEstabelecimento =(Spinner) findViewById(R.id.spTipoEstabelecimento);
		spTipoEstabelecimento.setAdapter(spTipo);
		txtNome=(EditText) findViewById(R.id.txtNome);
		txtTelefone=(EditText) findViewById(R.id.txtTelefone);
		txtTelefone.addTextChangedListener(Mask.insert("(##)####-####", txtTelefone));
		txtHoraInicio =(EditText) findViewById(R.id.txtHoraInicio);
		txtHoraInicio.addTextChangedListener(Mask.insert("##:##", txtHoraInicio));
		txtNumero =(EditText) findViewById(R.id.txtNumero);
		txtHoraTermino =(EditText) findViewById(R.id.txtHora);
		txtHoraTermino.addTextChangedListener(Mask.insert("##:##", txtHoraTermino));
		txtEndereco =(EditText) findViewById(R.id.txtEndereco);
		txtDescricao =(EditText) findViewById(R.id.txtDescricao);
		btnCriar = (Button) findViewById(R.id.btnCriar);
		btnCriar.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cadastrar_estabelecimento, menu);
		return true;
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		if(v.getId()== R.id.btnCriar){
			String nome = txtNome.getText().toString();
			String endereco = txtEndereco.getText().toString();
			String telefone = txtTelefone.getText().toString();
			String numero = txtNumero.getText().toString();
			String horaTermino = txtHoraTermino.getText().toString();			
			String descricao = txtDescricao.getText().toString();
			String dataFuncionamento= spDataFuncionamento.getSelectedItem().toString();
			String tipo = spTipoEstabelecimento.getSelectedItem().toString();
			Integer idOwner = Usuario.getId();
			String horaInicio = txtHoraInicio.getText().toString();
			Integer prioridade = 4;
			Banco banco = new Banco(this);
			Usuario usuario = banco.getUsuario(Usuario.getId());
			int custo = 150;
			
			facebookClick = new android.content.DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if(which==dialog.BUTTON_POSITIVE){
						//facebook.dialog(getActivity(), "stream.publish", new DialogPublishFacebook());
						//chamarFacebook();		
				         
				        
						facebook.dialog(TelaCadastroEstabelecimento.this, "stream.publish", new WallPostDialogListener());
						//updateStatusClick(v, txtNomeEvento.getText().toString());
					}
					if(which==dialog.BUTTON_NEGATIVE){
						Intent intent=new Intent(TelaCadastroEstabelecimento.this, TelaPrincipal.class);
						startActivity(intent);
				
					}
				}

				};
			android.content.DialogInterface.OnClickListener dialogClick = new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					Intent intent = new Intent(TelaCadastroEstabelecimento.this, TelaPrincipal.class);
					startActivity(intent);
				}
			};
			if(usuario.getMascates() >= custo){
			
			Estabelecimento estabelecimento = new Estabelecimento(idOwner, nome, endereco, numero, null, dataFuncionamento, horaInicio, horaTermino, telefone, descricao, tipo, 456, prioridade, telefone);
			
			banco.inserirEstabelecimento(estabelecimento);
			usuario.setMascates(usuario.getMascates()-custo);
			banco.inserirMeusEstabelecimentos(usuario);
			banco.updateUsuario(usuario);
			//banco.inserirMeusEstabelecimentos(estabelecimento, usuario);
			
			
			
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Estabelecimento criado com sucesso. Compartilhar?")
			       .setTitle("Criação de Estabelecimentos").setPositiveButton("Sim", facebookClick).setNegativeButton("Não", facebookClick);
			AlertDialog dialog = builder.create();
			dialog.show();
		}
			else{
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setMessage("Saldo insuficiente, você possui "+banco.getUsuario(Usuario.getId()).getMascates()+" mascates. Compre mais na nossa loja ou dê mais simboras! ")
				       .setTitle("Falha na criação do estabelecimento").setPositiveButton("OK", dialogClick);
				AlertDialog dialog = builder.create();
				dialog.show();
			}
	  }
		
	}
	
	
	
	public class WallPostDialogListener implements
	com.facebook.android.Facebook.DialogListener {

	/**
	* Called when the dialog has completed successfully
	*/
	@SuppressWarnings("deprecation")
	public void onComplete(Bundle values) {
	final String postId = values.getString("post_id");
	if (postId != null) {
	    Log.d("FB Sample App", "Dialog Success! post_id=" + postId);
	    //mAsyncRunner.request(postId, new WallPostRequestListener());
	} else {
	    Log.d("FB Sample App", "No wall post made");
	}

	Intent intent=new Intent(TelaCadastroEstabelecimento.this, TelaPrincipal.class);
	startActivity(intent);
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
		

}
