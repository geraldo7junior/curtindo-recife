package dominio;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import bd.Banco;

import com.br.curtindorecife.R;
import com.br.curtindorecife.TelaAgenda;
import com.br.curtindorecife.TelaCadastroEvento;
import com.br.curtindorecife.TelaCategoriaEvento;
import com.br.curtindorecife.TelaLogin;
import com.br.curtindorecife.TelaMostrarPerfil;

public class FragmentEstabelecimentosPrincipal extends Fragment implements OnClickListener {
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	ImageButton btnCadastrarEstabelecimento;
	ImageButton btnAgendaEstabelecimento;
	ImageButton btnEstabelecimento1;
	ImageButton btnEstabelecimento2;
	ImageButton btnEstabelecimento3;
	ImageButton btnEstabelecimento4;
	Usuario usuario;
	int imgEstabelecimento1, imgEstabelecimento2, imgEstabelecimento3,imgEstabelecimento4;
	ImageButton btnTop10Estabelecimento;
	ImageButton btnNightEstabelecimento;
	public static final String ARG_SECTION_NUMBER = "section_number";

	public FragmentEstabelecimentosPrincipal() {
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_estabelecimentos_principal,
				container, false);
		btnCadastrarEstabelecimento = (ImageButton)rootView.findViewById(R.id.btnCadastrarEstabelecimento);
		btnCadastrarEstabelecimento.setOnClickListener(this);
		btnAgendaEstabelecimento = (ImageButton)rootView.findViewById(R.id.btnAgendaEstabelecimento);
		btnAgendaEstabelecimento.setOnClickListener(this);
		btnEstabelecimento1 = (ImageButton)rootView.findViewById(R.id.btnEstabelecimento1);
		btnEstabelecimento1.setOnClickListener(this);
		btnEstabelecimento2 = (ImageButton)rootView.findViewById(R.id.btnEstabelecimento2);
		btnEstabelecimento2.setOnClickListener(this);
		btnEstabelecimento3 = (ImageButton)rootView.findViewById(R.id.btnEstabelecimento3);
		btnEstabelecimento3.setOnClickListener(this);
		btnEstabelecimento4 = (ImageButton)rootView.findViewById(R.id.btnEstabelecimento4);
		btnEstabelecimento4.setOnClickListener(this);
		btnNightEstabelecimento=(ImageButton)rootView.findViewById(R.id.btnNightEstabelecimento);
		btnNightEstabelecimento.setOnClickListener(this);
		
		
		ArrayAdapter<CharSequence> ar = ArrayAdapter.createFromResource(getActivity(),R.array.Categorias,android.R.layout.simple_list_item_1);
		ar.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		
		/*if(Usuario.getId()!=0){*/
			Banco banco = new Banco(getActivity());
			usuario = banco.getUsuario(Usuario.getId());
			//Evento.getNomesEventosTelaPrincipal().clear();
			/*Evento.retorneListaNomesEventos("Show", "Teatro", "Esporte", ar);
			imgEstabelecimento1 = Evento.associeImagem(Evento.getNomesEventosTelaPrincipal().get(0));
			imgEstabelecimento2 = Evento.associeImagem(Evento.getNomesEventosTelaPrincipal().get(1));
			imgEstabelecimento3 = Evento.associeImagem(Evento.getNomesEventosTelaPrincipal().get(2));
			imgEstabelecimento4 = Evento.associeImagem(Evento.getNomesEventosTelaPrincipal().get(3));

			
			
			btnEstabelecimento1.setImageResource(imgEstabelecimento1);
			btnEstabelecimento2.setImageResource(imgEstabelecimento2);
			btnEstabelecimento3.setImageResource(imgEstabelecimento3);
			btnEstabelecimento4.setImageResource(imgEstabelecimento4);*/
			
			//btnLogin.setText("Perfil");
		/*}*//*else{
			Evento.getNomesEventosTelaPrincipal().clear();
			Evento.retorneListaNomesEventos("Show", "Teatro", "Esporte", ar);
			imgEvento1 = Evento.associeImagem(Evento.getNomesEventosTelaPrincipal().get(0));
			imgEvento2 = Evento.associeImagem(Evento.getNomesEventosTelaPrincipal().get(1));
			imgEvento3 = Evento.associeImagem(Evento.getNomesEventosTelaPrincipal().get(2));
			imgEvento4 = Evento.associeImagem(Evento.getNomesEventosTelaPrincipal().get(3));
			imgEvento5 = Evento.associeImagem(Evento.getNomesEventosTelaPrincipal().get(4));
			imgEvento6 = Evento.associeImagem(Evento.btnEstabelecimentoventosTelaPrincipal().get(5));
			
			
			btnEvento1.setImageResource(imgEvento1);
			btnEvento2.setImageResource(imgEvento2);
			btnEvento3.setImageResource(imgEvento3);
			btnEvento4.setImageResource(imgEvento4);
			btnEvento5.setImageResource(imgEvento5);
			btnEvento6.setImageResource(imgEvento6);

		}*/
		return rootView;
	}	
	
		@Override
		public void onClick(View v) {
			if(v.getId() == R.id.btnLogin && Usuario.getId()==0){
				/*if(btnLogin.getText().equals("Perfil")){
					Intent intent = new Intent(getActivity(), TelaPerfilUsuario.class);
				}else{*/
					Intent intent = new Intent(getActivity(), TelaLogin.class);
					startActivity(intent);
				//}
			}if(v.getId() == R.id.btnLogin && Usuario.getId()!=0){
				//Usuario.setId(0);
				Intent intent = new Intent(getActivity(), TelaMostrarPerfil.class);
				startActivity(intent);
				
			}
		
		if(v.getId()== R.id.btnNightEstabelecimento){
				Evento.setMeusEventosClickados(false);
				Evento.setAtual("Night");
				Intent intent = new Intent(getActivity(),TelaCategoriaEvento.class);
				startActivity(intent);
		}
		if(v.getId() == R.id.btnAgendaEstabelecimento){
			Evento.setMeusEventosClickados(false);
			Evento.setAtual("Agenda");
			Intent intent = new Intent(getActivity(), TelaAgenda.class);
			startActivity(intent);
		}
		
		if(v.getId() == R.id.btnCadastrarEstabelecimento){
			if(Usuario.getId()!=0){
				Evento.setMeusEventosClickados(false);
				Intent intent = new Intent(getActivity(),TelaCadastroEvento.class);
				startActivity(intent);
			}else{
				DialogInterface.OnClickListener dialogClick = new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(getActivity(),TelaLogin.class);
						startActivity(intent);
						
					}
				};
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setMessage("Para criar um evento é necessário estar cadastrado").setTitle("Não cadastrado").setPositiveButton("OK", dialogClick);

				AlertDialog dialog = builder.create();



				dialog.show();
			}
		}
		if(v.getId()== R.id.btnTop10Estabelecimento){
			Evento.setMeusEventosClickados(false);
			Evento.setAtual("Top10");
			Intent intent = new Intent(getActivity(),TelaCategoriaEvento.class);
			startActivity(intent);
		}
		direcionarBtn(v.getId());
		
		
		
	}
		
	public void direcionarBtn(int idBtn){
		boolean idCorreto = false;
		/*if(Usuario.getId() == 0){*/
			if(idBtn==R.id.btnEstabelecimento1){
				Estabelecimento.setAtual("Bar");
				idCorreto = true;
				
			}else if(idBtn==R.id.btnEstabelecimento2){
				Estabelecimento.setAtual("Restaurante");
				idCorreto = true;
				
			}else if (idBtn==R.id.btnEstabelecimento3){
				Estabelecimento.setAtual("Casa de show");
				idCorreto = true;
			}
			
			else if(idBtn==R.id.btnEstabelecimento4){
				Estabelecimento.setAtual("Boate");
				idCorreto = true;
			}
				
			
		/*}else{
			Banco banco = new Banco(getActivity());
			Usuario usuario;
			usuario = banco.getUsuario(Usuario.getId());
		if(idBtn==R.id.btnEstabelecimento1){
			Evento.setAtual(usuario.getEventoFavorito1());
			idCorreto = true;
			
		}else if(idBtn==R.id.btnEstabelecimento2){
			Evento.setAtual(usuario.getEventoFavorito2());
			idCorreto = true;
			
		}else if (idBtn==R.id.btnEvento3){
			Evento.setAtual(usuario.getEventoFavorito3());
			idCorreto = true;
		}
		if(idBtn==R.id.btnEvento4){
			Evento.setAtual(Evento.getNomesEventosTelaPrincipal().get(3));
			idCorreto = true;
			
		}else if(idBtn==R.id.btnEvento5){
			Evento.setAtual(Evento.getNomesEventosTelaPrincipal().get(4));
			idCorreto = true;
			
		}else if (idBtn==R.id.btnEvento6){
			Evento.setAtual(Evento.getNomesEventosTelaPrincipal().get(5));
			idCorreto = true;
			}
			
		}*/
		if(idCorreto){
			Evento.setAtual("");
			Evento.setMeusEventosClickados(false);
			Intent intent = new Intent(getActivity(), TelaCategoriaEvento.class);
			startActivity(intent);
		}
	}
		
		
	
	
}