package com.br.curtindorecife;

import persistencia.Teste;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

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
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tela_agenda, menu);
		return true;
	}

}
