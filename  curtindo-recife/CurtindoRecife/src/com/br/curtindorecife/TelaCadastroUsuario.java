package com.br.curtindorecife;


import dominio.Mask;
import dominio.Usuario;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.text.DateFormat;
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
	String sexo="";
	Spinner spCategorias,spCategorias2,spCategorias3;
	DateFormat format = DateFormat.getDateInstance();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tela_cadastro_usuario);
		
		spCategorias = (Spinner) findViewById(R.id.spCategorias);
		ArrayAdapter<CharSequence> ar = ArrayAdapter.createFromResource(this,R.array.Categorias,android.R.layout.simple_list_item_1);
		ar.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		spCategorias.setAdapter(ar);
		
		spCategorias2 = (Spinner) findViewById(R.id.spCategorias2);
		spCategorias2.setAdapter(ar);
		
		spCategorias3 = (Spinner) findViewById(R.id.spCategorias3);
		spCategorias3.setAdapter(ar);
		
		txtDataDeNascimento=(EditText) findViewById(R.id.cadastroNascimento);
		txtDataDeNascimento.addTextChangedListener(Mask.insert("##/##/####", txtDataDeNascimento));
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tela_cadastro_usuario, menu);
		
		txtNome=(EditText) findViewById(R.id.cadastroNome);
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
	
	String NomeBanco = "CurtindoRecifeDB";
	SQLiteDatabase BancoDados = null;
	Cursor cursor;
	
	@SuppressWarnings("deprecation")
	public void Cadastrar(String nome, String dataDeNascimento, String email, String senha, String sexo, String eventoFavorito1, String eventoFavorito2, String eventoFavorito3){
		//Cadastra Usuário
		//o formato da data é (YYYY-MM-DD)
		try {
			BancoDados = openOrCreateDatabase(NomeBanco, MODE_WORLD_READABLE, null);
			String sql = "INSERT INTO tabelaUsuarios (nome, dataNascimento, email, senha, sexo, eventoFavorito1, eventoFavorito2, eventoFavorito3) VALUES ('"+nome+"','"+dataDeNascimento+"','"+email+"','"+senha+"','"+sexo+"','"+eventoFavorito1+"','"+eventoFavorito2+"','"+eventoFavorito3+"')";
			BancoDados.execSQL(sql);
		} catch (Exception erro) {
			// TODO: handle exception
			System.out.println(erro);
		}finally{
			BancoDados.close();
		}
	}
	
	public boolean cadastrar(){
		boolean validar=true;
		String nome=txtNome.getText().toString();
		String email=txtEmail.getText().toString();
		String dataDeNascimento=txtDataDeNascimento.getText().toString();
		String senha=txtSenha.getText().toString();
		String eventoFavorito1 = spCategorias.getSelectedItem().toString();
		String eventoFavorito2 = spCategorias2.getSelectedItem().toString();
		String eventoFavorito3 = spCategorias3.getSelectedItem().toString();
		
		
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
		if(validar){
		Cadastrar(nome, dataDeNascimento, email, senha, sexo, eventoFavorito1, eventoFavorito2,eventoFavorito3);
		System.out.println(eventoFavorito1);
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
			sexo="Homem";
		}
		if(v.getId()==R.id.rdbMulher){
			opcoesRadioButton(rdbMulher);
			sexo="Mulher";
		}
		
		
	}
	
	public void opcoesRadioButton(View view){
		
		boolean checked = ((RadioButton) view).isChecked();
	    
	    // Check which radio button was clicked
	    switch(view.getId()) {
	        case R.id.rdbHomem:
	            if (checked)
	            break;
	        case R.id.rdbMulher:
	            if (checked)
	            break;
	    }

	}

}
