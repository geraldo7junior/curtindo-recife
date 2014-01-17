package dominio;

import android.content.Intent;
import android.os.Bundle;
import android.sax.TextElementListener;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import bd.Banco;
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
		TextView txtNumero;
		TextView txtDescricao;
		ImageView imgEvento;
		TextView txtPreco;
		
		public String nomeEvento;
		public String endereco;
		public String numero;
		public String data;
		public String hora;
		public String telefone;
		public String descricao;
		public String preco;
		public int imagem;
		public int id;
		
		public static final String ARG_SECTION_NUMBER = "section_number";

		public FragmentEventos() {
			// TODO Auto-generated constructor stub
		}
		
		public FragmentEventos(Evento evento){
			this.nomeEvento= evento.getNome();
			this.endereco=evento.getEndereco();
			this.imagem=evento.getImage();
			this.data=evento.getData();
			this.numero=evento.getNumero();
			this.hora=evento.getHora();
			this.telefone=evento.getTelefone();
			this.descricao=evento.getDescricao();
			this.preco=evento.getPreco();
			this.id = evento.getId();
		}
		
		
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_tela_eventos_dummy, container, false);
			//_Relacionamento
			
			btnSimbora = (Button) rootView.findViewById(R.id.btnSimbora);
			btnSimbora.setOnClickListener(this);
			txtNomeEvento = (TextView) rootView.findViewById(R.id.txtTituloEvento);
			txtDescricao = (TextView) rootView.findViewById(R.id.textView1);
			txtEndereco = (TextView) rootView.findViewById(R.id.txtEndereco);
			txtHora = (TextView) rootView.findViewById(R.id.txtHora);
			txtPreco = (TextView) rootView.findViewById(R.id.txtPreco);
			imgEvento = (ImageView)rootView.findViewById(R.id.imgEvento);
			txtData = (TextView) rootView.findViewById(R.id.txtData);
			

			txtEndereco.setText(this.endereco+", "+this.numero);
			txtNomeEvento.setText(this.nomeEvento);
			imgEvento.setBackgroundResource(this.imagem);
			txtData.setText(this.data);
			txtHora.setText(this.hora);
			txtPreco.setText(this.preco);
			txtDescricao.setText(this.descricao);
			return rootView;
		}
		
		@Override
		public void onClick(View v) {
			
			if(v.getId()==R.id.btnSimbora){
				Banco banco = new Banco(getActivity());
				banco.darSimbora(this.id);
				
			}
			// TODO Auto-generated method stub
			
		}
	}
	


