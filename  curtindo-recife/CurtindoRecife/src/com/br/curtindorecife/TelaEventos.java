package com.br.curtindorecife;

import java.util.ArrayList;
import java.util.Locale;

import dominio.Evento;
import dominio.FragmentEventos;
import dominio.Usuario;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class TelaEventos extends FragmentActivity {

	static int numEventos;
	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;
	Button btnSimbora;
	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tela_eventos);
		
		//Evento.getListaEventos().clear();
		if(Evento.isMeusEventosClickados()){
			numEventos=Evento.getMeusEventos().size();
		}
		else{
			getCategoriasEventos(Evento.getAtual());
			numEventos=Evento.getListaEventos().size();		
		}
		
		Intent intent=getIntent();
		int posicao=intent.getIntExtra("position", 0);
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		mViewPager.setCurrentItem(posicao);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tela_eventos, menu);
		return true;
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	String NomeBanco = "CurtindoRecifeDB";
	SQLiteDatabase BancoDados = null;
	Cursor cursor;
	
	public void darSimbora(int id) {
		try {
			BancoDados = openOrCreateDatabase(NomeBanco, MODE_WORLD_READABLE, null);
			String sqlSimboras = "SELECT simboras FROM tabelaEventos WHERE _id LIKE '"+id+"'";
			cursor = BancoDados.rawQuery(sqlSimboras,null);
			cursor.moveToFirst();
			int oldSimbora = cursor.getInt(cursor.getColumnIndex("simboras"));
			int newSimbora = oldSimbora + 1;
			String sql = "UPDATE tabelaEventos SET simboras = '"+newSimbora+"' WHERE _id LIKE '"+id+"'";

			BancoDados.execSQL(sql);
		} catch (Exception erro) {
			// TODO: handle exception
			System.out.println(erro);
		}finally{
			BancoDados.close();
		}
		
	}
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		
		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment=null;
			if(Evento.isMeusEventosClickados()){
				fragment = new FragmentEventos(Evento.getMeusEventos().get(position));
				
			}
			else{
				fragment = new FragmentEventos(Evento.getListaEventos().get(position));
			}
			Bundle args = new Bundle();
			args.putInt(FragmentEventos.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			return fragment;
		}
		
		@Override
		public int getCount() {
			// Show 3 total pages.
			return numEventos;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			if(Evento.isMeusEventosClickados()){
				return Evento.getMeusEventos().get(position).getNome();
			}
			else{
				return Evento.getListaEventos().get(position).getNome();
			}
		}
	}

	public void getCategoriasEventos(String nomeEvento){
		String NomeBanco = "CurtindoRecifeDB";
		SQLiteDatabase BancoDados = null;
		Cursor cursor;
		try {
			BancoDados = openOrCreateDatabase(NomeBanco, MODE_WORLD_READABLE, null);
			String sql = "SELECT * FROM tabelaEventos WHERE tipo LIKE '"+nomeEvento+"'";
			cursor = BancoDados.rawQuery(sql, null);
			numEventos=(cursor.getCount());
			cursor.moveToFirst();
			for(int i=0;i<cursor.getCount();i++){			
				Evento.addListaEventos(new Evento(cursor.getString(cursor.getColumnIndex("nome")),cursor.getString(cursor.getColumnIndex("data")),cursor.getString(cursor.getColumnIndex("hora")),cursor.getInt(cursor.getColumnIndex("idImagem")),cursor.getInt(cursor.getColumnIndex("_id")),cursor.getInt(cursor.getColumnIndex("idOwner")),cursor.getString(cursor.getColumnIndex("descricao")),cursor.getString(cursor.getColumnIndex("tipo")),cursor.getString(cursor.getColumnIndex("telefone")),cursor.getInt(cursor.getColumnIndex("simboras")),cursor.getString(cursor.getColumnIndex("preco")),cursor.getString(cursor.getColumnIndex("numero")),cursor.getString(cursor.getColumnIndex("endereco")), false));
				Evento.getListaEventos().get(i).setCurtido(eventoCurtido(Evento.getListaEventos().get(i)));
				if(i!=cursor.getCount()-1){
					cursor.moveToNext();
				}	
			}
			
		} catch (Exception erro) {
			System.out.println(erro);
			// retorna 0 caso o email não seja encontrado ou algum erro no banco.
		}finally{
			BancoDados.close();
		}	
	}
	
	public boolean eventoCurtido(Evento evento){
		String NomeBanco = "CurtindoRecifeDB";
		SQLiteDatabase BancoDados = null;
		Cursor cursor;
		if(Usuario.getId()!=0){
			try {
				BancoDados = openOrCreateDatabase(NomeBanco, MODE_WORLD_READABLE, null);
				String sql = "SELECT idUsuario FROM tabelaMeusEventos WHERE idEvento LIKE '"+evento.getId()+"'";
				cursor = BancoDados.rawQuery(sql, null);
				cursor.moveToFirst();
				if(cursor.getInt(cursor.getColumnIndex("idUsuario"))==Usuario.getId()){
					return true;
				}
				else{
					return false;
				}
				
			} catch (Exception erro) {
				System.out.println(erro);
				return false;
				// retorna 0 caso o email não seja encontrado ou algum erro no banco.
			}finally{
				BancoDados.close();
			}	
		}
		else{
			return false;
		}
	}
}
