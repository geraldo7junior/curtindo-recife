package dominio;

public class Evento {
	
	private static final String EVENTO_SHOW="Show";
	private static final String EVENTO_TEATRO="Teatro";
	private static final String EVENTO_ESPORTES="Esportes";
	private static final String EVENTO_OUTROS="Outros";
	
	private int image;
	private String nome;
	private String data;
	private String hora;
	private String descricao;
	private String tipoDeEvento;
	private static int idEvento;
	private int idOwner; 
	
	 public Evento() {
		 
	 }
	 
	public Evento(String nome, String data, String hora, int image) {
	     this.data = data;
	     this.hora = hora;
	     this.nome = nome;
	     this.image = image;
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
	
	
	
}
