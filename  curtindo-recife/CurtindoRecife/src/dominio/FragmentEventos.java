package dominio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.br.curtindorecife.R;
import com.br.curtindorecife.TelaCadastroEvento;

public class FragmentEventos extends Fragment implements OnClickListener {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		Button btnSimbora;
		TextView txtNomeEvento;
		TextView txtEndereco;
		TextView txtData;
		TextView txtHora;
		TextView txtDescricao;
		ImageView imgEvento;
		public static final String ARG_SECTION_NUMBER = "section_number";

		public FragmentEventos() {
			// TODO Auto-generated constructor stub
		}
		
		public FragmentEventos(String nomeEvento, int imagem,String endereco, String numero, String data, String hora, String telefone, String descricao){
			
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_tela_eventos_dummy, container, false);
			
			btnSimbora = (Button) rootView.findViewById(R.id.btnSimbora);
			btnSimbora.setOnClickListener(this);
			
			return rootView;
		}
		
		@Override
		public void onClick(View v) {
			
			if(v.getId()==R.id.btnSimbora){
				Intent intent = new Intent(getActivity(),TelaCadastroEvento.class);
				startActivity(intent);
			}
			// TODO Auto-generated method stub
			
		}
	}
	


