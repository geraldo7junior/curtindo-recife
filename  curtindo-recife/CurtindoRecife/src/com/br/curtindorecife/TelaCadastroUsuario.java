package com.br.curtindorecife;

import dominio.Usuario;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

public class TelaCadastroUsuario extends Activity implements OnClickListener {

	
	EditText txtNome;
	EditText txtDataDeNascimento;
	EditText txtEmail;
	EditText txtSenha;
	Button btnCadastrar;
	RadioButton rdbHomem;
	RadioButton rdbMulher;
	Spinner spCategorias;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tela_cadastro_usuario);
		
		spCategorias = (Spinner) findViewById(R.id.spCategorias);
		ArrayAdapter<CharSequence> ar = ArrayAdapter.createFromResource(this,R.array.Categorias,android.R.layout.simple_list_item_1);
		ar.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		spCategorias.setAdapter(ar);
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
		rdbHomem=(RadioButton) findViewById(R.id.rdbHomem);
		rdbHomem.setOnClickListener(this);
		rdbMulher=(RadioButton) findViewById(R.id.rdbMulher);
		rdbMulher.setOnClickListener(this);
		return true;
	}
	
	public boolean cadastrar(){
		boolean validar=true;
		String nome=txtNome.getText().toString();
		String email=txtEmail.getText().toString();
		String dataDeNascimento=txtDataDeNascimento.getText().toString();
		String senha=txtSenha.getText().toString();
		
		Usuario usuario= new Usuario(nome, email, senha, dataDeNascimento, null, null, null, null);
		//UsuarioDAO.cadastrarUsuario(usuario);
		System.out.println(usuario.getEmail());
		System.out.println(usuario.getSenha());
		
		if(usuario.getSenha().length()<=4){
			AlertDialog.Builder builder = new AlertDialog.Builder(this);

			// 2. Chain together various setter methods to set the dialog characteristics
			builder.setMessage("A senha está curta. Ela deve possuir mais de 4 caracteres");

			// 3. Get the AlertDialog from create()
			AlertDialog dialog = builder.create();
			dialog.show();
			validar=false;
		}
		
		if(!usuario.getEmail().contains("@")){
			AlertDialog.Builder builder = new AlertDialog.Builder(this);

			// 2. Chain together various setter methods to set the dialog characteristics
			builder.setMessage("Endereço de e-mail inválido.");

			// 3. Get the AlertDialog from create()
			AlertDialog dialog = builder.create();
			dialog.show();
			validar=false;
		}
		return validar;
		
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		if(v.getId()==R.id.btnCadastrar){
			if(cadastrar()){

				AlertDialog.Builder builder = new AlertDialog.Builder(this);
	
				// 2. Chain together various setter methods to set the dialog characteristics
				builder.setMessage("Usuário Cadastrado com Sucesso")
				       .setTitle("Cadastrado");
	
				// 3. Get the AlertDialog from create()
				AlertDialog dialog = builder.create();
				dialog.show();
			}
		}
		if(v.getId()==R.id.rdbHomem){
			opcoesRadioButton(rdbHomem);
		}
		if(v.getId()==R.id.rdbMulher){
			opcoesRadioButton(rdbMulher);
		}
		
		
	}
	
	public void opcoesRadioButton(View view){
		
		boolean checked = ((RadioButton) view).isChecked();
	    
	    // Check which radio button was clicked
	    switch(view.getId()) {
	        case R.id.rdbHomem:
	            if (checked)
	                // Pirates are the best
	            break;
	        case R.id.rdbMulher:
	            if (checked)
	                // Ninjas rule
	            break;
	    }

	}

}
