package dominio;

import java.util.ArrayList;
import java.util.Collections;

import com.br.curtindorecife.R;

import android.database.Cursor;

public class Estabelecimento {
	
	public Estabelecimento(){	
	}
	
	public Estabelecimento(Cursor cursor){
		setId(cursor.getInt(cursor.getColumnIndex("_id")));
		System.out.println("id");
		setIdOwner(cursor.getInt(cursor.getColumnIndex("idOwner")));
		System.out.println("idOwner");
		setImage(cursor.getInt(cursor.getColumnIndex("idImagem")));
		setNome(cursor.getString(cursor.getColumnIndex("nome")));
		setData(cursor.getString(cursor.getColumnIndex("data_funcionamento")));
		setHoraInicio(cursor.getString(cursor.getColumnIndex("horaInicio")));
		setHoraTermino(cursor.getString(cursor.getColumnIndex("horaTermino")));
		setDescricao(cursor.getString(cursor.getColumnIndex("descricao")));
		setTipo(cursor.getString(cursor.getColumnIndex("tipo")));
		System.out.println("tipo");
		setTelefone(cursor.getString(cursor.getColumnIndex("telefone")));
		setSimboras(cursor.getInt(cursor.getColumnIndex("simboras")));
		setPreco(cursor.getString(cursor.getColumnIndex("preco")));
		setNumero(cursor.getString(cursor.getColumnIndex("numero")));
		setEndereco(cursor.getString(cursor.getColumnIndex("endereco")));
		System.out.println("endereco");
		setPrioridade(cursor.getInt(cursor.getColumnIndex("prioridade")));
		setRanking(cursor.getInt(cursor.getColumnIndex("ranking")));
		setTelefone(cursor.getString(cursor.getColumnIndex("telefone")));
	}
	
	public Estabelecimento(int idOwner,String nome, String endereco, String numero, String preco, String data, String horaInicio,String horaTermino, String telefone, String descricao, String tipo, int imagem, int prioridade, String Telefone){
		setIdOwner(idOwner);
		setNome(nome);
		setData(data);
		setHoraInicio(horaInicio);
		setHoraTermino(horaTermino);
		setDescricao(descricao);
		setTipo(tipo);
		setPreco(preco);
		setNumero(numero);
		setTelefone(Telefone);
		setEndereco(endereco);
		setPrioridade(prioridade);
		setRanking(0);
		setSimboras(0);
	}
	
	private int ranking;
	private int id;
	private int image;
	private String nome;
	private String data;
	/////Hor�rio de funcionamento///
	private String horaInicio;
	private String horaTermino;
	//---------------------------///
	private String descricao;
	private String tipo;
	private int idOwner;
	private String cnpj;
	private int simboras;
	private String preco;
	private String numero;
	private String endereco;
	private int prioridade;
	private String telefone;
	private static String atual;
	private static ArrayList<Estabelecimento> listaEstabelecimento = new ArrayList<Estabelecimento>();
	private static ArrayList<Estabelecimento> listaMeusEstabelecimentos = new ArrayList<Estabelecimento>();
	private static boolean meusEstabelecimentosClickados=false;
	
	public static ArrayList<Estabelecimento> getListaMeusEstabelecimentos() {
		return listaMeusEstabelecimentos;
	}

	public static void setListaMeusEstabelecimentos(
			ArrayList<Estabelecimento> listaMeusEstabelecimentos) {
		Estabelecimento.listaMeusEstabelecimentos = listaMeusEstabelecimentos;
	}

	public static ArrayList<Estabelecimento> getListaEstabelecimento() {
		return listaEstabelecimento;
	}

	public static void setListaEstabelecimento(
			ArrayList<Estabelecimento> listaEstabelecimento) {
		Estabelecimento.listaEstabelecimento = listaEstabelecimento;
	}

	public static String getAtual() {
		return atual;
	}

	public static void setAtual(String atual) {
		Estabelecimento.atual = atual;
	}

	public int getRanking() {
		return ranking;
	}

	public void setRanking(int ranking) {
		this.ranking = ranking;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getImage() {
		return image;
	}
	public void setImage(int image) {
		this.image = image;
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
	public String getHoraInicio() {
		return horaInicio;
	}
	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}
	public String getHoraTermino() {
		return horaTermino;
	}
	public void setHoraTermino(String horaTermino) {
		this.horaTermino = horaTermino;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public int getIdOwner() {
		return idOwner;
	}
	public void setIdOwner(int idOwner) {
		this.idOwner = idOwner;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public int getSimboras() {
		return simboras;
	}
	public void setSimboras(int simboras) {
		this.simboras = simboras;
	}
	public String getPreco() {
		return preco;
	}
	public void setPreco(String preco) {
		this.preco = preco;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public int getPrioridade() {
		return prioridade;
	}
	public void setPrioridade(int prioridade) {
		this.prioridade = prioridade;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
	public static ArrayList<Estabelecimento> ranking(){
		ArrayList<Integer> listaSimboras=new ArrayList<Integer>();
		ArrayList<Estabelecimento> listaOrdenada=new ArrayList<Estabelecimento>();
		for(int k=0;k<listaEstabelecimento.size();k++){
			listaSimboras.add(listaEstabelecimento.get(k).getSimboras());
			System.out.println(listaSimboras.get(k));
		}
		Collections.sort(listaSimboras);
		Collections.reverse(listaSimboras);
		for(int i=0;i<listaSimboras.size();i++){
			if(i>9){
				break;
			}
			for(int j=0;j<listaEstabelecimento.size();j++){
				if((listaSimboras.get(i)==listaEstabelecimento.get(j).getSimboras())&&(!listaOrdenada.contains(listaEstabelecimento.get(j)))){
					 listaOrdenada.add(listaEstabelecimento.get(j));
					 System.out.println("Adicionando "+listaEstabelecimento.get(j).getNome());
					 break;
				}
			}
		}
		
		return listaOrdenada;
	}
	
	public static int associeImagem(String tipo){
		int imagem=R.drawable.logo_recife;
		
		if(tipo.equals("Bar")){
			imagem=R.drawable.botao_bares;
		}
		if(tipo.equals("Restaurante")){
			imagem=R.drawable.botao_restaurantes;
		}
		if(tipo.equals("Casa de Show")){
			imagem=R.drawable.botao_casa_de_show;
		}
		if(tipo.equals("Boate")){
			imagem=R.drawable.botao_boate;
		}
		return imagem;
	}

	public static boolean isMeusEstabelecimentosClickados() {
		return meusEstabelecimentosClickados;
	}

	public static void setMeusEstabelecimentosClickados(
			boolean meusEstabelecimentosClickados) {
		Estabelecimento.meusEstabelecimentosClickados = meusEstabelecimentosClickados;
	}
}
