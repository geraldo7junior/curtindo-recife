package com.br.curtindorecife;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import bd.Banco;

import com.br.curtindorecife.R.id;

import dominio.Evento;
import dominio.Mensagem;
import dominio.Usuario;
import android.os.Bundle;
import android.app.Activity;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;


public class TelaAgenda extends Activity {
	CalendarView calendarView;
	OnDateChangeListener dateClick;
	OnClickListener clickListener;
	
	public int getMaximoDiaDoMes(int mes, int ano){
		if(ano%4==0 && mes == 2){
			return 29;
		}
		if(mes==1 || mes==3 || mes==5 || mes==7 || mes==8 || mes==10 || mes==12){
			return 31;
		}
		if(mes==4 || mes==6 || mes==9 || mes==11){
			return 30;
		}
		if(mes== 2 && ano%4!=0){
			return 28;
		}
		return 0;
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tela_agenda);
		calendarView=(CalendarView) findViewById(R.id.calendarView1);
		calendarView.setHovered(false);
		DateFormat format=new SimpleDateFormat("dd/MM/yyyy");
		Date data=new Date();
	
		
		String dataHoje=format.format(data);
		System.out.println(dataHoje);
		Evento.setDia(dataHoje);
		System.out.println(Evento.getAtual());
		dateClick=new OnDateChangeListener() {
			
			@Override
			public void onSelectedDayChange(CalendarView cal, int arg1, int arg2,
					int arg3) {
				System.out.println(arg3+"/"+(arg2+1)+"/"+arg1);
				String dia, mes;
				if(arg3<10){
					dia="0"+arg3;
				}
				else{
					dia=Integer.toString(arg3);
				}
				if(arg2<10){
					mes="0"+(arg2+1);
				}
				else{
					mes=Integer.toString(arg2+1);
				}
				Calendar calendar=Calendar.getInstance();
				System.out.println(calendar.get(Calendar.DAY_OF_MONTH));
				//System.out.println(calendar.get(arg3));
				System.out.println(getMaximoDiaDoMes(arg2+1, arg1));
				String extra=(dia+"/"+mes+"/"+arg1);
				System.out.println("Dia alterado: "+extra);
				System.out.println("dia do evento.get() "+ Evento.getDia());
				if(!Evento.getDia().equals(extra)){
					Evento.setDia(extra);
					Banco banco = new Banco(TelaAgenda.this);
					banco.ListarEventoPorData(extra);
					Intent intent=new Intent(TelaAgenda.this, TelaEventos.class);
					intent.putExtra("extra", extra);
					//intent.putExtra("maximoMes", getMaximoDiaDoMes(arg2+1, arg1));
					Mensagem.dias=getMaximoDiaDoMes(arg2+1, arg1);
					startActivity(intent);
					
				}
				
				
			}
		
		};
		
		calendarView.setOnDateChangeListener(dateClick);
		//calendarView.setSelectedWeekBackgroundColor(Color.WHITE);
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

}
