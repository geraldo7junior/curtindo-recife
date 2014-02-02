package com.br.curtindorecife;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class TelaCadastroEstabelecimento extends Activity {

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
		spDataFuncionamento =(Spinner) findViewById(R.id.spTipoEstabelecimento);
		spDataFuncionamento.setAdapter(spTipo);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cadastrar_estabelecimento, menu);
		return true;
	}

}
