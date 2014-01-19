package com.br.curtindorecife;


import persistencia.LoginBS;
import dominio.*;
import android.os.Bundle;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
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
		txtEndereco =(EditText) findViewById(R.id.txtEndereco);
		txtDescricao =(EditText) findViewById(R.id.txtDescricao);
		txtData=(EditText) findViewById(R.id.txtCadastroData);
		
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
	public void cadastrar(int idOwner, String nome, String endereco, String numero, String preco, String data, String hora, String telefone, String descricao, String tipo, int imagem){
		// Cadastra evento
		String NomeBanco = "CurtindoRecifeDB";
		SQLiteDatabase BancoDados = null;
		Cursor cursor;	
		try {
			BancoDados = openOrCreateDatabase(NomeBanco, MODE_WORLD_READABLE, null);
			String sql = "INSERT INTO tabelaEventos (nome, endereco, numero, preco, data, hora, telefone, descricao, tipo, idOwner, idImagem, simboras) VALUES ('"+nome+"','"+endereco+"','"+numero+"','"+preco+"','"+data+"','"+hora+"','"+telefone+"','"+descricao+"','"+tipo+"','"+idOwner+"','"+imagem+"','0')";
			BancoDados.execSQL(sql);
			String sqlPesquisaMeusEventos = "SELECT _id FROM tabelaEventos WHERE nome LIKE '"+nome+"'";
			cursor = BancoDados.rawQuery(sqlPesquisaMeusEventos, null);
			cursor.moveToFirst();
			Evento.setIdEvento(cursor.getInt(cursor.getPosition()));
			String sqlMeusEventos="INSERT INTO tabelaMeusEventos (idUsuario, idEvento) VALUES ('"+Usuario.getId()+"','"+Evento.getIdEvento()+"')";
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
		
		cadastrar(idOwner, nome, endereco, numero, preco, data, hora, telefone, descricao, tipo, Evento.associeImagem(tipo));
	}
	
	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.btnCriar){
			
			    /////////////////////_Aqui!/////////////////////////////////////////////
			
			if(Usuario.getId()!=0){
				cadastrarEvento();
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setMessage("Parabéns "+nomeUsuario(Usuario.getId())+" , você criou um evento!")
				       .setTitle("Sucesso!");
	
				// 3. Get the AlertDialog from create()
				AlertDialog dialog = builder.create();
				dialog.show();
				Intent intent = new Intent(TelaCadastroEvento.this, TelaPrincipal.class);
				startActivity(intent);
			}
			else{
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setMessage("Você precisa estar logado para criar um evento :(")
				       .setTitle("Que pena :(");
	
				// 3. Get the AlertDialog from create()
				AlertDialog dialog = builder.create();
				dialog.show();
				Intent intent = new Intent(TelaCadastroEvento.this, TelaLogin.class);
				startActivity(intent);
			}
		}
		
	}

}
