package com.br.curtindorecife;

import java.util.ArrayList;
import java.util.Locale;

import dominio.Evento;
import dominio.FragmentEventos;

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
	public static ArrayList<String> nomes=new ArrayList<String>();
	public static ArrayList<String> horas=new ArrayList<String>();
	public static ArrayList<String> datas=new ArrayList<String>();
	public static ArrayList<Integer> imagens=new ArrayList<Integer>();
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
		
		nomes.clear();
		horas.clear();
		datas.clear();
		imagens.clear();
		numEventos=0;
		getCategoriasEventos(Evento.getAtual());
		Intent intent=getIntent();
		int posicao=intent.getIntExtra("position", 0);
		System.out.println(posicao+ " Posicao tela eventos");
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		
		
		
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
			Fragment fragment = new FragmentEventos(nomes.get(position), imagens.get(position), "Rua do Líbano", "35", datas.get(position), horas.get(position), "1234-1234", "É isso aí, vamo lá e vamo lá", "R$ 2,00");
			//Fragment fragment = new FragmentEventos();
			
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
			return nomes.get(position);
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
				
				nomes.add((cursor.getString(cursor.getColumnIndex("nome"))));
				datas.add((cursor.getString(cursor.getColumnIndex("data"))));
				horas.add((cursor.getString(cursor.getColumnIndex("hora"))));
				imagens.add((cursor.getInt(cursor.getColumnIndex("idImagem"))));
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
	
}
