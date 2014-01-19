package com.br.curtindorecife;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TelaPerfilUsuario extends Activity implements OnClickListener {
	TextView txtNomeUsuario;
	TextView txtEventoFavorito1;
	TextView txtEventoFavorito3;
	TextView txtEventoFavorito2;
	TextView txtEmail;
	EditText txtboxEditarEmail;
	EditText txtboxSenhaAntiga;
	EditText txtboxNovaSenha1;
	EditText txtboxNovaSenha2;
	Button btnEditar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tela_perfil_usuario);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tela_perfil_usuario, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
