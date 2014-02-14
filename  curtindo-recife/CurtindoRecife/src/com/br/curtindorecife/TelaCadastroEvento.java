package com.br.curtindorecife;

import bd.Banco;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import persistencia.LoginBS;
import dominio.*;
import android.os.Bundle;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tela_cadastro_evento);
		ArrayAdapter<CharSequence> ar = ArrayAdapter.createFromResource(this,R.array.Categorias,android.R.layout.simple_list_item_1);
		ar.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		spCadastroEvento =(Spinner) findViewById(R.id.spCadastroEvento);
		spCadastroEvento.setAdapter(ar);
		
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
										builder.setMessage("Parabéns "+nomeUsuario(Usuario.getId())+" , você criou um evento!")
										       .setTitle("Sucesso!").setPositiveButton("OK", dialogClick);
							
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
										builder.setMessage("Saldo insuficiente, você possui "+banco.getUsuario(Usuario.getId()).getMascates()+" mascates. Compre mais na nossa loja ou dê mais simboras! ")
										       .setTitle("Falha na criação do evento!").setPositiveButton("OK", dialogClick);
										AlertDialog dialog = builder.create();
										dialog.show();
									}
									}else{
										cadastrarEvento();
										AlertDialog.Builder builder = new AlertDialog.Builder(this);
										builder.setMessage("Parabéns "+nomeUsuario(Usuario.getId())+" , você criou um evento!")
										       .setTitle("Sucesso!").setPositiveButton("OK", dialogClick);
							
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
								builder.setMessage("Você precisa estar logado para criar um evento :(")
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
	//Métodos de validaçãok dos campos
	
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
			txtTelefone.setError("Telefone inválido.");
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
			txtEndereco.setError("Endereço inválido.");
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
			txtData.setError("Data inválida.");
			return false;
		
			
		}
	}
	
	public boolean validaHora(){
		

		if(txtHora.getText().toString().length()<5){		
			txtHora.setError("Digite o horário.");
			return false;
		}
		else{return true;}
		
		
	}
public boolean validaTodosCampos() throws ParseException{
		
		if(validaEndereco()&validaNome()&validaTelefone()&validaData()&validaHora()&validaPreco()){			
			
			return true;			
		}else{
			return false;
		}
		
	}
	
	


	
	
	
	
	
	
	
	
	
}
