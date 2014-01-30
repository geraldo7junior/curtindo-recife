package dominio;

import java.util.ArrayList;
import java.util.List;

import bd.Banco;

import com.br.curtindorecife.R;
import com.br.curtindorecife.TelaCategoriaEvento;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FragmentListaEventos extends Fragment {
	
	String data;
	
	public FragmentListaEventos(String data){
		this.data = data;
	}
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(
				R.layout.fragment_lista_eventos, container, false);
		Banco banco = new Banco(getActivity());
		ArrayList<Evento> listaEventosData = banco.ListarEventoPorData(data);
		
		List EventoList = listaEventosData;
		
		ArrayAdapter ad = new CustomAdapter(getActivity(), R.layout.item, EventoList);
        ListView lv = (ListView) rootView.findViewById(R.id.listEventoData);
        lv.setAdapter(ad);
        
		return rootView;
	}

}
