package com.br.curtindorecife;

import android.os.Bundle;
import android.app.Activity;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class TelaPrincipal extends Activity implements android.view.View.OnClickListener {

	Button btnLogin;
	ImageButton btnAgenda;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		navegacao();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}
	
	public void navegacao(){
		btnLogin = (Button)findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(this);
		btnAgenda = (ImageButton)findViewById(R.id.btnAgenda);
		btnAgenda.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.btnLogin){
			Intent intent = new Intent(TelaPrincipal.this, TelaLogin.class);
			startActivity(intent);
		}
		
		if(v.getId() == R.id.btnAgenda){
			Intent intent = new Intent(TelaPrincipal.this, TelaAgenda.class);
			startActivity(intent);
		}
		
	}

}
