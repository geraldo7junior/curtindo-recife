package com.br.curtindorecife;


import android.os.Bundle;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TelaCadastroEvento extends Activity implements OnClickListener {

	Button btnCriar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tela_cadastro_evento);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tela_cadastro_evento, menu);
		return true;
	}
	
	public void navegacao(){
		btnCriar = (Button)findViewById(R.id.btnCriar);
		btnCriar.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.btnCriar){
			Intent intent = new Intent(TelaCadastroEvento.this, TelaPrincipal.class);
			startActivity(intent);
			
			
		}
		
	}

}
