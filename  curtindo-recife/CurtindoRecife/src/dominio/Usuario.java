package dominio;

import java.sql.Date;

//classe que contém todos os atributos de um usuário
public class Usuario {
	
	private static int idUsuario;
	private String nome;
	private String email;
	private String dataDeNascimento;
	private String senha;
	private String sexo;
	private String eventoFavorito1;
	private String eventoFavorito2;
	private String eventoFavorito3;
	
	public static int getId() {
		return idUsuario;
	}
	public static void setId(int idUsuario) {
		Usuario.idUsuario = idUsuario;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDataDeNascimento() {
		return dataDeNascimento;
	}
	public void setDataDeNascimento(String dataDeNascimento) {
		this.dataDeNascimento = dataDeNascimento;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public String getEventoFavorito1() {
		return eventoFavorito1;
	}
	public void setEventoFavorito1(String eventoFavorito1) {
		this.eventoFavorito1 = eventoFavorito1;
	}
	public String getEventoFavorito2() {
		return eventoFavorito2;
	}
	public void setEventoFavorito2(String eventoFavorito2) {
		this.eventoFavorito2 = eventoFavorito2;
	}
	public String getEventoFavorito3() {
		return eventoFavorito3;
	}
	public void setEventoFavorito3(String eventoFavorito3) {
		this.eventoFavorito3 = eventoFavorito3;
	}
	
	
	public Usuario(){
	
	}
	
	public Usuario(String nome, String email, String senha, String dataDeNascimento, String sexo, String eventoFavorito1, String eventoFavorito2, String eventoFavorito3){
		setDataDeNascimento(dataDeNascimento);
		setEmail(email);
		setSenha(senha);
		setNome(nome);
		setEventoFavorito1(eventoFavorito1);
		setEventoFavorito2(eventoFavorito2);
		setEventoFavorito3(eventoFavorito3);
	}

	
}
