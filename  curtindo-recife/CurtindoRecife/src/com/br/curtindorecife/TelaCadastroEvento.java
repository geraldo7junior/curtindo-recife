package com.br.curtindorecife;


import android.os.Bundle;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
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
		txtPreco =(EditText) findViewById(R.id.txtPreco);
		txtNumero =(EditText) findViewById(R.id.txtNumero);
		txtHora =(EditText) findViewById(R.id.txtHora);
		txtEndereco =(EditText) findViewById(R.id.txtEndereco);
		txtDescricao =(EditText) findViewById(R.id.txtDescricao);
		txtData=(EditText) findViewById(R.id.txtData);
		
		return true;
	}
	
	public void navegacao(){
		btnCriar = (Button)findViewById(R.id.btnCriar);
		btnCriar.setEnabled(false);
		btnCriar.setOnClickListener(this);
		
	}
	String NomeBanco = "CurtindoRecifeDB";
	SQLiteDatabase BancoDados = null;
	
/////////////////////_cadastrar evento_//////////////////////////////	
	@SuppressWarnings("deprecation")
	public void cadastrar(int idOwner, String nome, String endereco, String numero, String preco, String data, String hora, String telefone, String descricao, String tipo){
		// Cadastra evento
		
		try {
			BancoDados = openOrCreateDatabase(NomeBanco, MODE_WORLD_READABLE, null);
			String sql = "INSERT INTO tabelaEventos (nome, endereco, numero, preco, data, hora, telefone, descricao, tipo, idOwner) VALUES ('"+nome+"','"+endereco+"','"+numero+"','"+preco+"','"+data+"','"+hora+"','"+telefone+"','"+descricao+"',"+tipo+"','"+idOwner+"')";
			BancoDados.execSQL(sql);
		} catch (Exception erro) {
			System.out.println(erro);
		}finally{
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
		Integer idOwner = null;
		
		cadastrar(idOwner, nome, endereco, numero, preco, data, hora, telefone, descricao, tipo);
	}
	
	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.btnCriar){
			
			cadastrarEvento();    /////////////////////_Aqui!/////////////////////////////////////////////
			
			
			// 1. Instantiate an AlertDialog.Builder with its constructor
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			
			// 2. Chain together various setter methods to set the dialog characteristics
			builder.setMessage("É preciso realizar o login para curtir eventos.")
			       .setTitle("Login");

			// 3. Get the AlertDialog from create()
			AlertDialog dialog = builder.create();
			dialog.show();
			Intent intent = new Intent(TelaCadastroEvento.this, TelaPrincipal.class);
			startActivity(intent);
			
			
		}
		
	}

}
