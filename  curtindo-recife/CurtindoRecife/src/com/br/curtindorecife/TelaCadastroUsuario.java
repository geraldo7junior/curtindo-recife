package com.br.curtindorecife;

import dominio.Usuario;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class TelaCadastroUsuario extends Activity implements OnClickListener {

	
	EditText txtNome;
	EditText txtDataDeNascimento;
	EditText txtEmail;
	EditText txtSenha;
	Button btnCadastrar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tela_cadastro_usuario);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tela_cadastro_usuario, menu);
		
		txtNome=(EditText) findViewById(R.id.cadastroNome);
		txtDataDeNascimento=(EditText) findViewById(R.id.cadastroNascimento);
		txtEmail=(EditText)findViewById(R.id.cadastroEmail);
		txtSenha=(EditText)findViewById(R.id.cadastroSenhar);
		btnCadastrar=(Button) findViewById(R.id.btnCadastrar);
		btnCadastrar.setOnClickListener(this);
		return true;
	}
	
	public void cadastrar(){
		String nome=txtNome.getText().toString();
		String email=txtEmail.getText().toString();
		String dataDeNascimento=txtDataDeNascimento.getText().toString();
		String senha=txtSenha.getText().toString();
		
		Usuario usuario= new Usuario(nome, email, senha, dataDeNascimento, null, null, null, null);
		//UsuarioDAO.cadastrarUsuario(usuario);
		System.out.println(usuario.getEmail());
		System.out.println(usuario.getSenha());
		
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		if(v.getId()==R.id.btnCadastrar){
			cadastrar();

			AlertDialog.Builder builder = new AlertDialog.Builder(this);

			// 2. Chain together various setter methods to set the dialog characteristics
			builder.setMessage("Usuário Cadastrado com Sucesso")
			       .setTitle("Cadastrado");

			// 3. Get the AlertDialog from create()
			AlertDialog dialog = builder.create();
			dialog.show();
		}
		
	}

}
