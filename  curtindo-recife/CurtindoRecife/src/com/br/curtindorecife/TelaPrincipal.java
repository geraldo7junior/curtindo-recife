package com.br.curtindorecife;

import bd.Banco;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.br.curtindorecife.R.id;

import dominio.*;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class TelaPrincipal extends FragmentActivity implements
		ActionBar.TabListener {
		
		
	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	//public static int numEventos=0;
	
	private static int numeroTela = 1;// (0,1,2)///
	
	SectionsPagerAdapter mSectionsPagerAdapter;
	
	
	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	
	public TelaPrincipal(){
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Banco banco = new Banco(this);
		banco.criarBanco();
		System.out.println(banco.usuarioCadastrado("demismg72@gmail.com"));
		Cadastrar("Admin", "12/12/1903", "admin@curtindorecife.br", "ADMIN", "indefinido", "Show", "Teatro", "Esporte");
		Evento.getListaEventos().clear();
		//numEventos=0;
		Evento.getMeusEventos().clear();
		/*CriarBanco();*/
		checarBD();
		CadastrarEstabelecimento();
		if(Usuario.getId()!=0){
			banco.setMeusEventos(Usuario.getId());
		}
		Evento.setAtual("");
		
		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		
		
		
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		
		
		
		
		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		
		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
		mViewPager.setCurrentItem(numeroTela);
		setNumeroTela(1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if(Usuario.getId()==0){
			MenuInflater menuInflater=getMenuInflater();
			menuInflater.inflate(R.menu.main, menu);
			
			
		}else{
			MenuInflater menuInflater=getMenuInflater();
			menuInflater.inflate(R.menu.main_logado, menu);
		}
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
	//Realizar um case pelo “Id” dos itens e logo em seguida mostrar uma mensagem ao usuário
	  switch (item.getItemId())
	  {     
	   case id.Cadastrar:
		   Intent intent = new Intent(this, TelaCadastroUsuario.class);
		   startActivity(intent);
	   break;
	   
	   case id.sairPontinhos:
		   Usuario.setId(0);
		   Intent intentSair = new Intent(this, TelaLogin.class);
		   startActivity(intentSair);
		   break;
	  }
	   //Retornar a classe pai
	   return super.onOptionsItemSelected(item);
	}
	String NomeBanco = "CurtindoRecifeDB";
	SQLiteDatabase BancoDados = null;
	
	/*@SuppressWarnings("deprecation")
	public void CriarBanco() {
		try {			
		BancoDados = openOrCreateDatabase(NomeBanco, MODE_WORLD_READABLE, null);
		String sql = "CREATE TABLE IF NOT EXISTS tabelaUsuarios (_id INTEGER PRIMARY KEY, nome TEXT, dataNascimento TEXT, email TEXT, senha TEXT, sexo TEXT, eventoFavorito1 TEXT, eventoFavorito2 TEXT, eventoFavorito3 TEXT, mascates INTEGER, ranking INTEGER)";
		BancoDados.execSQL(sql);
		Cadastrar("Admin", "12/12/1903", "admin@curtindorecife.br", "ADMIN", "indefinido", "", "", "");
		String sqlEvento = "CREATE TABLE IF NOT EXISTS tabelaEventos (_id INTEGER PRIMARY KEY, nome TEXT, endereco TEXT, numero TEXT, preco TEXT,data TEXT, hora TEXT, telefone TEXT, descricao TEXT, tipo TEXT, idOwner INTEGER, simboras INTEGER, idImagem INTEGER, prioridade INTEGER)";
		BancoDados.execSQL(sqlEvento);
		String sqlMeusEventos = "CREATE TABLE IF NOT EXISTS tabelaMeusEventos (_id INTEGER PRIMARY KEY, idUsuario INTEGER, idEvento INTEGER)";
		BancoDados.execSQL(sqlMeusEventos);
		String sqlEstabelecimentos = "CREATE TABLE IF NOT EXISTS tabelaEstabelecimentos (_id INTEGER PRIMARY KEY, nome TEXT, endereco TEXT, numero TEXT, preco TEXT,data TEXT, horaInicio TEXT, horaTermino TEXT, telefone TEXT, descricao TEXT, tipo TEXT, idOwner INTEGER, simboras INTEGER, idImagem INTEGER, prioridade INTEGER, ranking INTEGER)";
		BancoDados.execSQL(sqlEstabelecimentos);
		} catch (Exception erro) {
			System.out.println(erro);
		}finally{
			BancoDados.close();
		}
	}*/
	Cursor cursor;
	
	@SuppressWarnings("deprecation")
	public boolean checarBD(){
		try {
			BancoDados = openOrCreateDatabase(NomeBanco, MODE_WORLD_READABLE,null);
			String sql = "SELECT * FROM tabelaUsuarios";
			cursor = BancoDados.rawQuery(sql, null);
			if (cursor.getCount() != 0){
				System.out.println("true");
				return true;
			}else{
				System.out.println("false");
				return false;
			}
		} catch (Exception erro) {
			System.out.println(erro);
			return false;
		}finally{
			BancoDados.close();
			
		}
	}
	
	@SuppressWarnings("deprecation")
	public void Cadastrar(String nome, String dataDeNascimento, String email, String senha, String sexo, String eventoFavorito1, String eventoFavorito2, String eventoFavorito3){
		//Cadastra ADMIN
		//o formato da data é (YYYY-MM-DD)
		String NomeBanco = "CurtindoRecifeDB";
		SQLiteDatabase BancoDados = null;
		Cursor cursor;
		try {
				BancoDados = openOrCreateDatabase(NomeBanco, MODE_WORLD_READABLE, null);
				String sql = "SELECT _id FROM tabelaUsuarios WHERE nome LIKE 'Admin'";
				cursor = BancoDados.rawQuery(sql, null);
				cursor.moveToFirst();
				System.out.println(cursor.getInt(cursor.getPosition()));
				
		} catch (Exception erro) {
				String insert = "INSERT INTO tabelaUsuarios (nome, dataNascimento, email, senha, sexo, eventoFavorito1, eventoFavorito2, eventoFavorito3) VALUES ('"+nome+"','"+dataDeNascimento+"','"+email+"','"+senha+"','"+sexo+"','"+eventoFavorito1+"','"+eventoFavorito2+"','"+eventoFavorito3+"')";
				BancoDados.execSQL(insert);
				System.out.println(erro);
				// retorna 0 caso o email não seja encontrado ou algum erro no banco.
		}finally{
				BancoDados.close();
		}
	}
	
	
	
	
	public void CadastrarEstabelecimento(){
		String NomeBanco = "CurtindoRecifeDB";
		SQLiteDatabase BancoDados = null;
		Cursor cursor;
		try {
				BancoDados = openOrCreateDatabase(NomeBanco, MODE_WORLD_READABLE, null);
				String sql = "SELECT _id FROM tabelaEstabelecimentos WHERE nome LIKE 'Bar da curva'";
				cursor = BancoDados.rawQuery(sql, null);
				cursor.moveToFirst();
				System.out.println(cursor.getInt(cursor.getPosition()));
				
		} catch (Exception erro) {
				Banco banco= new Banco(this);
				
				banco.inserirEstabelecimento(1, "Bar da Curva", "R. D. Manuel de Medeiros", "S/N", "", "02/02/2014", "09:00", "20:00", "(81)2323-4545", "é um bar bom", "Bar", 1231323, 4);
				banco.inserirEstabelecimento(1, "RU", "R. D. Manuel de Medeiros", "S/N", "", "02/02/2014", "11:00", "20:00", "(81)2323-4545", "é um RU bom", "Restaurante", 1231323, 4);
				banco.inserirEstabelecimento(1, "Senzala", "R. D. Manuel de Medeiros", "S/N", "", "02/02/2014", "18:00", "23:00", "(81)2323-4545", "é uma casa de shows boa", "Casa de Show", 1231323, 4);
				banco.inserirEstabelecimento(1, "LoL Boat", "R. D. Manuel de Medeiros", "S/N", "", "02/02/2014", "09:00", "20:00", "(81)2323-4545", "é uma boate boa", "Boate", 1231323, 4);
				
				System.out.println(erro);
				// retorna 0 caso o email não seja encontrado ou algum erro no banco.
		}finally{
				BancoDados.close();
		}
	}
	
	
	

	
	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	public static int getNumeroTela() {
		return numeroTela;
	}

	public static void setNumeroTela(int numeroTela) {
		TelaPrincipal.numeroTela = numeroTela;
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			if(position==0){
				fragment=new FragmentEstabelecimentosPrincipal();
				
			}
			if(position==1){
				args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
				fragment.setArguments(args);
				
			}
			if(position==2){
				fragment=new SectionFragment();
			}
			
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return ("Locais").toUpperCase(l);
			case 1:
				return ("Menu").toUpperCase(l);
			case 2:
				return ("Meus Eventos").toUpperCase(l);
			}
			return null;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	///
	/////
	//FRAGMENTO DA TELA PRINCIPAL
	//////
	public static class DummySectionFragment extends Fragment implements OnClickListener {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		ImageButton btnLogin;
		ImageButton btnCadastarEvento;
		ImageButton btnAgenda;
		ImageButton btnEvento1;
		ImageButton btnEvento2;
		ImageButton btnEvento3;
		ImageButton btnEvento4;
		ImageButton btnEvento5;
		ImageButton btnEvento6;
		Usuario usuario;
		int imgEvento1, imgEvento2, imgEvento3,imgEvento4, imgEvento5, imgEvento6;
		TextView txtEvento1, txtEvento2, txtEvento3;
		ImageButton btnTop10;
		ImageButton btnNight;
		ImageButton btnRolandoAgora;
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment() {
		}
		
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main_dummy,
					container, false);
			btnCadastarEvento = (ImageButton)rootView.findViewById(R.id.btnCadastrarEvento);
			btnCadastarEvento.setOnClickListener(this);
			btnLogin = (ImageButton)rootView.findViewById(R.id.btnLogin);
			btnLogin.setOnClickListener(this);
			btnAgenda = (ImageButton)rootView.findViewById(R.id.btnAgenda);
			btnAgenda.setOnClickListener(this);
			btnEvento1 = (ImageButton)rootView.findViewById(R.id.btnEvento1);
			btnEvento1.setOnClickListener(this);
			btnEvento2 = (ImageButton)rootView.findViewById(R.id.btnEvento2);
			btnEvento2.setOnClickListener(this);
			btnEvento3 = (ImageButton)rootView.findViewById(R.id.btnEvento3);
			btnEvento3.setOnClickListener(this);
			btnEvento4 = (ImageButton)rootView.findViewById(R.id.btnEvento4);
			btnEvento4.setOnClickListener(this);
			btnEvento5 = (ImageButton)rootView.findViewById(R.id.btnEvento5);
			btnEvento5.setOnClickListener(this);
			btnEvento6 = (ImageButton)rootView.findViewById(R.id.btnEvento6);
			btnEvento6.setOnClickListener(this);
			btnTop10=(ImageButton)rootView.findViewById(R.id.btnTop10);
			btnTop10.setOnClickListener(this);
			btnNight=(ImageButton)rootView.findViewById(R.id.btnNight);
			btnNight.setOnClickListener(this);
			btnRolandoAgora = (ImageButton)rootView.findViewById(R.id.btnRolandoAgora);
			btnRolandoAgora.setOnClickListener(this);
			
			
			ArrayAdapter<CharSequence> ar = ArrayAdapter.createFromResource(getActivity(),R.array.Categorias,android.R.layout.simple_list_item_1);
			ar.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
			
			if(Usuario.getId()!=0){
				Banco banco = new Banco(getActivity());
				usuario = banco.getUsuario(Usuario.getId());
				Evento.getNomesEventosTelaPrincipal().clear();
				Evento.retorneListaNomesEventos(usuario.getEventoFavorito1(), usuario.getEventoFavorito2(), usuario.getEventoFavorito3(), ar);
				imgEvento1 = Evento.associeImagem(Evento.getNomesEventosTelaPrincipal().get(0));
				imgEvento2 = Evento.associeImagem(Evento.getNomesEventosTelaPrincipal().get(1));
				imgEvento3 = Evento.associeImagem(Evento.getNomesEventosTelaPrincipal().get(2));
				imgEvento4 = Evento.associeImagem(Evento.getNomesEventosTelaPrincipal().get(3));
				imgEvento5 = Evento.associeImagem(Evento.getNomesEventosTelaPrincipal().get(4));
				imgEvento6 = Evento.associeImagem(Evento.getNomesEventosTelaPrincipal().get(5));
				
				
				btnEvento1.setImageResource(imgEvento1);
				btnEvento2.setImageResource(imgEvento2);
				btnEvento3.setImageResource(imgEvento3);
				btnEvento4.setImageResource(imgEvento4);
				btnEvento5.setImageResource(imgEvento5);
				btnEvento6.setImageResource(imgEvento6);
				
				//btnLogin.setText("Perfil");
			}else{
				Evento.getNomesEventosTelaPrincipal().clear();
				Evento.retorneListaNomesEventos("Show", "Teatro", "Esporte", ar);
				imgEvento1 = Evento.associeImagem(Evento.getNomesEventosTelaPrincipal().get(0));
				imgEvento2 = Evento.associeImagem(Evento.getNomesEventosTelaPrincipal().get(1));
				imgEvento3 = Evento.associeImagem(Evento.getNomesEventosTelaPrincipal().get(2));
				imgEvento4 = Evento.associeImagem(Evento.getNomesEventosTelaPrincipal().get(3));
				imgEvento5 = Evento.associeImagem(Evento.getNomesEventosTelaPrincipal().get(4));
				imgEvento6 = Evento.associeImagem(Evento.getNomesEventosTelaPrincipal().get(5));
				
				
				btnEvento1.setImageResource(imgEvento1);
				btnEvento2.setImageResource(imgEvento2);
				btnEvento3.setImageResource(imgEvento3);
				btnEvento4.setImageResource(imgEvento4);
				btnEvento5.setImageResource(imgEvento5);
				btnEvento6.setImageResource(imgEvento6);

			}
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
			
			if(v.getId()== R.id.btnNight){
					Evento.setMeusEventosClickados(false);
					Evento.setAtual("Night");
					Intent intent = new Intent(getActivity(),TelaCategoriaEvento.class);
					startActivity(intent);
			}
			if(v.getId() == R.id.btnRolandoAgora){
				Evento.setMeusEventosClickados(false);
				Evento.setAtual("Rolando Agora");
				Intent intent = new Intent(getActivity(),TelaCategoriaEvento.class);
				startActivity(intent);
				
			}
			if(v.getId() == R.id.btnAgenda){
				Evento.setMeusEventosClickados(false);
				Evento.setAtual("Agenda");
				Intent intent = new Intent(getActivity(), CalendarView.class);
				startActivity(intent);
			}
			
			if(v.getId() == R.id.btnCadastrarEvento){
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
			if(v.getId()== R.id.btnTop10){
				Evento.setMeusEventosClickados(false);
				Evento.setAtual("Top10");
				Evento.setTipoEventoTop10("Todos");
				Intent intent = new Intent(getActivity(),TelaCategoriaEvento.class);
				startActivity(intent);
			}
			direcionarBtn(v.getId());
			
			
			
		}
			
		public void direcionarBtn(int idBtn){
			boolean idCorreto = false;
			if(Usuario.getId() == 0){
				if(idBtn==R.id.btnEvento1){
					Evento.setAtual("Show");
					idCorreto = true;
					
				}else if(idBtn==R.id.btnEvento2){
					Evento.setAtual("Teatro");
					idCorreto = true;
					
				}else if (idBtn==R.id.btnEvento3){
					Evento.setAtual("Esportes");
					idCorreto = true;
				}
				
				if(idBtn==R.id.btnEvento4){
					Evento.setAtual("Familia");
					idCorreto = true;
					
				}else if(idBtn==R.id.btnEvento5){
					Evento.setAtual("Encontro");
					idCorreto = true;
					
				}else if (idBtn==R.id.btnEvento6){
					Evento.setAtual("Palestra");
					idCorreto = true;
				}
			}else{
				Banco banco = new Banco(getActivity());
				Usuario usuario;
				usuario = banco.getUsuario(Usuario.getId());
				for(int i=0;i< Evento.getNomesEventosTelaPrincipal().size();i++){
					System.out.println(Evento.getNomesEventosTelaPrincipal().get(i));
					}
			if(idBtn==R.id.btnEvento1){
				Evento.setAtual(usuario.getEventoFavorito1());
				idCorreto = true;
				
			}else if(idBtn==R.id.btnEvento2){
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
				
			}
			if(idCorreto){
				Estabelecimento.setAtual("");
				Evento.setMeusEventosClickados(false);
				Intent intent = new Intent(getActivity(), TelaCategoriaEvento.class);
				startActivity(intent);
			}
		}
			
			
		
		
	}
	
	
	///
		/////
		//FRAGMENTO DA TELA MEUS EVENTOS
		//////
	public static class SectionFragment extends Fragment{
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		
		public static final String ARG_SECTION_NUMBER = "section_number";

		public SectionFragment() {
		}
		
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			
			View rootView = inflater.inflate(R.layout.fragment_meus_eventos,container, false);
			
			
			
			List EventoList = createEventos();
			//PRA TELA ESTABELECIMENTOS, TROCAR O CUSTOMADAPTER POR CUSTOMADAPTERESTABELECIMENTO
	        ArrayAdapter ad = new CustomAdapter(this.getActivity(), R.layout.item, EventoList);
	        ListView lv = (ListView) rootView.findViewById(R.id.listEventoData);
	        lv.setAdapter(ad);
	        lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					Intent intent = new Intent(getActivity(),TelaEventos.class);
					int position=arg2;
					Evento.setMeusEventosClickados(true);
					intent.putExtra("position", position);
					startActivity(intent);
				}
				
			});
	        return rootView;
	    }
	 
	    private List createEventos(){
	    	//PARA ESTABELECIMENTOS, A LISTA P RECEBERÁ A LSITA  MEUS ESTABELECIMENTOS
	        List p = Evento.getMeusEventos();
	        return p;
	    }
	    
		
	}

}
