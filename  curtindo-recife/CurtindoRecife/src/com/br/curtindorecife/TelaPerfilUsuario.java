package com.br.curtindorecife;

import dominio.Usuario;
import bd.Banco;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class TelaPerfilUsuario extends Activity implements OnClickListener {
	TextView txtNomeUsuario;
	TextView txtEmail;
	EditText txtboxEditarEmail;
	EditText txtboxSenhaAntiga;
	EditText txtboxNovaSenha1;
	EditText txtboxNovaSenha2;
	Button btnEditar;
	Button btnSair;
	Spinner spCategoriaPerfil1,spCategoriaPerfil2, spCategoriaPerfil3;
	public Usuario usuario;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Banco banco=new Banco(this);
		usuario=banco.getUsuario(Usuario.getId());
		setContentView(R.layout.activity_tela_perfil_usuario);
		spCategoriaPerfil1 = (Spinner) findViewById(R.id.spCategoriaPerfil1);
		ArrayAdapter<CharSequence> ar = ArrayAdapter.createFromResource(this,R.array.Categorias,android.R.layout.simple_list_item_1);
		ar.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		spCategoriaPerfil1.setAdapter(ar);
		spCategoriaPerfil2 = (Spinner)findViewById(R.id.spCategoriaPerfil2);
		spCategoriaPerfil2.setAdapter(ar);
		spCategoriaPerfil3 = (Spinner) findViewById(R.id.spCategoriaPerfil3);
		spCategoriaPerfil3.setAdapter(ar);
		txtNomeUsuario= (TextView) findViewById(R.id.txtNomeUsuario);
		txtEmail= (TextView) findViewById(R.id.txtEmail);
		
		
		txtNomeUsuario.setText(usuario.getNome());
		txtEmail.setText(usuario.getEmail());
		spCategoriaPerfil1.setSelection(ar.getPosition(usuario.getEventoFavorito1()));
		spCategoriaPerfil2.setSelection(ar.getPosition(usuario.getEventoFavorito2()));
		spCategoriaPerfil3.setSelection(ar.getPosition(usuario.getEventoFavorito3()));
		
		txtboxEditarEmail= (EditText) findViewById(R.id.txtboxEditarEmail);
		txtboxSenhaAntiga= (EditText) findViewById(R.id.txtboxSenhaAntiga);
		txtboxNovaSenha1= (EditText) findViewById(R.id.txtboxNovaSenha1);
		txtboxNovaSenha2= (EditText) findViewById(R.id.txtboxNovasenha2);
		btnEditar= (Button) findViewById(R.id.btnEditar);
		btnEditar.setOnClickListener(this);
		btnSair= (Button) findViewById(R.id.btnSair);
		btnSair.setOnClickListener(this);
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tela_perfil_usuario, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		View focusView=null;
		if(v.getId()==R.id.btnSair){
			Usuario.setId(0);
			Intent intent=new Intent(this, TelaPrincipal.class);
			startActivity(intent);
		}
		if(v.getId()==R.id.btnEditar){
			if(txtboxEditarEmail.getText().toString().contains("@")){
				Banco banco=new Banco(this);
				int retorno=banco.editarUsuário(txtboxEditarEmail.getText().toString(),txtboxNovaSenha1.getText().toString(), txtboxNovaSenha2.getText().toString(), txtboxSenhaAntiga.getText().toString(), spCategoriaPerfil1.getSelectedItem().toString(), spCategoriaPerfil2.getSelectedItem().toString(), spCategoriaPerfil3.getSelectedItem().toString());
				if(retorno==0){
					AlertDialog.Builder builder = new AlertDialog.Builder(this);
	
					builder.setMessage("Todos os campos foram alterados com sucesso").setTitle("Campos alterados");
	
					// 3. Get the AlertDialog from create()
					AlertDialog dialog = builder.create();
					dialog.show();
					
					Intent intent=new Intent(this, TelaPrincipal.class);
					startActivity(intent);
				}
				if(retorno==1){
					txtboxEditarEmail.setError("Email já cadastrado");
					focusView=txtboxEditarEmail;
					focusView.requestFocus();
				}
				if(retorno==2){
					txtboxSenhaAntiga.setError("Senha incorreta");
					focusView=txtboxSenhaAntiga;
					focusView.requestFocus();
				}
				if(retorno==3){
					txtboxNovaSenha1.setError("As senhas novas são diferentes");
					focusView=txtboxNovaSenha1;
					focusView.requestFocus();
				}
			}
			else{
				txtboxEditarEmail.setError("Email inválido");
				focusView=txtboxEditarEmail;
				focusView.requestFocus();
			}
		}
		
		
	}

}
