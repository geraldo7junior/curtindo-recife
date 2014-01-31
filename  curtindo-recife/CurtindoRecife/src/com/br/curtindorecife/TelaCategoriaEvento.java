package com.br.curtindorecife;

import java.util.ArrayList;
import java.util.List;

import bd.Banco;

import com.br.curtindorecife.R.id;

import dominio.CustomAdapter;
import dominio.Estabelecimento;
import dominio.Evento;
import dominio.Usuario;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

public class TelaCategoriaEvento extends Activity {
	static int numEventos;
	TextView txtCategoria;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Evento.getListaEventos().clear();
		setContentView(R.layout.activity_tela_categoria_evento);
		numEventos=0;
		txtCategoria = (TextView)findViewById(R.id.txtCategoria);
		if(Evento.getAtual().equals("Top10")){
			getTodasCategorias();
			txtCategoria.setText("Top10");
			/*for (int i = 0; i < Evento.getListaEventos().size(); i++) {
				System.out.println("
 da categoria: "+Evento.getListaEventos().get(i).getNome());
			}*/
		}
		if(!Evento.getAtual().equals("")){
			if(Evento.getAtual().equals("Night")){
				getTodasCategorias();
				txtCategoria.setText("Night");
			}
			else{
				getCategoriasEventos(Evento.getAtual());
				txtCategoria.setText(Evento.getAtual());
				for (int i = 0; i < Evento.getListaEventos().size(); i++) {
					System.out.println("Evnto da categoria: "+Evento.getListaEventos().get(i).getNome());
				}
			
			}
		}else{
			
			txtCategoria.setText(Estabelecimento.getAtual());
		}
		
		List EventoList = createEventos();
        ArrayAdapter ad = new CustomAdapter(TelaCategoriaEvento.this, R.layout.item, EventoList);
        ListView lv = (ListView) findViewById(R.id.listaCategoriaEvento);
        lv.setAdapter(ad);
        lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(TelaCategoriaEvento.this,TelaEventos.class);
				int position=arg2;
				System.out.println(position);
				intent.putExtra("position", position);
				startActivity(intent);
			}
			
		});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if(Usuario.getId()==0){
			MenuInflater menuInflater=getMenuInflater();
			menuInflater.inflate(R.menu.main, menu);
			
			
		}else{
			MenuInflater menuInflater=getMenuInflater();
			menuInflater.inflate(R.menu.main_logado, menu);
		}
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
	//Realizar um case pelo “Id” dos itens e logo em seguida mostrar uma mensagem ao usuário
	  switch (item.getItemId())
	  {     
	   case id.Cadastrar:
		   Intent intent = new Intent(this, TelaCadastroUsuario.class);
		   startActivity(intent);
	   break;
	   
	   case id.sairPontinhos:
		   Usuario.setId(0);
		   Intent intentSair = new Intent(this, TelaLogin.class);
		   startActivity(intentSair);
		   break;
	  }
	   //Retornar a classe pai
	   return super.onOptionsItemSelected(item);
	}
    
    private List createEventos(){
    	
        List p;
        if(!Evento.getAtual().equals("")){
	        if(Evento.getAtual().equals("Top10")){       	
	        	Evento.setListaEventos(Evento.ranking());   
	        	p=Evento.getListaEventos();
	        }
	        if(Evento.getAtual().equals("Night")){
	        	Evento.setListaEventos(Evento.listaNight());   
	        	p=Evento.getListaEventos();
	        }
	        else{
	        	
	        	p=Evento.getListaEventos();  
	            
	        }
        }
        else{
        	Banco banco =new Banco(this);
        	p=banco.getListaEstabelecimentos(Estabelecimento.getAtual());
        	
        }
        return p;
    }
    
	public void getCategoriasEventos(String nomeEvento){
		String NomeBanco = "CurtindoRecifeDB";
		SQLiteDatabase BancoDados = null;
		Cursor cursor;
		try {
			BancoDados = openOrCreateDatabase(NomeBanco, MODE_WORLD_READABLE, null);
			String sql = "SELECT * FROM tabelaEventos WHERE tipo LIKE '"+nomeEvento+"'";
			cursor = BancoDados.rawQuery(sql, null);
			numEventos=(cursor.getCount());
			cursor.moveToFirst();
			for(int i=0;i<cursor.getCount();i++){			
				Evento.addListaEventos(new Evento(cursor.getString(cursor.getColumnIndex("nome")),cursor.getString(cursor.getColumnIndex("data")),cursor.getString(cursor.getColumnIndex("hora")),cursor.getInt(cursor.getColumnIndex("idImagem")),cursor.getInt(cursor.getColumnIndex("_id")),cursor.getInt(cursor.getColumnIndex("idOwner")),cursor.getString(cursor.getColumnIndex("descricao")),cursor.getString(cursor.getColumnIndex("tipo")),cursor.getString(cursor.getColumnIndex("telefone")),cursor.getInt(cursor.getColumnIndex("simboras")),cursor.getString(cursor.getColumnIndex("preco")),cursor.getString(cursor.getColumnIndex("numero")),cursor.getString(cursor.getColumnIndex("endereco")),false));
				Evento.getListaEventos().get(i).setCurtido(eventoCurtido(Evento.getListaEventos().get(i)));
				if(i!=cursor.getCount()-1){
					cursor.moveToNext();
				}
				
			}
			
		} catch (Exception erro) {
			System.out.println(erro);
			// retorna 0 caso o email não seja encontrado ou algum erro no banco.
		}finally{
			BancoDados.close();
		}	
	}
	
	
	public void getTodasCategorias(){
		String NomeBanco = "CurtindoRecifeDB";
		SQLiteDatabase BancoDados = null;
		Cursor cursor;
		try {
			Evento.getListaEventos().clear();
			BancoDados = openOrCreateDatabase(NomeBanco, MODE_WORLD_READABLE, null);
			String sql = "SELECT * FROM tabelaEventos";
			cursor = BancoDados.rawQuery(sql, null);
			numEventos=(cursor.getCount());
			cursor.moveToFirst();
			for(int i=0;i<cursor.getCount();i++){			
				Evento.addListaEventos(new Evento(cursor.getString(cursor.getColumnIndex("nome")),cursor.getString(cursor.getColumnIndex("data")),cursor.getString(cursor.getColumnIndex("hora")),cursor.getInt(cursor.getColumnIndex("idImagem")),cursor.getInt(cursor.getColumnIndex("_id")),cursor.getInt(cursor.getColumnIndex("idOwner")),cursor.getString(cursor.getColumnIndex("descricao")),cursor.getString(cursor.getColumnIndex("tipo")),cursor.getString(cursor.getColumnIndex("telefone")),cursor.getInt(cursor.getColumnIndex("simboras")),cursor.getString(cursor.getColumnIndex("preco")),cursor.getString(cursor.getColumnIndex("numero")),cursor.getString(cursor.getColumnIndex("endereco")), false));
				Evento.getListaEventos().get(i).setCurtido(eventoCurtido(Evento.getListaEventos().get(i)));
				if(i!=cursor.getCount()-1){
					cursor.moveToNext();
				}
				
			}
			
			Evento.ranking();
			
		} catch (Exception erro) {
			System.out.println(erro);
			// retorna 0 caso o email não seja encontrado ou algum erro no banco.
		}finally{
			BancoDados.close();
		}	
	}
	public boolean eventoCurtido(Evento evento){
		String NomeBanco = "CurtindoRecifeDB";
		SQLiteDatabase BancoDados = null;
		Cursor cursor;
		if(Usuario.getId()!=0){
			try {
				BancoDados = openOrCreateDatabase(NomeBanco, MODE_WORLD_READABLE, null);
				String sql = "SELECT idUsuario FROM tabelaMeusEventos WHERE idEvento LIKE '"+evento.getId()+"' AND idUsuario LIKE '"+Usuario.getId()+"'";
				cursor = BancoDados.rawQuery(sql, null);
				cursor.moveToFirst();
				System.out.println(cursor.getInt(cursor.getColumnIndex("idUsuario"))+" ID USUÀRIO");
				System.out.println("CRIADOR DO EVENTO "+evento.getIdOwner());
				System.out.println("Nome do evento: "+evento.getNome());
				if((Usuario.getId()!=evento.getIdOwner())){
					return true;
				}
				else{
					return false;
				}
				
			} catch (Exception erro) {
				System.out.println(erro);
				return false;
				// retorna 0 caso o email não seja encontrado ou algum erro no banco.
			}finally{
				BancoDados.close();
			}	
		}
		else{
			return false;
		}
	}

}
