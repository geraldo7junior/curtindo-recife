package com.br.curtindorecife;

import com.br.curtindorecife.R.id;

import bd.Banco;
import dominio.Evento;
import dominio.Usuario;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class TelaMostrarPerfil extends Activity implements OnClickListener {
	
	TextView txtEmailPerfil, txtNomePerfil, txtDataPerfil, txtMascates,
	txtEventoDisponivel, txtSexoPerfil;
	Button btnEditar;
	ImageView imgEvento1,imgEvento2,imgEvento3;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tela_mostrar_perfil);
		txtEmailPerfil =(TextView) findViewById(R.id.txtEmailPerfil);
		txtNomePerfil = (TextView) findViewById(R.id.txtNomePerfil);
		txtDataPerfil = (TextView) findViewById(R.id.txtDataPerfil);
		txtMascates = (TextView) findViewById(R.id.txtMascate);
		txtEventoDisponivel = (TextView) findViewById(R.id.txtEventoDisponivel);
		txtSexoPerfil = (TextView) findViewById(R.id.txtSexoPerfil);
		imgEvento1 = (ImageView) findViewById(R.id.imgEvento1);
		imgEvento2 = (ImageView) findViewById(R.id.imgEvento2);
		imgEvento3 = (ImageView) findViewById(R.id.imgEvento3);
		
		Banco banco=new Banco(this);
		Usuario usuario;
		usuario=banco.getUsuario(Usuario.getId());
		
		int eventDisponiveis=usuario.getMascates()/50;
		
		if(usuario.getNome().toUpperCase().equals("DEMIS")){
			txtNomePerfil.setText("Denis");
		}else{
			txtNomePerfil.setText(usuario.getNome());
		}
		txtEmailPerfil.setText(usuario.getEmail());
		txtDataPerfil.setText(usuario.getDataDeNascimento());
		txtMascates.setText(Integer.toString(usuario.getMascates()));
		txtEventoDisponivel.setText(Integer.toString(eventDisponiveis));
		txtSexoPerfil.setText(usuario.getSexo());
		imgEvento1.setImageDrawable(getResources().getDrawable(Evento.associeImagemPerfil(usuario.getEventoFavorito1())));
		imgEvento2.setImageDrawable(getResources().getDrawable(Evento.associeImagemPerfil(usuario.getEventoFavorito2())));
		imgEvento3.setImageDrawable(getResources().getDrawable(Evento.associeImagemPerfil(usuario.getEventoFavorito3())));
		
		
		
	}
	


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		 MenuInflater menuInflater = getMenuInflater();
		 menuInflater.inflate(R.menu.tela_mostrar_perfil, menu); 
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
		if (v.getId()== R.id.btnEditarPerfil){
			Intent intent = new Intent(TelaMostrarPerfil.this, TelaPerfilUsuario.class);
			startActivity(intent);
		}
		
	}

}
