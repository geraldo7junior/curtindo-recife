package com.br.curtindorecife;

import dominio.Evento;
import dominio.Usuario;
import bd.Banco;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

public class TelaPerfilUsuario extends Activity implements OnClickListener {

	EditText txtboxEditarEmail, txtData;
	EditText txtboxSenhaAntiga;
	EditText txtboxNovaSenha1;
	EditText txtboxNovaSenha2;
	Button btnEditar;
	Button btnExcluir;
	Spinner spCategoriaPerfil1,spCategoriaPerfil2, spCategoriaPerfil3;
	RadioButton rdbHomem, rdbMulher;
	public Usuario usuario;
	DialogInterface.OnClickListener dialogClick;
	DialogInterface.OnClickListener passarClick;
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
		rdbHomem = (RadioButton) findViewById(R.id.rbHomem);
		rdbHomem.setOnClickListener(this);
		rdbMulher = (RadioButton) findViewById(R.id.rbMulher);
		rdbMulher.setOnClickListener(this);
		txtData = (EditText) findViewById(R.id.edtData);
		
		
		
		
		spCategoriaPerfil1.setSelection(ar.getPosition(usuario.getEventoFavorito1()));
		spCategoriaPerfil2.setSelection(ar.getPosition(usuario.getEventoFavorito2()));
		spCategoriaPerfil3.setSelection(ar.getPosition(usuario.getEventoFavorito3()));
		
		txtboxEditarEmail= (EditText) findViewById(R.id.txtboxEditarEmail);
		txtboxSenhaAntiga= (EditText) findViewById(R.id.txtboxSenhaAntiga);
		txtboxNovaSenha1= (EditText) findViewById(R.id.txtboxNovaSenha1);
		txtboxNovaSenha2= (EditText) findViewById(R.id.txtboxNovasenha2);
		btnEditar= (Button) findViewById(R.id.btnEditar);
		btnEditar.setOnClickListener(this);
		btnExcluir = (Button)findViewById(R.id.btnExcluir);
		btnExcluir.setOnClickListener(this);
		
		
		dialogClick=new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if(which==DialogInterface.BUTTON_POSITIVE){
					
					Banco banco = new Banco(TelaPerfilUsuario.this);
					Evento.getMeusEventos().clear();
					banco.setMeusEventos(Usuario.getId());
					for (int i = 0; i < Evento.getMeusEventos().size(); i++) {
						System.out.println(Evento.getMeusEventos().get(i).getNome()+ " Evento da lista de eventos a ser exclu�do");
						banco.excluir(Evento.getMeusEventos().get(i), Usuario.getId());	
					}
					banco.excluir(Usuario.getId());
					Usuario.setId(0);
					
					AlertDialog.Builder builder = new AlertDialog.Builder(TelaPerfilUsuario.this);
					
					builder.setMessage("Usu�rio exclu�do com sucesso").setTitle("Exclus�o");

					// 3. Get the AlertDialog from create()
					AlertDialog dialogo = builder.create();
					dialogo.show();
					Intent intent=new Intent(TelaPerfilUsuario.this, TelaPrincipal.class);
					startActivity(intent);

				}
				
				
			}
		};
		passarClick=new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent=new Intent(TelaPerfilUsuario.this, TelaPrincipal.class);
				startActivity(intent);
				
			}
		};
		
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tela_perfil_usuario, menu);
		return true;
	}
	String sexo ="";
	@Override
	public void onClick(View v) {
		
		View focusView=null;
		if(v.getId()==R.id.rbMulher){
			opcoesRadioButton(rdbMulher);
			sexo = "Mulher";
		}if(v.getId()==R.id.rbHomem){
			opcoesRadioButton(rdbHomem);
			sexo = "Homem";
		}
		System.out.println(sexo);
		
		if(v.getId()==R.id.btnExcluir){
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			
			builder.setMessage("Tem certeza que ir�s excluir o perfil?").setTitle("Excluir").setPositiveButton("Sim", dialogClick).setNegativeButton("N�o", dialogClick);
			AlertDialog dialog = builder.create();
			dialog.show();			
		}
		if(v.getId()==R.id.btnEditar){
			
			if(txtboxEditarEmail.getText().toString().contains("@") || txtboxEditarEmail.getText().toString().equals("") ){
				Banco banco=new Banco(this);
				int retorno=banco.editarUsuario(txtboxEditarEmail.getText().toString(),txtboxNovaSenha1.getText().toString(), txtboxNovaSenha2.getText().toString(), txtboxSenhaAntiga.getText().toString(), spCategoriaPerfil1.getSelectedItem().toString(), spCategoriaPerfil2.getSelectedItem().toString(), spCategoriaPerfil3.getSelectedItem().toString(),sexo,txtData.getText().toString());
				if(retorno==0){
					AlertDialog.Builder builder = new AlertDialog.Builder(this);
	
					builder.setMessage("Todos os campos foram alterados com sucesso").setTitle("Campos alterados").setPositiveButton("OK", passarClick);
	
					// 3. Get the AlertDialog from create()
					AlertDialog dialog = builder.create();
					dialog.show();
				}
				if(retorno==1){
					/*txtboxEditarEmail.setError("Email j� cadastrado");
					focusView=txtboxEditarEmail;
					focusView.requestFocus();*/
					AlertDialog.Builder builder = new AlertDialog.Builder(this);
					
					builder.setMessage("Campo e-mail e/ou eventos modificados com sucesso").setTitle("Campos alterados").setPositiveButton("OK", passarClick);
	
					// 3. Get the AlertDialog from create()
					AlertDialog dialog = builder.create();
					dialog.show();
				}
				if(retorno==2){
					/*txtboxSenhaAntiga.setError("Senha incorreta");
					focusView=txtboxSenhaAntiga;
					focusView.requestFocus();*/
					AlertDialog.Builder builder = new AlertDialog.Builder(this);
					
					builder.setMessage("Campo senha e/ou eventos modificados com sucesso").setTitle("Campos alterados").setPositiveButton("OK", passarClick);
	
					// 3. Get the AlertDialog from create()
					AlertDialog dialog = builder.create();
					dialog.show();
				}
				if(retorno==3){
					/*txtboxNovaSenha1.setError("As senhas novas s�o diferentes");
					focusView=txtboxNovaSenha1;
					focusView.requestFocus();*/
					txtboxEditarEmail.setError("Email j� cadastrado ou inv�lido");
					focusView=txtboxEditarEmail;
					focusView.requestFocus();
				}
				if(retorno==4){
					txtboxNovaSenha1.setError("As senhas novas s�o diferentes");
					focusView=txtboxNovaSenha1;
					focusView.requestFocus();
				}
				if(retorno==5){
					txtboxSenhaAntiga.setError("Senha incorreta");
					focusView=txtboxSenhaAntiga;
					focusView.requestFocus();
				}

			}
			else{
				txtboxEditarEmail.setError("Email inv�lido");
				focusView=txtboxEditarEmail;
				focusView.requestFocus();
			}
		}
		
		
	}
	
public void opcoesRadioButton(View view){
		
		boolean checked = ((RadioButton) view).isChecked();
	    
	    // Check which radio button was clicked
	    switch(view.getId()) {
	        case R.id.rbHomem:
	            if (checked)
	            break;
	        case R.id.rbMulher:
	            if (checked)
	            break;
	    }

	}


}
