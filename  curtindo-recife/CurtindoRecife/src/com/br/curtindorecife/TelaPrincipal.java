package com.br.curtindorecife;

import bd.Banco;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import dominio.*;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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
	public static int numEventos=0;
	
	SectionsPagerAdapter mSectionsPagerAdapter;
	
	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Evento.getListaEventos().clear();
		numEventos=0;
		CriarBanco();
		checarBD();
		darSimbora();
		if(Usuario.getId()!=0){
			getMeusEventos(Usuario.getId());
		}
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
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		return true;
	}
	
	String NomeBanco = "CurtindoRecifeDB";
	SQLiteDatabase BancoDados = null;
	
	@SuppressWarnings("deprecation")
	public void CriarBanco() {
		try {
		BancoDados = openOrCreateDatabase(NomeBanco, MODE_WORLD_READABLE, null);
		String sql = "CREATE TABLE IF NOT EXISTS tabelaUsuarios (_id INTEGER PRIMARY KEY, nome TEXT, dataNascimento TEXT, email TEXT, senha TEXT, sexo TEXT, eventoFavorito1 TEXT, eventoFavorito2 TEXT, eventoFavorito3 TEXT)";
		BancoDados.execSQL(sql);
		Cadastrar("Admin", "12/12/1903", "admin@curtindorecife.br", "ADMIN", "indefinido", "", "", "");
		String sqlEvento = "CREATE TABLE IF NOT EXISTS tabelaEventos (_id INTEGER PRIMARY KEY, nome TEXT, endereco TEXT, numero TEXT, preco TEXT,data TEXT, hora TEXT, telefone TEXT, descricao TEXT, tipo TEXT, idOwner INTEGER, simboras INTEGER, idImagem INTEGER)";
		BancoDados.execSQL(sqlEvento);
		String sqlMeusEventos = "CREATE TABLE IF NOT EXISTS tabelaMeusEventos (_id INTEGER PRIMARY KEY, idUsuario INTEGER, idEvento INTEGER)";
		BancoDados.execSQL(sqlMeusEventos);
		} catch (Exception erro) {
			System.out.println(erro);
		}finally{
			BancoDados.close();
		}
	}
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
	
	
	public void darSimbora() {
		try {
			BancoDados = openOrCreateDatabase(NomeBanco, MODE_WORLD_READABLE, null);
			String sqlSimboras = "SELECT simboras FROM tabelaEventos WHERE _id LIKE '"+11+"'";
			cursor = BancoDados.rawQuery(sqlSimboras,null);
			cursor.moveToFirst();
			int oldSimbora = cursor.getInt(cursor.getColumnIndex("simboras"));
			int newSimbora = oldSimbora + 1;
			String sql = "UPDATE tabelaEventos SET simboras = '"+newSimbora+"' WHERE _id LIKE '"+11+"'";

			BancoDados.execSQL(sql);
		} catch (Exception erro) {
			// TODO: handle exception
			System.out.println(erro);
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
				args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
				fragment.setArguments(args);
			}
			if(position==1){
				fragment=new SectionFragment();
			}
			
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return ("Menu").toUpperCase(l);
			case 1:
				return ("Meus Eventos").toUpperCase(l);
			}
			return null;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment implements OnClickListener {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		Button btnLogin;
		Button btnCadastarEvento;
		ImageButton btnAgenda;
		ImageButton btnShows;
		ImageButton btnTeatro;
		
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment() {
		}
		
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main_dummy,
					container, false);
			
			btnCadastarEvento = (Button)rootView.findViewById(R.id.btnCadastrarEvento);
			btnCadastarEvento.setOnClickListener(this);
			btnLogin = (Button)rootView.findViewById(R.id.btnLogin);
			btnLogin.setOnClickListener(this);
			btnAgenda = (ImageButton)rootView.findViewById(R.id.btnAgenda);
			btnAgenda.setOnClickListener(this);
			btnShows = (ImageButton)rootView.findViewById(R.id.btnShows);
			btnShows.setOnClickListener(this);
			btnTeatro = (ImageButton)rootView.findViewById(R.id.btnTeatro);
			btnTeatro.setOnClickListener(this);
			return rootView;
		}	
		
			@Override
			public void onClick(View v) {
				if(v.getId() == R.id.btnLogin){
					/*if(btnLogin.getText().equals("Perfil")){
						Intent intent = new Intent(getActivity(), TelaPerfilUsuario.class);
					}else{*/
						Intent intent = new Intent(getActivity(), TelaLogin.class);
						startActivity(intent);
					//}
				}
			
			if(v.getId() == R.id.btnAgenda){
				Intent intent = new Intent(getActivity(), TelaAgenda.class);
				startActivity(intent);
			}
			
			if(v.getId() == R.id.btnShows){
				Evento.setAtual("Show");
				Intent intent = new Intent(getActivity(), TelaCategoriaEvento.class);
				startActivity(intent);
			}
			
			if(v.getId() == R.id.btnCadastrarEvento){
				Intent intent = new Intent(getActivity(),TelaCadastroEvento.class);
				startActivity(intent);
			}
			
			if(v.getId() == R.id.btnTeatro){
				Evento.setAtual("Show");
				Intent intent = new Intent(getActivity(),TelaEventos.class);
				startActivity(intent);
			}
			
		}
		
	}
	
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
	        ArrayAdapter ad = new CustomAdapter(this.getActivity(), R.layout.item, EventoList);
	        ListView lv = (ListView) rootView.findViewById(R.id.listView1);
	        lv.setAdapter(ad);
	        lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					Intent intent = new Intent(getActivity(),TelaEventos.class);
					int position=arg2;
					System.out.println(position);
					intent.putExtra("position", position);
					startActivity(intent);
				}
				
			});
	        return rootView;
	    }
	 
	    private List createEventos(){
	        List p = Evento.getListaEventos();
	        return p;
	    }
	    
		
	}
	
	public Integer getMeusEventos(int idUsuario){
		String NomeBanco = "CurtindoRecifeDB";
		SQLiteDatabase BancoDados = null;
		Cursor cursor;
		try {
			BancoDados = openOrCreateDatabase(NomeBanco, MODE_WORLD_READABLE, null);
			String sql = "SELECT * FROM tabelaEventos WHERE idOwner LIKE '"+idUsuario+"' ";
			cursor = BancoDados.rawQuery(sql, null);
			numEventos=(cursor.getCount());
			cursor.moveToFirst();
			for(int i=0;i<cursor.getCount();i++){			
				Evento.addListaEventos(new Evento(cursor.getString(cursor.getColumnIndex("nome")),cursor.getString(cursor.getColumnIndex("data")),cursor.getString(cursor.getColumnIndex("hora")),cursor.getInt(cursor.getColumnIndex("idImagem")),cursor.getInt(cursor.getColumnIndex("_id")),cursor.getInt(cursor.getColumnIndex("idOwner")),cursor.getString(cursor.getColumnIndex("descricao")),cursor.getString(cursor.getColumnIndex("tipo")),cursor.getString(cursor.getColumnIndex("telefone")),cursor.getInt(cursor.getColumnIndex("simboras")),cursor.getString(cursor.getColumnIndex("preco")),cursor.getString(cursor.getColumnIndex("numero")),cursor.getString(cursor.getColumnIndex("endereco"))));
				if(i!=cursor.getCount()-1){
					cursor.moveToNext();
				}
				
			}
			return cursor.getInt(cursor.getPosition());
		} catch (Exception erro) {
			System.out.println(erro);
			return null;
			// retorna 0 caso o email não seja encontrado ou algum erro no banco.
		}finally{
			BancoDados.close();
		}	
	}
	

}
