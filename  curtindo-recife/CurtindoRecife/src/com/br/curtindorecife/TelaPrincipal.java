package com.br.curtindorecife;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class TelaPrincipal extends Activity implements android.view.View.OnClickListener {

	Button btnLogin;
	Button btnCadastarEvento;
	ImageButton btnAgenda;
	ImageButton btnCinema;
	ImageButton btnTeatro;
	ImageButton btnEsportes;
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
		btnCadastarEvento = (Button)findViewById(R.id.btnCadastrarEvento);
		btnCadastarEvento.setOnClickListener(this);
		btnLogin = (Button)findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(this);
		btnAgenda = (ImageButton)findViewById(R.id.btnAgenda);
		btnAgenda.setOnClickListener(this);
		btnCinema = (ImageButton)findViewById(R.id.btnCinema);
		btnCinema.setOnClickListener(this);
		btnTeatro = (ImageButton)findViewById(R.id.btnTeatro);
		btnTeatro.setOnClickListener(this);
		btnEsportes = (ImageButton)findViewById(R.id.btnEsportes);
		btnEsportes.setOnClickListener(this);
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
		
		if(v.getId() == R.id.btnCinema){
			Intent intent = new Intent(TelaPrincipal.this, TelaEventos.class);
			startActivity(intent);
		}
		
		if(v.getId() == R.id.btnCadastrarEvento){
			Intent intent = new Intent(TelaPrincipal.this,TelaCadastroEvento.class);
			startActivity(intent);
		}
		
		if(v.getId() == R.id.btnEsportes){
			Intent intent = new Intent(TelaPrincipal.this,MainActivity.class);
			startActivity(intent);
		}
		if(v.getId() == R.id.btnTeatro){
				CharSequence[] charSequences = new CharSequence[]{"Sempre Compartilhar"};
		        final boolean[] checados = new boolean[charSequences.length];

		        AlertDialog.Builder builder = new AlertDialog.Builder(this);
		        builder.setTitle("Compartilhar no Facebook?");
		        builder.setMultiChoiceItems(charSequences, checados, new DialogInterface.OnMultiChoiceClickListener() {
		            public void onClick(DialogInterface arg0, int arg1, boolean arg2) {
		                checados[arg1] = arg2;
		            }
		        });

		        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
		            public void onClick(DialogInterface arg0, int arg1) {
		                StringBuilder texto = new StringBuilder("Checados: ");
		                for (boolean ch : checados) {
		                    texto.append(ch).append("; ");
		                }
		            }
		        });
		        
		        builder.setNegativeButton("Não", new DialogInterface.OnClickListener(){
		        	public void onClick(DialogInterface arg0, int arg1) {
		                StringBuilder texto = new StringBuilder("Checados: ");
		                for (boolean ch : checados) {
		                    texto.append(ch).append("; ");
		                }
		            }
		        });

		        AlertDialog alerta = builder.create();
		        alerta.show();
		        
			// TODO Auto-generated method stub
			
			Intent intent = new Intent(TelaPrincipal.this,Teste2.class);
			startActivity(intent);
		}	
		
	}

}
