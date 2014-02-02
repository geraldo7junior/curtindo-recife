package dominio;



import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import bd.Banco;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public class Utility {
	public static ArrayList<String> nameOfEvent = new ArrayList<String>();
	public static ArrayList<String> startDates = new ArrayList<String>();
	public static ArrayList<String> endDates = new ArrayList<String>();
	public static ArrayList<String> descriptions = new ArrayList<String>();
	public static ArrayList<Evento> listaEventos=new ArrayList<Evento>();
	
	public static ArrayList<String> readCalendarEvent(Context context) {
		DateFormat df=new SimpleDateFormat("dd/mm/yyyy");
		nameOfEvent.clear();
		startDates.clear();
		endDates.clear();
		descriptions.clear();
		Banco banco=new Banco(context);
		listaEventos=banco.ListarEvento();
		
		for (int i = 0; i < listaEventos.size(); i++) {

			nameOfEvent.add(listaEventos.get(i).getNome());
			startDates.add(listaEventos.get(i).getData());
			descriptions.add(listaEventos.get(i).getDescricao());
		}

		
		return nameOfEvent;
	}

	public static String getDate(long milliSeconds) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/mm/yyyy");
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(milliSeconds);
		return formatter.format(calendar.getTime());
	}
}
