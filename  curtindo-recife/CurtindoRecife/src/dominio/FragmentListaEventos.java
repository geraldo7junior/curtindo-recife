package dominio;

import java.util.ArrayList;
import java.util.List;

import bd.Banco;

import com.br.curtindorecife.R;
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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Spinner;

public class FragmentListaEventos extends Fragment {
	
	String data;
	ArrayList<Evento> listaEventosData;
	Spinner spCategoriaData;
	public FragmentListaEventos(String data){
		this.data = data;
	}
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(
				R.layout.fragment_lista_eventos, container, false);
		spCategoriaData = (Spinner) rootView.findViewById(R.id.spCategoriaData);
		ArrayAdapter<CharSequence> ar = ArrayAdapter.createFromResource(getActivity(),R.array.Categorias,android.R.layout.simple_list_item_1);
		ar.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		spCategoriaData.setAdapter(ar);
		
		Banco banco = new Banco(getActivity());
		listaEventosData = banco.ListarEventoPorData(data);
		
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
