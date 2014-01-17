package com.br.curtindorecife;

import java.util.ArrayList;
import java.util.List;

import dominio.CustomAdapter;
import dominio.Evento;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class TelaCategoriaEvento extends Activity {
	Spinner spCategoriaEvento;
	static int numEventos;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Evento.getListaEventos().clear();
		numEventos=0;
		getCategoriasEventos(Evento.getAtual());
		setContentView(R.layout.activity_tela_categoria_evento);
		spCategoriaEvento = (Spinner) findViewById(R.id.spCategoriaEvento);
		ArrayAdapter<CharSequence> ar = ArrayAdapter.createFromResource(this,R.array.Categorias,android.R.layout.simple_list_item_1);
		ar.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		spCategoriaEvento.setAdapter(ar);
		//String tipo=spCategoriaEvento.getOnItemSelectedListener().toString();
		//System.out.println(tipo);
		
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
	
    
    private List createEventos(){
        List p = Evento.getListaEventos();  
        return p;
    }
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tela_categoria_evento, menu);
		return true;
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
				Evento.addListaEventos(new Evento(cursor.getString(cursor.getColumnIndex("nome")),cursor.getString(cursor.getColumnIndex("data")),cursor.getString(cursor.getColumnIndex("hora")),cursor.getInt(cursor.getColumnIndex("idImagem")),cursor.getInt(cursor.getColumnIndex("_id")),cursor.getInt(cursor.getColumnIndex("idOwner")),cursor.getString(cursor.getColumnIndex("descricao")),cursor.getString(cursor.getColumnIndex("tipo")),cursor.getString(cursor.getColumnIndex("telefone")),cursor.getInt(cursor.getColumnIndex("simboras")),cursor.getString(cursor.getColumnIndex("preco")),cursor.getString(cursor.getColumnIndex("numero")),cursor.getString(cursor.getColumnIndex("endereco"))));
				if(i!=cursor.getCount()-1){
					cursor.moveToNext();
				}
				
			}
			
		} catch (Exception erro) {
			System.out.println(erro);
			// retorna 0 caso o email n�o seja encontrado ou algum erro no banco.
		}finally{
			BancoDados.close();
		}	
	}
	

}
