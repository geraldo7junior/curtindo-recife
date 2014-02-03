package com.br.curtindorecife;

import java.util.List;

import bd.Banco;

import dominio.CustomAdapter;
import dominio.CustomAdapterEstabelecimento;
import dominio.Estabelecimento;
import dominio.Evento;
import dominio.Usuario;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class TelaMeusEstabelecimentos extends FragmentActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tela_meus_estabelecimentos);
		List EstabelecimentoList = createEstabelecimentos();
		//PRA TELA ESTABELECIMENTOS, TROCAR O CUSTOMADAPTER POR CUSTOMADAPTERESTABELECIMENTO
        ArrayAdapter ad = new CustomAdapterEstabelecimento(this, R.layout.item, EstabelecimentoList);
        ListView lv = (ListView) findViewById(R.id.listEstabelecimento);
        lv.setAdapter(ad);
        lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(TelaMeusEstabelecimentos.this,TelaEventos.class);
				int position=arg2;
				Evento.setMeusEventosClickados(true);
				intent.putExtra("position", position);
				startActivity(intent);
			}
			
		});
	}
	private List createEstabelecimentos(){
		Banco banco= new Banco(this);
    	//PARA ESTABELECIMENTOS, A LISTA P RECEBERÁ A LSITA  MEUS ESTABELECIMENTOS
        Estabelecimento.getListaMeusEstabelecimentos().clear();
        Usuario usuario = banco.getUsuario(Usuario.getId()); 
        Estabelecimento.setListaMeusEstabelecimentos(banco.getListaMeusEstabelecimentos(usuario));
		List p = Estabelecimento.getListaMeusEstabelecimentos();
        return p;
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tela_meus_estabelecimentos, menu);
		return true;
	}

}
