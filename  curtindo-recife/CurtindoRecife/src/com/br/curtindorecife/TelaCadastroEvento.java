package com.br.curtindorecife;

import bd.Banco;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.facebook.Session;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;

import persistencia.LoginBS;
import dominio.*;
import dominio.FragmentEventos.WallPostDialogListener;
import dominio.FragmentEventos.WallPostDialogListener.WallPostRequestListener;
import android.os.Bundle;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Spinner;

@SuppressLint("SimpleDateFormat")
public class TelaCadastroEvento extends Activity implements OnClickListener {
	
	EditText txtNome;
	EditText txtEndereco;
	EditText txtHora;
	EditText txtNumero;
	EditText txtData;
	EditText txtPreco;
	EditText txtDescricao;
	EditText txtTelefone;
	
	Button btnCriar;
	Spinner spCadastroEvento;
	DialogInterface dialog;
	android.content.DialogInterface.OnClickListener dialogClick;
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
		setContentView(R.layout.activity_tela_cadastro_evento);
		facebook = new Facebook(APP_ID);
		ArrayAdapter<CharSequence> ar = ArrayAdapter.createFromResource(this,R.array.Categorias,android.R.layout.simple_list_item_1);
		ar.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		spCadastroEvento =(Spinner) findViewById(R.id.spCadastroEvento);
		spCadastroEvento.setAdapter(ar);
		Session.openActiveSessionFromCache(TelaCadastroEvento.this);
		navegacao();
	}
		
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tela_cadastro_evento, menu);
		txtNome=(EditText) findViewById(R.id.txtNome);
		txtTelefone=(EditText) findViewById(R.id.txtTelefone);
		txtTelefone.addTextChangedListener(Mask.insert("(##)####-####", txtTelefone));
		txtPreco =(EditText) findViewById(R.id.txtPreco);
		txtNumero =(EditText) findViewById(R.id.txtNumero);
		txtHora =(EditText) findViewById(R.id.txtHora);
		txtHora.addTextChangedListener(Mask.insert("##:##", txtHora));
		txtEndereco =(EditText) findViewById(R.id.txtEndereco);
		txtDescricao =(EditText) findViewById(R.id.txtDescricao);
		txtData=(EditText) findViewById(R.id.txtCadastroData);
		txtData.addTextChangedListener(Mask.insert("##/##/####", txtData));
		
		
		return true;
	}
	
	public void navegacao(){
		btnCriar = (Button)findViewById(R.id.btnCriar);
		btnCriar.setOnClickListener(this);
		
	}
	String NomeBanco = "CurtindoRecifeDB";
	SQLiteDatabase BancoDados = null;
	
/////////////////////_cadastrar evento_//////////////////////////////	
	@SuppressWarnings("deprecation")
	public void cadastrar(int idOwner, String nome, String endereco, String numero, String preco, String data, String hora, String telefone, String descricao, String tipo, int imagem, int prioridade){
		// Cadastra evento
		String NomeBanco = "CurtindoRecifeDB";
		SQLiteDatabase BancoDados = null;
		Cursor cursor;	
		try {
			BancoDados = openOrCreateDatabase(NomeBanco, MODE_WORLD_READABLE, null);
			String sql = "INSERT INTO tabelaEventos (nome, endereco, numero, preco, data, hora, telefone, descricao, tipo, idOwner, idImagem, simboras, prioridade) VALUES ('"+nome+"','"+endereco+"','"+numero+"','"+preco+"','"+data+"','"+hora+"','"+telefone+"','"+descricao+"','"+tipo+"','"+idOwner+"','"+imagem+"','0', '"+prioridade+"')";
			BancoDados.execSQL(sql);
			String sqlPesquisaMeusEventos = "SELECT _id FROM tabelaEventos WHERE nome LIKE '"+nome+"'";
			cursor = BancoDados.rawQuery(sqlPesquisaMeusEventos, null);
			cursor.moveToFirst();
			Evento.setIdEvento(cursor.getInt(cursor.getPosition()));
			String sqlMeusEventos="INSERT INTO tabelaMeusEventos (idUsuario, idEvento, votou) VALUES ('"+Usuario.getId()+"','"+Evento.getIdEvento()+"','"+1+"')";
			BancoDados.execSQL(sqlMeusEventos);
		} catch (Exception erro) {
			System.out.println(erro);
		}finally{
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
	public void cadastrarEvento(){
		String nome = txtNome.getText().toString();
		String telefone = txtTelefone.getText().toString();
		String preco = txtPreco.getText().toString();
		String numero = txtNumero.getText().toString();
		String hora = txtHora.getText().toString();
		String endereco = txtEndereco.getText().toString();
		String descricao = txtDescricao.getText().toString();
		String data = txtData.getText().toString();
		String tipo = spCadastroEvento.getSelectedItem().toString();
		Integer idOwner = Usuario.getId();
		Integer prioridade = 4;
		cadastrar(idOwner, nome, endereco, numero, preco, data, hora, telefone, descricao, tipo, Evento.associeImagem(tipo), prioridade);
	}
	
	
	@Override
	public void onClick(View v) {
		
		facebookClick = new android.content.DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if(which==dialog.BUTTON_POSITIVE){
					//facebook.dialog(getActivity(), "stream.publish", new DialogPublishFacebook());
					//chamarFacebook();		
			         
			        
					facebook.dialog(TelaCadastroEvento.this, "stream.publish", new WallPostDialogListener());
					//updateStatusClick(v, txtNomeEvento.getText().toString());
				}
				if(which==dialog.BUTTON_NEGATIVE){
					Intent intent=new Intent(TelaCadastroEvento.this, TelaPrincipal.class);
					startActivity(intent);
			
				}
			}

			};
		if(v.getId() == R.id.btnCriar){
			
		
/////////////////////_Aqui!/////////////////////////////////////////////
			
			if(Usuario.getId()!=0){
						
				//
					try {if(Usuario.getId()!=0){
						if (validaTodosCampos()){		

								if (!txtNome.getText().toString().equals("")){
									dialogClick =new DialogInterface.OnClickListener() {
										
										@Override
										public void onClick(DialogInterface arg0, int arg1) {
												Intent intent = new Intent(TelaCadastroEvento.this, TelaPrincipal.class);
												startActivity(intent);
										}
									};
									
									Banco banco = new Banco(this);
									Usuario usuario = banco.getUsuario(Usuario.getId());
									if(usuario.getIdUnico()!=1){
									if(usuario.getMascates()>=50){
										cadastrarEvento();
										usuario.setMascates(usuario.getMascates()-50);
										banco.updateUsuario(usuario);
										
										AlertDialog.Builder builder = new AlertDialog.Builder(this);
										builder.setMessage("Parab�ns "+nomeUsuario(Usuario.getId())+" , voc� criou um evento! Compartilhar?")
										       .setTitle("Sucesso!").setPositiveButton("Sim", facebookClick).setNegativeButton("N�o", facebookClick);
							
										AlertDialog dialog = builder.create();
										dialog.show();
										
										
									}else{
										dialogClick =new DialogInterface.OnClickListener() {
											
											@Override
											public void onClick(DialogInterface arg0, int arg1) {
												Intent intent = new Intent(TelaCadastroEvento.this, TelaPrincipal.class);
												startActivity(intent);
											}
										};
										AlertDialog.Builder builder = new AlertDialog.Builder(this);
										builder.setMessage("Saldo insuficiente, voc� possui "+banco.getUsuario(Usuario.getId()).getMascates()+" mascates. Compre mais na nossa loja ou d� mais simboras! ")
										       .setTitle("Falha na cria��o do evento!").setPositiveButton("OK", dialogClick);
										AlertDialog dialog = builder.create();
										dialog.show();
									}
									}else{
										cadastrarEvento();
										AlertDialog.Builder builder = new AlertDialog.Builder(this);
										builder.setMessage("Parab�ns "+nomeUsuario(Usuario.getId())+" , voc� criou um evento! Compartilhar?")
										       .setTitle("Sucesso!").setPositiveButton("Sim", facebookClick).setNegativeButton("N�o", facebookClick);
							
										AlertDialog dialog = builder.create();
										dialog.show();
									}
									
									// 3. Get the AlertDialog from create()
									
									
								}
								else{txtNome.setError("Digite um nome.");}

							
								
							}
					}
						else{
								AlertDialog.Builder builder = new AlertDialog.Builder(this);
								builder.setMessage("Voc� precisa estar logado para criar um evento :(")
								       .setTitle("Que pena :(");

								// 3. Get the AlertDialog from create()
								AlertDialog dialog = builder.create();
								dialog.show();
								dialogClick =new DialogInterface.OnClickListener() {
									
									@Override
									public void onClick(DialogInterface arg0, int arg1) {
										Intent intent = new Intent(TelaCadastroEvento.this, TelaLogin.class);
										startActivity(intent);
									}
								};
								
							}
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				}
			}
		
	}
	//M�todos de valida��ok dos campos
	
	public boolean validaNome(){
		
		if (txtNome.getText().toString().equals("")){
			txtNome.setError("Digite o nome.");
			return false;
		}else{
			return true;
			}
		
			
		
	}
	
	public boolean validaTelefone(){	
		
		if (txtTelefone.getText().toString().equals("")){
			txtTelefone.setError("Digite um telefone.");
			return false;
		}
		else if (txtTelefone.getText().toString().length()<10){
			txtTelefone.setError("Telefone inv�lido.");
			return false;
		}else{
		
		return true;	
		}
	}
	
	public boolean validaEndereco(){
		
		if (txtEndereco.getText().toString().equals("")){
			txtEndereco.setError("Digite o endereco.");
			return false;
		}
		else if (txtEndereco.getText().toString().length()<=4){
			txtEndereco.setError("Endere�o inv�lido.");
			return false;
		}else{
		
		return true;	
		}
	}
	
	
	
	public boolean validaPreco(){	
		
		if(txtPreco.getText().toString().equals("")){		
			
			txtPreco.setError("Digite o valor.");
			return false;
		}
		else{return true;}
	}
	
	public boolean validaData() throws ParseException{
		
		Date dataHoje = new Date();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");  
		Date data = new Date(format.parse(txtData.getText().toString()).getTime());
		dataHoje.setHours(data.getHours());
		dataHoje.setMinutes(data.getMinutes());
		dataHoje.setSeconds(data.getSeconds());
		System.out.println(data+" data");
		System.out.println(dataHoje+" datahoje");
		if (data.after(dataHoje)){		
			return true;
		}
		else if (data.getDay()==(dataHoje.getDay())){
			return true;
		}else{
			txtData.setError("Data inv�lida.");
			return false;
		
			
		}
	}
	
	public boolean validaHora(){
		
	    DateFormat formato = new SimpleDateFormat("HH:mm");  
	    String horaConvertida = txtHora.getText().toString(); 
	    int horaConvertida2;
	    int minuto;
	    horaConvertida2 = Integer.parseInt(horaConvertida.substring(0,2));
	    minuto = Integer.parseInt(horaConvertida.substring(3,5));

		if(txtHora.getText().toString().length()<5){		
			txtHora.setError("Digite o hor�rio.");
			return false;
		}
		else{
			if((horaConvertida2>=0 && horaConvertida2<=23) && (minuto>0 && minuto<=59)){
				return true;
			}else{
				txtHora.setError("Digite o hor�rio.");
				return false;
			}
			
		}
		
		
		
	}
public boolean validaTodosCampos() throws ParseException{
		
		if(validaEndereco()&validaNome()&validaTelefone()&validaData()&validaHora()&validaPreco()){			
			
			return true;			
		}else{
			return false;
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
    mAsyncRunner.request(postId, new WallPostRequestListener());
} else {
    Log.d("FB Sample App", "No wall post made");
}

Intent intent=new Intent(TelaCadastroEvento.this, TelaPrincipal.class);
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
