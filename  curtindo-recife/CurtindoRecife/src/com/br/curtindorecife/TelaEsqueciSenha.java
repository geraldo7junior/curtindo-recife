package com.br.curtindorecife;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

public class TelaEsqueciSenha extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tela_esqueci_senha);
		Intent intent=getIntent();
		String extra=intent.getStringExtra("extra");
		TextView textData=(TextView) findViewById(R.id.txtDataConfirmada);
		textData.setText(extra);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tela_esqueci_senha, menu);
		return true;
	}

}
 