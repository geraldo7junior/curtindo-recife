package com.br.curtindorecife;

import com.br.curtindorecife.R.id;

import bd.Banco;
import dominio.Usuario;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class TelaMostrarPerfil extends Activity implements OnClickListener {
	
	TextView txtEmailPerfil, txtNomePerfil, txtEvento1Perfil, txtEvento2Perfil, txtEvento3Perfil, txtDataPerfil, txtMascates,
	txtEventoDisponivel, txtSexoPerfil;
	Button btnSair, btnEditar;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tela_mostrar_perfil);
		txtEmailPerfil =(TextView) findViewById(R.id.txtEmailPerfil);
		txtNomePerfil = (TextView) findViewById(R.id.txtNomePerfil);
		txtEvento1Perfil = (TextView) findViewById(R.id.txtEvento1Perfil);
		txtEvento2Perfil = (TextView) findViewById(R.id.txtEvento2Perfil);
		txtEvento3Perfil = (TextView) findViewById(R.id.txtEvento3Perfil);
		txtDataPerfil = (TextView) findViewById(R.id.txtDataPerfil);
		txtMascates = (TextView) findViewById(R.id.txtMascate);
		txtEventoDisponivel = (TextView) findViewById(R.id.txtEventoDisponivel);
		txtSexoPerfil = (TextView) findViewById(R.id.txtSexoPerfil);
		
		Banco banco=new Banco(this);
		Usuario usuario;
		usuario=banco.getUsuario(Usuario.getId());
		
		txtNomePerfil.setText(usuario.getNome());
		txtEmailPerfil.setText(usuario.getEmail());
		txtEvento1Perfil.setText(usuario.getEventoFavorito1());
		txtEvento2Perfil.setText(usuario.getEventoFavorito2());
		txtEvento3Perfil.setText(usuario.getEventoFavorito3());
		txtDataPerfil.setText(usuario.getDataDeNascimento());
		txtMascates.setText("20");
		txtEventoDisponivel.setText("2");
		txtSexoPerfil.setText(usuario.getSexo());
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		 MenuInflater menuInflater = getMenuInflater();
		 menuInflater.inflate(R.menu.tela_mostrar_perfil, menu); 
		btnSair =(Button)findViewById(R.id.btnSairPerfil);
		btnSair.setOnClickListener(this);
		btnEditar = (Button)findViewById(R.id.btnEditarPerfil);
		btnEditar.setOnClickListener(this);
		return super.onCreateOptionsMenu(menu); 
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
	//Realizar um case pelo “Id” dos itens e logo em seguida mostrar uma mensagem ao usuário
	  switch (item.getItemId())
	  {     
	   case id.sairPontinhos:
		   Usuario.setId(0);
		   Intent intent = new Intent(TelaMostrarPerfil.this, TelaPrincipal.class);
		   startActivity(intent);;
	   break;
	  }
	   //Retornar a classe pai
	   return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.btnSairPerfil) {
			Usuario.setId(0);
			Intent intent = new Intent(TelaMostrarPerfil.this, TelaPrincipal.class);
			startActivity(intent);
		}if (v.getId()== R.id.btnEditarPerfil){
			Intent intent = new Intent(TelaMostrarPerfil.this, TelaPerfilUsuario.class);
			startActivity(intent);
		}
		
	}

}
