package dominio;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
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
import com.br.curtindorecife.TelaPrincipal;

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
		public int idOwner;
		public boolean curtido;
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
			this.idOwner=evento.getIdOwner();
			this.curtido=evento.isCurtido();
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
			txtDescricao = (TextView) rootView.findViewById(R.id.txtCategoria);
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
			
			if(this.idOwner==Usuario.getId()){
				btnSimbora.setText("Evento Criado");
				btnSimbora.setBackgroundColor(Color.BLUE);
				btnSimbora.setEnabled(false);
			}
			if(this.curtido==true){
				btnSimbora.setText("Evento Curtido");
				btnSimbora.setBackgroundColor(Color.BLUE);
				btnSimbora.setEnabled(false);
			}
			return rootView;
		}
		
		@Override
		public void onClick(View v) {
			
			if(v.getId()==R.id.btnSimbora){
				if(Usuario.getId()==0){
					AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

					// 2. Chain together various setter methods to set the dialog characteristics
					builder.setMessage("Para dar Simbora é preciso estar logado.");

					// 3. Get the AlertDialog from create()
					AlertDialog dialog = builder.create();
					dialog.show();
				}else{
					Banco banco = new Banco(getActivity());
					banco.darSimbora(this.id);
					AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

					// 2. Chain together various setter methods to set the dialog characteristics
					builder.setMessage("Simbora realizado com sucesso.").setTitle("Sucesso!");

					// 3. Get the AlertDialog from create()
					AlertDialog dialog = builder.create();
					dialog.show();
					
					Intent intent=new Intent(getActivity(), TelaPrincipal.class);
					startActivity(intent);
					}
				
			}
			// TODO Auto-generated method stub
			
		}
	}
	


