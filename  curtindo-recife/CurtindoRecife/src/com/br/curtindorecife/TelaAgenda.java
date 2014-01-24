package com.br.curtindorecife;

import com.br.curtindorecife.R.id;

import dominio.Usuario;
import persistencia.Teste;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class TelaAgenda extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tela_agenda);
		Teste.setMia(0);
		System.out.println(Teste.getMia());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if(Usuario.getId()==0){
			MenuInflater menuInflater=getMenuInflater();
			menuInflater.inflate(R.menu.main, menu);
			
			
		}else{
			MenuInflater menuInflater=getMenuInflater();
			menuInflater.inflate(R.menu.main_logado, menu);
		}
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
	//Realizar um case pelo “Id” dos itens e logo em seguida mostrar uma mensagem ao usuário
	  switch (item.getItemId())
	  {     
	   case id.Cadastrar:
		   Intent intent = new Intent(this, TelaCadastroUsuario.class);
		   startActivity(intent);
	   break;
	   
	   case id.sairPontinhos:
		   Usuario.setId(0);
		   Intent intentSair = new Intent(this, TelaLogin.class);
		   startActivity(intentSair);
		   break;
	  }
	   //Retornar a classe pai
	   return super.onOptionsItemSelected(item);
	}

}
