package dominio;

import java.util.ArrayList;
import java.util.List;

import bd.Banco;

import com.br.curtindorecife.R;
import com.br.curtindorecife.TelaAgenda;
import com.br.curtindorecife.TelaCategoriaEvento;
import com.br.curtindorecife.TelaEventos;
import com.br.curtindorecife.TelaEventosData;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Space;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Spinner;

public class FragmentListaEventos extends Fragment {
	
	String data, atual;
	ArrayList<Evento> listaEventosData;
	Spinner spCategoriaData;
	static int position;
	public FragmentListaEventos(String data){
		this.data = data;
	}
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(
				R.layout.fragment_lista_eventos, container, false);
		
		spCategoriaData = (Spinner) rootView.findViewById(R.id.spCategoriaData);
		ArrayAdapter<CharSequence> ar = ArrayAdapter.createFromResource(getActivity(),R.array.Categorias2,android.R.layout.simple_list_item_1);
		ar.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		spCategoriaData.setAdapter(ar);
		System.out.println(position+" POSITION");
		spCategoriaData.setSelection(position);
		
		
		atual = spCategoriaData.getSelectedItem().toString();
		
		spCategoriaData.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int pos, long arg3) {
				if(!spCategoriaData.getSelectedItem().toString().equals(atual)){
					 position = pos;
					System.out.println(position+" POSITION no ONITEM");
					Intent intent = new Intent(getActivity(),TelaAgenda.class);
					startActivity(intent);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			
				
			}
		});{
			
		}
		
		Banco banco = new Banco(getActivity());
		if(spCategoriaData.getSelectedItem().toString().equals("Todos")){
			listaEventosData = banco.ListarEventoPorData(data);
		}
		else if(spCategoriaData.getSelectedItem().toString().equals(spCategoriaData.getItemAtPosition(position).toString())){
			listaEventosData = banco.ListarEventoPorData(data,spCategoriaData.getItemAtPosition(position).toString());
		}
		
		List EventoList = listaEventosData;
		
		ArrayAdapter ad = new CustomAdapter(getActivity(), R.layout.item, EventoList);
        ListView lv = (ListView) rootView.findViewById(R.id.listEventoData);
        lv.setAdapter(ad);
        lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(getActivity(),TelaEventosData.class);
				int position=arg2;
				System.out.println(position);
				//Mensagem.clickData=true;
				Mensagem.listaData.clear();
				Mensagem.listaData=listaEventosData;
				intent.putExtra("position", position);
				startActivity(intent);
			}
			
		});
        
		return rootView;
	}

}
