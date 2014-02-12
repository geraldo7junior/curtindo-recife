package dominio;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import android.widget.ArrayAdapter;

import com.br.curtindorecife.R;

public class Evento {
	
	private static final String EVENTO_SHOW="Show";
	private static final String EVENTO_TEATRO="Teatro";
	private static final String EVENTO_ESPORTES="Esporte";
	private static final String EVENTO_OUTROS="Outros";
	private static final String EVENTO_FAMILIA="Familia";
	private static final String EVENTO_PALESTRA="Palestra";
	private static final String EVENTO_ENCONTRO="Encontro";
	private static final String EVENTO_NIGHT="Night";
	private static ArrayList<Evento> listaEventos=new ArrayList<Evento>();
	private static ArrayList<Evento> meusEventos = new ArrayList<Evento>();
	private static int idEvento;
	private static String atual;
	private static boolean meusEventosClickados=false;
	private static String dia="";
	private static ArrayList<String> nomesEventosTelaPrincipal=new ArrayList<String>();
	private static String tipoEventoTop10;
	private int ranking;
	
	public int getRanking() {
		return ranking;
	}

	public void setRanking(int ranking) {
		this.ranking = ranking;
	}

	public static String getDia() {
		return dia;
	}

	public static void setDia(String dia) {
		Evento.dia = dia;
	}
	public static ArrayList<String> getNomesEventosTelaPrincipal() {
		return nomesEventosTelaPrincipal;
	}

	public static void setNomesEventosTelaPrincipal(
			ArrayList<String> nomesEventosTelaPrincipal) {
		Evento.nomesEventosTelaPrincipal = nomesEventosTelaPrincipal;
	}

	public static ArrayList<Evento> getMeusEventos() {
		return meusEventos;
	}

	public static void setMeusEventos(ArrayList<Evento> meusEventos) {
		Evento.meusEventos.clear();
		Evento.meusEventos = meusEventos;
	}
	
	public static void addMeusEventos(Evento evento){
		Evento.meusEventos.add(evento);
	}

	public static String getEventoFamilia() {
		return EVENTO_FAMILIA;
	}

	public static String getEventoPalestra() {
		return EVENTO_PALESTRA;
	}

	public static String getEventoEncontro() {
		return EVENTO_ENCONTRO;
	}

	public static String getEventoNight() {
		return EVENTO_NIGHT;
	}

	private int id;
	private int image;
	private String nome;
	private String data;
	private String hora;
	private String descricao;
	private String tipoDeEvento;

	private int idOwner;

	private int simboras;
	private String preco;
	private String numero;
	private String endereco;
	private boolean curtido=false;
	private int prioridade;
	private int curtidas;
	private int morgadas;
	
	public int getCurtidas() {
		return curtidas;
	}

	public void setCurtidas(int curtidas) {
		this.curtidas = curtidas;
	}

	public int getMorgadas() {
		return morgadas;
	}

	public void setMorgadas(int morgadas) {
		this.morgadas = morgadas;
	}

	public int getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(int prioridade) {
		this.prioridade = prioridade;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getPreco() {
		return preco;
	}

	public void setPreco(String preco) {
		this.preco = preco;
	}

	public int getSimboras() {
		return simboras;
	}

	public void setSimboras(int simbora) {
		this.simboras = simbora;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	private String telefone;
	
	 public Evento() {
		 
	 }
	 
	public Evento(String nome, String data, String hora, int image,int idEvento, int idOwner, String descricao, String tipo, String telefone, int simboras, String preco, String numero, String endereco, boolean curtido) {
	     this.data = data;
	     this.hora = hora;
	     this.nome = nome;
	     this.image = image;
	     this.id = idEvento;
	     this.idOwner = idOwner;
	     this.telefone = telefone;
	     this.simboras = simboras;
	     this.preco = preco;
	     this.numero = numero;
	     this.setEndereco(endereco);
	     this.descricao = descricao;
	     this.curtido=curtido;
	     this.tipoDeEvento=tipo;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int idEvento) {
		this.id = idEvento;
	}
	public int getIdOwner() {
		return idOwner;
	}
	public void setIdOwner(int idOwner) {
		this.idOwner = idOwner;
	}
	public static int getIdEvento() {
		return idEvento;
	}
	public static void setIdEvento(int idEvento) {
		Evento.idEvento = idEvento;
	}
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getHora() {
		return hora;
	}
	public void setHora(String hora) {
		this.hora = hora;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getTipoDeEvento() {
		return tipoDeEvento;
	}
	public void setTipoDeEvento(String tipoDeEvento) {
		this.tipoDeEvento = tipoDeEvento;
	}
	public static String getEventoShow() {
		return EVENTO_SHOW;
	}
	public static String getEventoTeatro() {
		return EVENTO_TEATRO;
	}
	public static String getEventoEsportes() {
		return EVENTO_ESPORTES;
	}
	public static String getEventoOutros() {
		return EVENTO_OUTROS;
	}
	public int getImage() {
		return image;
	}
	public void setImage(int image) {
		this.image = image;
	}
	
	
	public static int associeImagem(String nomeEvento){
		int imagem=R.drawable.logo_recife;
		
		if(nomeEvento.equals(EVENTO_SHOW)){
			imagem=R.drawable.imgshow;
		}
		
		if(nomeEvento.equals(EVENTO_ESPORTES)){
			imagem=R.drawable.imgesportes;
		}
		if(nomeEvento.equals(EVENTO_TEATRO)){
			imagem=R.drawable.imgteatro;
		}
		if(nomeEvento.equals(EVENTO_FAMILIA)){
			imagem=R.drawable.imgfamilia;
		}
		if(nomeEvento.equals(EVENTO_PALESTRA)){
			imagem=R.drawable.imgpalestra;
		}
		if(nomeEvento.equals(EVENTO_ENCONTRO)){
			imagem=R.drawable.imgencontro;
		}
		if(nomeEvento.equals(EVENTO_NIGHT)){
			imagem=R.drawable.imgnight;
		}
		
		
		return imagem;
	}
	
	public static int associeImagemPerfil(String nomeEvento){
		int imagem=R.drawable.logo_recife;
		
		if(nomeEvento.equals(EVENTO_SHOW)){
			imagem=R.drawable.imgshow_50x50;
		}
		if(nomeEvento.equals(EVENTO_ENCONTRO)){
			imagem=R.drawable.imgencontro_50x50;
		}
		if(nomeEvento.equals(EVENTO_PALESTRA)){
			imagem=R.drawable.imgpalestra_50x50;
		}
		if(nomeEvento.equals(EVENTO_ESPORTES)){
			imagem=R.drawable.imgesportes_50x50;
		}
		if(nomeEvento.equals(EVENTO_TEATRO)){
			imagem=R.drawable.imgteatro_50x50;
		}
		if(nomeEvento.equals(EVENTO_FAMILIA)){
			imagem=R.drawable.imgfamilia_50x50;
		}
		return imagem;
	}

	public static String getAtual() {
		return atual;
	}

	public static void setAtual(String atual) {
		Evento.atual = atual;
	}

	public static ArrayList<Evento> getListaEventos() {
		return listaEventos;
	}

	public static void addListaEventos(Evento evento) {
		Evento.listaEventos.add(evento);
	}
	public static void setListaEventos(ArrayList<Evento> listaEventos){
		Evento.listaEventos.clear();
		Evento.listaEventos=listaEventos;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	

	public static ArrayList<Evento> ranking(){
		ArrayList<Integer> listaSimboras=new ArrayList<Integer>();
		ArrayList<Evento> listaOrdenada=new ArrayList<Evento>();
		for(int k=0;k<listaEventos.size();k++){
			listaSimboras.add(listaEventos.get(k).getSimboras());
			System.out.println(listaSimboras.get(k));
		}
		Collections.sort(listaSimboras);
		Collections.reverse(listaSimboras);
		for(int i=0;i<listaSimboras.size();i++){
			if(i>9){
				break;
			}
			for(int j=0;j<listaEventos.size();j++){
				if((listaSimboras.get(i)==listaEventos.get(j).getSimboras())&&(!listaOrdenada.contains(listaEventos.get(j)))){
					 listaOrdenada.add(listaEventos.get(j));
					 System.out.println("Adicionando "+listaEventos.get(j).getNome());
					 break;
				}
			}
		}
		
		return listaOrdenada;
	}

	public boolean isCurtido() {
		return curtido;
	}

	public void setCurtido(boolean curtido) {
		this.curtido = curtido;
	}

	public static boolean isMeusEventosClickados() {
		return meusEventosClickados;
	}

	public static void setMeusEventosClickados(boolean meusEventosClickados) {
		Evento.meusEventosClickados = meusEventosClickados;
	}
	public static ArrayList<Evento> listaNight(){
		////tudo data
		Calendar calendar=Calendar.getInstance();
		Date date=new Date();
	    DateFormat formato = new SimpleDateFormat("HH:mm");  
	    String horaConvertida = formato.format(date); 
	    DateFormat formatoData=new SimpleDateFormat("dd/MM/yyyy");
	    String dataConvertida=formatoData.format(date);
	    System.out.println(horaConvertida+" Hora");
	    System.out.println(dataConvertida+" Data");
	    try {
			calendar.setTime(formatoData.parse(dataConvertida));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	    calendar.add(Calendar.DAY_OF_MONTH, 1);
	    
	    
	    Date dataAmanha= calendar.getTime();
	    String dataAmanhaConvertida=formatoData.format(dataAmanha);
	    System.out.println(dataAmanhaConvertida);
		ArrayList<Evento> listaNight = new ArrayList<Evento>();
		/////acaa aqui
		for (int i = 0; i < Evento.getListaEventos().size(); i++) {
			if(!Evento.getListaEventos().get(i).getData().equals("")){
				if(Evento.getListaEventos().get(i).getData().equals(dataConvertida) || Evento.getListaEventos().get(i).getData().equals(dataAmanhaConvertida) ){
					int hora = 99;
					if(!Evento.getListaEventos().get(i).getHora().equals("")){
					hora=Integer.parseInt(listaEventos.get(i).getHora().substring(0, 2));
					System.out.println(hora+ " Evento: "+listaEventos.get(i).getNome());
					}else{
						continue;
					}
					if(Evento.getListaEventos().get(i).getData().equals(dataConvertida) && hora>= 18){
						listaNight.add(Evento.getListaEventos().get(i));	
					}
					if(Evento.getListaEventos().get(i).getData().equals(dataAmanhaConvertida) && (hora>=0 && hora<5)){
						listaNight.add(Evento.getListaEventos().get(i));
						
					}
					
				}
			}	
			
		}
		return listaNight;
		
	}
	
	public static ArrayList<Evento> listaRolandoAgora(){
	
		Calendar calendar=Calendar.getInstance();
		Date date=new Date();
	    DateFormat formato = new SimpleDateFormat("HH:mm");  
	    String horaConvertida = formato.format(date); 
	    DateFormat formatoData=new SimpleDateFormat("dd/MM/yyyy");
	    String dataConvertida=formatoData.format(date);
	    System.out.println(horaConvertida+" Hora");
	    System.out.println(dataConvertida+" Data");
	    try {
			calendar.setTime(formatoData.parse(dataConvertida));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		ArrayList<Evento> listaRolandoAGora = new ArrayList<Evento>();
	
		for (int i = 0; i < Evento.getListaEventos().size(); i++) {
			if(!Evento.getListaEventos().get(i).getData().equals("") && Evento.getListaEventos().get(i).getData().equals(dataConvertida) ){
				int hora = 99;
				int minuto = 99;
				int minutoConvertido = 99;
				int horaConvertida2 = 99;
				if(!Evento.getListaEventos().get(i).getHora().equals("")){
					hora=Integer.parseInt(listaEventos.get(i).getHora().substring(0, 2));
					minuto = Integer.parseInt(listaEventos.get(i).getHora().substring(3,5));
					System.out.println(hora+ " Evento: "+listaEventos.get(i).getNome());
					horaConvertida2 = Integer.parseInt(horaConvertida.substring(0,2));
					minutoConvertido = Integer.parseInt(horaConvertida.substring(3,5));
				}else{
					continue;
				}	
				System.out.println("Hora evento: "+hora+" Hora Convertida: "+horaConvertida2);
				if((hora < horaConvertida2) || (hora == horaConvertida2 && minuto < minutoConvertido)){
					System.out.println("Hora evento: "+hora+" Hora Convertida: "+horaConvertida2);
					listaRolandoAGora.add(Evento.getListaEventos().get(i));
				}	
				
			}	
			
		}
		return listaRolandoAGora;
		
	}
	
	public static void retorneListaNomesEventos(String eventoFavorito1,String eventoFavorito2,String eventoFavorito3, ArrayAdapter<CharSequence> ar){
		nomesEventosTelaPrincipal.add(eventoFavorito1);
		nomesEventosTelaPrincipal.add(eventoFavorito2);
		nomesEventosTelaPrincipal.add(eventoFavorito3);
		
		for(int i=0;i< ar.getCount();i++){
			if(!nomesEventosTelaPrincipal.contains(ar.getItem(i).toString())){
				nomesEventosTelaPrincipal.add(ar.getItem(i).toString());
			}
		}
		
		
		}
	
	
	public ArrayList<Evento> listarEventos(ArrayList<Evento> listaNaoOrdenada){
		
		ArrayList<Evento> listaOrdenada = null;
		int indice = 4;
		for (Evento eve: listaNaoOrdenada){
			
			if(eve.getPrioridade()==indice){
				
				listaOrdenada.add(eve);
				listaOrdenada.remove(eve);
				
			}
			
			if(listaNaoOrdenada.get(-1) != eve){
				
				indice--;
				
			}
			
			indice--;
		}
		
		return listaOrdenada;
		
	}

	public static String getTipoEventoTop10() {
		return tipoEventoTop10;
	}

	public static void setTipoEventoTop10(String tipoEventoTop10) {
		Evento.tipoEventoTop10 = tipoEventoTop10;
	}
		
		
	}

	

	

