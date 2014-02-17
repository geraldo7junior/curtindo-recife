package com.br.curtindorecife;


import dominio.Mask;
import dominio.Usuario;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;



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
	DialogInterface.OnClickListener dialogClick;
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
		//Cadastra Usu�rio
		//o formato da data � (YYYY-MM-DD)
		try {
			BancoDados = openOrCreateDatabase(NomeBanco, MODE_WORLD_READABLE, null);
			String sql = "INSERT INTO tabelaUsuarios (nome, dataNascimento, email, senha, sexo, eventoFavorito1, eventoFavorito2, eventoFavorito3, mascates, ranking) VALUES ('"+nome+"','"+dataDeNascimento+"','"+email+"','"+senha+"','"+sexo+"','"+eventoFavorito1+"','"+eventoFavorito2+"','"+eventoFavorito3+"', '150','0')";
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
		
		if(eventoFavorito1.equals(eventoFavorito2) || eventoFavorito1.equals(eventoFavorito3) || eventoFavorito2.equals(eventoFavorito3)){
			eventoFavorito1 ="";
			eventoFavorito2="";
			eventoFavorito3="";
			Toast toast = Toast.makeText(getApplicationContext(), "Todos os eventos devem ser diferentes, escolha novamente",Toast.LENGTH_LONG );
			toast.show();
			System.out.println(toast.getDuration());
			validar = false;
			
			
		}
		DateFormat formatoData=new SimpleDateFormat("dd/MM/yyyy");
		
		
		
		Usuario usuario= new Usuario(nome, email, senha, dataDeNascimento, null, null, null, null);
		//UsuarioDAO.cadastrarUsuario(usuario);
		System.out.println(usuario.getEmail());
		System.out.println(usuario.getSenha());
		if(usuario.getSenha().length()<=4){
			txtSenha.setError("A senha deve possuir pelo menos 5 caracteres");
			validar=false;
		}
		
		if(!usuario.getEmail().contains("@")){
			txtEmail.setError("E-mail inv�lido");
			validar=false;
		}
		if(usuario.getDataDeNascimento().equals("")){
			txtDataDeNascimento.setError("Campo obrigat�rio");
		}
		
			//datas v�lidas
			try {
				Date date=new Date();
				formatoData.parse(usuario.getDataDeNascimento());
				String dia=usuario.getDataDeNascimento().substring(0, 2);
				String mes=usuario.getDataDeNascimento().substring(3, 5);
				String ano=usuario.getDataDeNascimento().substring(6, 10);
				String anoHoje= formatoData.format(date);
				
				int diaConvertido=Integer.parseInt(dia);
				int mesConvertido=Integer.parseInt(mes);
				int anoConvertido=Integer.parseInt(ano);
				int anoHojeConvertido=Integer.parseInt(anoHoje.substring(6, 10));
				
				int anoparametro1=anoHojeConvertido-100;
				int anoparametro2=anoHojeConvertido-2;
				
				if(((diaConvertido<=0) || (diaConvertido>31))||((mesConvertido<=0)||(mesConvertido>12))||((anoConvertido<anoparametro1)||(anoConvertido>anoparametro2))){
					validar=false;
					txtDataDeNascimento.setError("Data inv�lida");
				}
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				validar=false;
				txtDataDeNascimento.setError("Data inv�lida");
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
		dialogClick=new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				Intent intent = new Intent(TelaCadastroUsuario.this, TelaLogin.class);
				startActivity(intent);
			}
		};
		if(v.getId()==R.id.btnCadastrar){
			if(cadastrar()){
				
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
	
				// 2. Chain together various setter methods to set the dialog characteristics
				builder.setMessage("Usu�rio Cadastrado com Sucesso")
				       .setTitle("Cadastrado").setPositiveButton("OK", dialogClick);
	
				// 3. Get the AlertDialog from create()
				AlertDialog dialog = builder.create();
				dialog.show();
					
				//4.Open new window
				
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
