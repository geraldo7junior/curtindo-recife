package com.br.curtindorecife;

import bd.Banco;
import dominio.Estabelecimento;
import dominio.Mask;
import dominio.Usuario;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cadastrar_estabelecimento);
		ArrayAdapter<CharSequence> ar = ArrayAdapter.createFromResource(this,R.array.Categorias3,android.R.layout.simple_list_item_1);
		ar.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		spDataFuncionamento =(Spinner) findViewById(R.id.spDataFuncionamento);
		spDataFuncionamento.setAdapter(ar);
		
		ArrayAdapter<CharSequence> spTipo = ArrayAdapter.createFromResource(this,R.array.Categorias4,android.R.layout.simple_list_item_1);
		spTipo.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		spTipoEstabelecimento =(Spinner) findViewById(R.id.spTipoEstabelecimento);
		spTipoEstabelecimento.setAdapter(spTipo);
		txtNome=(EditText) findViewById(R.id.txtNome);
		txtTelefone=(EditText) findViewById(R.id.txtTelefone);
		txtTelefone.addTextChangedListener(Mask.insert("(##)####-####", txtTelefone));
		txtHoraInicio =(EditText) findViewById(R.id.txtHoraInicio);
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
			banco.updateUsuario(usuario);
			
			
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Estabelecimento criado com sucesso.")
			       .setTitle("Criação de Estabelecimentos").setPositiveButton("OK", dialogClick);
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

}
