package com.br.curtindorecife;

import java.util.Locale;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements
		ActionBar.TabListener, OnClickListener {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;
	static Button btnLogin;
	static Button btnCadastarEvento;
	static ImageButton btnAgenda;
	static ImageButton btnCinema;
	static ImageButton btnTeatro;
	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

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
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
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
	public static class DummySectionFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment() {
		}
		

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main_dummy,
					container, false);
			return rootView;
		}	
		
	}
	
	public void navegacao(){
		btnCadastarEvento = (Button)findViewById(R.id.btnCadastrarEvento);
		btnCadastarEvento.setOnClickListener(this);
		btnLogin = (Button)findViewById(R.id.btnLogin);
		btnLogin.setOnClickListener(this);
		btnAgenda = (ImageButton)findViewById(R.id.btnAgenda);
		btnAgenda.setOnClickListener(this);
		btnCinema = (ImageButton)findViewById(R.id.btnCinema);
		btnCinema.setOnClickListener(this);
		btnTeatro = (ImageButton)findViewById(R.id.btnTeatro);
		btnTeatro.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {if(v.getId() == R.id.btnLogin){
		Intent intent = new Intent(MainActivity.this, TelaLogin.class);
		startActivity(intent);
	}
	
	if(v.getId() == R.id.btnAgenda){
		Intent intent = new Intent(MainActivity.this, TelaAgenda.class);
		startActivity(intent);
	}
	
	if(v.getId() == R.id.btnCinema){
		Intent intent = new Intent(MainActivity.this, TelaEventos.class);
		startActivity(intent);
	}
	
	if(v.getId() == R.id.btnCadastrarEvento){
		Intent intent = new Intent(MainActivity.this,TelaCadastroEvento.class);
		startActivity(intent);
	}
	
	if(v.getId() == R.id.btnTeatro){
			CharSequence[] charSequences = new CharSequence[]{"Sempre Compartilhar"};
	        final boolean[] checados = new boolean[charSequences.length];

	        AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        builder.setTitle("Compartilhar no Facebook?");
	        builder.setMultiChoiceItems(charSequences, checados, new DialogInterface.OnMultiChoiceClickListener() {
	            public void onClick(DialogInterface arg0, int arg1, boolean arg2) {
	                checados[arg1] = arg2;
	            }
	        });

	        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
	            public void onClick(DialogInterface arg0, int arg1) {
	                StringBuilder texto = new StringBuilder("Checados: ");
	                for (boolean ch : checados) {
	                    texto.append(ch).append("; ");
	                }
	            }
	        });
	        
	        builder.setNegativeButton("Não", new DialogInterface.OnClickListener(){
	        	public void onClick(DialogInterface arg0, int arg1) {
	                StringBuilder texto = new StringBuilder("Checados: ");
	                for (boolean ch : checados) {
	                    texto.append(ch).append("; ");
	                }
	            }
	        });

	        AlertDialog alerta = builder.create();
	        alerta.show();
	        
		// TODO Auto-generated method stub
		
		Intent intent = new Intent(MainActivity.this,TelaEventos.class);
		startActivity(intent);
	}
	
	if(v.getId() == R.id.btnEsportes){
		Intent intent = new Intent(MainActivity.this,Teste2.class);
		startActivity(intent);	
	}
}

}
