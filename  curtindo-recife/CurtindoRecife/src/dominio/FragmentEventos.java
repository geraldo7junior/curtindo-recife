package dominio;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
		
		private Boolean ehEvento;
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
		public String tipo;
		DialogInterface.OnClickListener dialogClick;
		public static final String ARG_SECTION_NUMBER = "section_number";
		Estabelecimento estabelecimento;
		public Boolean getEhEvento() {
			return ehEvento;
		}

		public void setEhEvento(Boolean ehEvento) {
			this.ehEvento = ehEvento;
		}

		public FragmentEventos() {
			// TODO Auto-generated constructor stub
		}
		
		public FragmentEventos(Evento evento){
			this.nomeEvento= evento.getNome();
			this.endereco=evento.getEndereco();
			this.data=evento.getData();
			this.numero=evento.getNumero();
			this.hora=evento.getHora();
			this.telefone=evento.getTelefone();
			this.descricao=evento.getDescricao();
			this.preco=evento.getPreco();
			this.id = evento.getId();
			this.idOwner=evento.getIdOwner();
			this.curtido=evento.isCurtido();
			this.tipo=evento.getTipoDeEvento();
			setEhEvento(true);
		}
		public FragmentEventos(Estabelecimento estabelecimento){
			this.estabelecimento=estabelecimento;
			this.nomeEvento= estabelecimento.getNome();
			this.endereco=estabelecimento.getEndereco();
			this.data=estabelecimento.getData();
			this.numero=estabelecimento.getNumero();
			this.hora=estabelecimento.getHoraInicio();
			this.telefone=estabelecimento.getTelefone();
			this.descricao=estabelecimento.getDescricao();
			this.preco=estabelecimento.getPreco();
			this.id = estabelecimento.getId();
			this.idOwner=estabelecimento.getIdOwner();
			this.tipo=estabelecimento.getTipo();
			setEhEvento(false);
		}
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_tela_eventos_dummy, container, false);
			//_Relacionamento
			if(!ehEvento){
			if (Usuario.getId()!=0){
					Banco banco = new Banco(getActivity());
					Usuario usuario = banco.getUsuario(Usuario.getId());
					this.curtido = banco.curtido(estabelecimento, usuario);
				}
			}
			btnSimbora = (Button) rootView.findViewById(R.id.btnSimbora);
			btnSimbora.setOnClickListener(this);
			
			txtNomeEvento = (TextView) rootView.findViewById(R.id.txtTituloEvento);
			txtDescricao = (TextView) rootView.findViewById(R.id.txtCategoria);
			txtEndereco = (TextView) rootView.findViewById(R.id.txtEndereco);
			txtHora = (TextView) rootView.findViewById(R.id.txtHora);
			txtPreco = (TextView) rootView.findViewById(R.id.txtPreco);
			imgEvento = (ImageView)rootView.findViewById(R.id.imgEvento);
			txtData = (TextView) rootView.findViewById(R.id.txtCadastroData);
			

			txtEndereco.setText(this.endereco+", "+this.numero);
			txtNomeEvento.setText(this.nomeEvento);
			if(ehEvento){
				imgEvento.setBackgroundResource(Evento.associeImagem(this.tipo));
			}
			else{
				imgEvento.setBackgroundResource(Estabelecimento.associeImagem(this.tipo));
				
			}
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
				btnSimbora.setText("Desistir");
				btnSimbora.setBackgroundColor(Color.BLUE);
				btnSimbora.setEnabled(false);
			}
			return rootView;
		}
		
		
		
		@Override
		public void onClick(View v) {
			dialogClick = new android.content.DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent intent=new Intent(getActivity(), TelaPrincipal.class);
					startActivity(intent);
					
				}
			};
			
			if(v.getId()==R.id.btnSimbora){
				
					if(Usuario.getId()==0){
						
						AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	
						// 2. Chain together various setter methods to set the dialog characteristics
						builder.setMessage("Para dar Simbora é preciso estar logado.").setPositiveButton("OK", dialogClick);
	
						// 3. Get the AlertDialog from create()
						AlertDialog dialog = builder.create();
						dialog.show();
					}else{
						
							if(getEhEvento()){
								Banco banco = new Banco(getActivity());
								banco.darSimbora(this.id);
								AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			
								// 2. Chain together various setter methods to set the dialog characteristics
								builder.setMessage("Simbora realizado com sucesso.").setTitle("Sucesso!").setPositiveButton("OK", dialogClick);
			
								// 3. Get the AlertDialog from create()
								AlertDialog dialog = builder.create();
								dialog.show();
							}else{
								Banco banco = new Banco(getActivity());
								Estabelecimento estabelecimento = banco.retornaEstabelecimento(this.id);
								Usuario usuario = banco.getUsuario(Usuario.getId());
								banco.darSimbora(estabelecimento, usuario);
								
								AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
								builder.setMessage("Simbora realizado com sucesso.").setTitle("Sucesso!").setPositiveButton("OK", dialogClick);
								AlertDialog dialog = builder.create();
								dialog.show();
								}
							
						}
				
			}
			/*if(v.getId()==R.id.btnSimbora & btnSimbora.getText().equals("Desistir")){
				Banco banco = new Banco(getActivity());
				Usuario usuario = banco.getUsuario(Usuario.getId());
				if(getEhEvento()){
					Evento evento = banco.retornaEvento(this.id);
					banco.tirarSimbora(evento, usuario);
					
					AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
					builder.setMessage("Você desistiu de ir ao evento").setTitle("Desistência").setPositiveButton("OK", dialogClick);
					AlertDialog dialog = builder.create();
					dialog.show();
				}else{
					Estabelecimento estabelecimento = banco.retornaEstabelecimento(this.id);
					banco.tirarSimbora(estabelecimento, usuario);
					
					AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
					builder.setMessage("Você desistiu de ir ao Estabelecimento").setTitle("Desistência").setPositiveButton("OK", dialogClick);
					AlertDialog dialog = builder.create();
					dialog.show();
				}
				
			}*/
			// TODO Auto-generated method stub
			
		}
	}
	


