package com.br.curtindorecife;

import java.util.ArrayList;
import java.util.List;

import dominio.CustomAdapter;
import dominio.Evento;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class TelaCategoriaEvento extends Activity {
	Spinner spCategoriaEvento;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tela_categoria_evento);
		spCategoriaEvento = (Spinner) findViewById(R.id.spCategoriaEvento);
		ArrayAdapter<CharSequence> ar = ArrayAdapter.createFromResource(this,R.array.Categorias,android.R.layout.simple_list_item_1);
		ar.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		spCategoriaEvento.setAdapter(ar);
		
		List EventoList = createEventos();
        ArrayAdapter ad = new CustomAdapter(TelaCategoriaEvento.this, R.layout.item, EventoList);
        ListView lv = (ListView) findViewById(R.id.listaCategoriaEvento);
        lv.setAdapter(ad);
    }
    
    private List createEventos(){
        List p = new ArrayList();
        p.add(new Evento("Rock in Rio", "12/11/2014", "22:00", R.drawable.logo_recife));
        p.add(new Evento("Maragandê", "12/13/2014", "22:00", R.drawable.ic_launcher));
        p.add(new Evento("Familia", "25/11/2014", "22:00", R.drawable.familia));
        
        
        /*for(int i=0;i<numEventos;i++){
        	p.add(new Evento(nomes.get(i), datas.get(i), horas.get(i), R.drawable.ic_launcher));
        }*/
        return p;
    }
    
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tela_categoria_evento, menu);
		return true;
	}

}
