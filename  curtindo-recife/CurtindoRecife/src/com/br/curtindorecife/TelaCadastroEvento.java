package com.br.curtindorecife;


import android.os.Bundle;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Spinner;

public class TelaCadastroEvento extends Activity implements OnClickListener {

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
		return true;
	}
	
	public void navegacao(){
		btnCriar = (Button)findViewById(R.id.btnCriar);
		btnCriar.setEnabled(false);
		btnCriar.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.btnCriar){
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
