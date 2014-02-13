package bd;

import java.util.ArrayList;

import dominio.Estabelecimento;
import dominio.Evento;
import dominio.Usuario;
import android.R.bool;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Banco{
	
	public  Banco(Context context){
		this.context = context;
		
	}
	
	private final String tabelaMeusEstabelecimentos="tabelaMeusEstabelecimentos";
	private final String tabelaEstabelecimentos="tabelaEstabelecimentos";
	private final String tabelaUsuarios = "tabelaUsuarios";
	private final String tabelaEventos ="tabelaEventos";
	private final String tabelaMeusEventos ="tabelaMeusEventos";
	private final String nomeBanco = "CurtindoRecifeDB";
	private static int versaoBD =1;
	private final Context context;
	private BDhelper bdHelper;
	private SQLiteDatabase bancoDados;
	private Cursor cursor;
	
	
	class BDhelper extends SQLiteOpenHelper{

		public BDhelper(Context context) {
			super(context, nomeBanco, null, versaoBD);
			// TODO Auto-generated constructor stb
		}
		
		
		
		@Override
		public void onCreate(SQLiteDatabase db) {
				//TABELA DE USUÁRIOS (tabelaUsuarios)
				String sql = "CREATE TABLE IF NOT EXISTS "+tabelaUsuarios+" (_id INTEGER PRIMARY KEY, nome TEXT, dataNascimento TEXT, email TEXT, senha TEXT, sexo TEXT, eventoFavorito1 TEXT, eventoFavorito2 TEXT, eventoFavorito3 TEXT, mascates INTEGER, ranking INTEGER)";
				db.execSQL(sql);
				//TABELA DE EVENTOS (tabelaEventos)
				String sqlEvento = "CREATE TABLE IF NOT EXISTS "+tabelaEventos+" (_id INTEGER PRIMARY KEY, nome TEXT, endereco TEXT, numero TEXT, preco TEXT,data TEXT, hora TEXT, telefone TEXT, descricao TEXT, tipo TEXT, idOwner INTEGER, simboras INTEGER, idImagem INTEGER, prioridade INTEGER, ranking INTEGER, curtidas INTEGER, morgadas INTEGER)";
				db.execSQL(sqlEvento);
				//TABELA DOS EVENTOS CRIADOS E QUE O USUÁRIO DEU SIMBORA (tabelMeusEventos)
				String sqlMeusEventos = "CREATE TABLE IF NOT EXISTS "+tabelaMeusEventos+" (_id INTEGER PRIMARY KEY, idUsuario INTEGER, idEvento INTEGER, votou BOOLEAN)";
				db.execSQL(sqlMeusEventos);
				////TABELA DE ESTABELECIMENTOS (tabelaEstabelecimentos)
				String sqlEstabelecimentos = "CREATE TABLE IF NOT EXISTS "+tabelaEstabelecimentos+" (_id INTEGER PRIMARY KEY, nome TEXT, endereco TEXT,telefone TEXT, numero TEXT, preco TEXT,data_funcionamento TEXT, horaInicio TEXT, horaTermino TEXT, descricao TEXT, tipo TEXT, idOwner INTEGER, simboras INTEGER, idImagem INTEGER, prioridade INTEGER, ranking INTEGER)";
				db.execSQL(sqlEstabelecimentos);
				////TABELA DE ESTABELECIMENTOS (tabelaEstabelecimentos)
				String sqlMeusEstabelecimentos = "CREATE TABLE IF NOT EXISTS "+tabelaMeusEstabelecimentos+" (_id INTEGER PRIMARY KEY, idEstabelecimento INTEGER, idUsuario INTEGER)";
				db.execSQL(sqlMeusEstabelecimentos);
		}

		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			
				String sql ="DROP TABLE IF EXISTS "+tabelaUsuarios;
				db.execSQL(sql);
				
				String sql2 = "DROP TABLE IF EXISTS "+ tabelaEventos;
				db.execSQL(sql2);
				
				String sql3 = "DROP TABLE IF EXISTS "+ tabelaMeusEventos;
				db.execSQL(sql3);
				
				String sql4 = "DROP TABLE IF EXISTS "+ tabelaEstabelecimentos;
				db.execSQL(sql4);
				
				String sql5 = "DROP TABLE IF EXISTS "+ tabelaMeusEstabelecimentos;
				db.execSQL(sql5);
				onCreate(db);
	
		}
		
		
	}
	
	public void atualizarBanco(){
		try {
			openBd();
			bdHelper= new BDhelper(context);
			bdHelper.onUpgrade(bancoDados, versaoBD, versaoBD+1);
			versaoBD = versaoBD+1;
			bdHelper.onCreate(bancoDados);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}finally{
			closeBd();
		}
		
	}
	public void criarBanco(){
		try {
			openBd();
			bdHelper.onCreate(bancoDados);
			System.out.println("banco Criado");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}finally{
			closeBd();
		}
	}
	
	public Banco openBd(){
		bdHelper= new BDhelper(context);
		bancoDados = bdHelper.getWritableDatabase();
		System.out.println(this+" "+"------------Opened Bd--------");
		return this;
	}
	public void closeBd(){
		bancoDados.close();
		System.out.println(this+" "+"------------Closed Bd--------");
	}
	
	public boolean usuarioCadastrado (String email){
		Cursor cursor;
		try {
			openBd();
			String sql="SELECT id FROM tabelaUsuarios WHERE email LIKE '"+email+"'";
			cursor=bancoDados.rawQuery(sql, null);
			if(cursor==null){
				return true;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	
//////////////////////DELETA TODOS OS ESTABELECIMENTOS DE UM USUARIO/////////////////////////////////
	public void deletarEstabelecimento(int idOwner){
		deletar(tabelaEstabelecimentos,idOwner);
	}
///////////////////DELETA UM ITEN OU MAIS DA Estabelecimentos\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\ 
	public void deletarEstabelecimento(Estabelecimento estabelecimento){
		deletar(estabelecimento.getId(), tabelaEstabelecimentos);
	}
	public void deletarEstabelecimento(Usuario usuarioDono){
		deletar(tabelaEstabelecimentos, "idOwner",""+ usuarioDono.getIdUnico());
	}
///////////////////DELETA UM ITEN OU MAIS DA TABELA MeusEstabelecimentos\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\	
	public void deletarMeusEstabelecimentos(Estabelecimento estabelecimento){
		deletar(tabelaMeusEstabelecimentos, "idEstabelecimento", ""+estabelecimento.getId());
	}
	public void deletarMeusEstabelecimentos(Estabelecimento estabelecimento,Usuario usuario){
		deletar(tabelaMeusEstabelecimentos, "idEstabelecimento","idUsuario", Integer.toString(estabelecimento.getId()),Integer.toString(usuario.getIdUnico()));
	}
	public void deletarMeusEstabelecimentos(Usuario usuario){
		deletar(tabelaMeusEstabelecimentos, "idUsuario", ""+usuario.getIdUnico());
	}
/////////////////Método deletar, deleta linha da tabela...//////// 
private Boolean deletar(String nomeTabela,int idOwner){

	try {
		openBd();
		String sqlExcluir ="DELETE FROM "+nomeTabela+" WHERE idOwner = '"+idOwner+"'";
		bancoDados.execSQL(sqlExcluir);
		return true;
		} catch (Exception e) {
		System.out.println(e);
		return false;
		}finally{
		closeBd();			
	}		
}
/////////////////Método deletar, deleta linha da tabela...//////// 
	private Boolean deletar(int idObjeto, String nomeTabela){
		
		try {
			openBd();
			String sqlExcluir ="DELETE FROM "+nomeTabela+" WHERE _id = '"+idObjeto+"'";
			bancoDados.execSQL(sqlExcluir);
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}finally{
			closeBd();;			
		}		
	}
	
	public void cadastrarUsuario(String nome, String dataDeNascimento, String email, String senha, String sexo, String eventoFavorito1, String eventoFavorito2, String eventoFavorito3){
		//Cadastra Usuário
		//o formato da data é (YYYY-MM-DD)
		try {
			openBd();
			String sql = "INSERT INTO tabelaUsuarios (nome, dataNascimento, email, senha, sexo, eventoFavorito1, eventoFavorito2, eventoFavorito3, mascates, ranking) VALUES ('"+nome+"','"+dataDeNascimento+"','"+email+"','"+senha+"','"+sexo+"','"+eventoFavorito1+"','"+eventoFavorito2+"','"+eventoFavorito3+"', '150','0')";
			bancoDados.execSQL(sql);
		} catch (Exception erro) {
			// TODO: handle exception
			System.out.println(erro);
		}finally{
			closeBd();
		}
	}
	
/////////////////Método deletar dupla condição, deleta linha da tabela...//////// 
private Boolean deletar(String nomeTabela, String colunaDaTabela1, String colunaDaTabela2, String valorColuna1,String valorColuna2){

try {
	openBd();
	System.out.println("DELETE FROM "+nomeTabela+" WHERE "+colunaDaTabela1+" = '"+valorColuna1+"' AND "+colunaDaTabela2+" = '"+valorColuna2+"'");
	String sqlExcluir ="DELETE FROM "+nomeTabela+" WHERE "+colunaDaTabela1+" = '"+valorColuna1+"' AND "+colunaDaTabela2+" = '"+valorColuna2+"'";
	bancoDados.execSQL(sqlExcluir);
	return true;
	} catch (Exception e) {
	System.out.println(e);
	return false;
	}finally{
	closeBd();				
}		
}
	
/////////////////Método deletar, deleta linha da tabela...//////// 
private Boolean deletar(String nomeTabela, String colunaDaTabela, String valorColuna ){

	try {
		openBd();
		String sqlExcluir ="DELETE FROM "+nomeTabela+" WHERE "+colunaDaTabela+" = '"+valorColuna+"'";
		bancoDados.execSQL(sqlExcluir);
		return true;
	} catch (Exception e) {
		System.out.println(e);
		return false;
	}finally{
		closeBd();			
	}		
}
	
/////////////////Método deletar, deleta linha da tabelaMeusEventos...//////// 
private Boolean deletar(Evento evento, int idUsuario){

		try {
			openBd();
			String sqlExcluir ="DELETE FROM "+tabelaMeusEventos+" WHERE idEvento = '"+evento.getId()+"' AND idUsuario = '"+idUsuario+"'";
			bancoDados.execSQL(sqlExcluir);
			
			return true;
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}finally{
			closeBd();			
	}		
	}
private Boolean deletar(Evento evento){

	try {
		openBd();
		String sqlExcluir ="DELETE FROM "+tabelaMeusEventos+" WHERE idEvento = '"+evento.getId()+"'";
		bancoDados.execSQL(sqlExcluir);
		
		return true;
	} catch (Exception e) {
		System.out.println(e);
		return false;
	}finally{
		closeBd();		
}		
}
////////////////O MÉTODO exclui o Usuario///////////////	
	public Boolean excluir(int idUsuario){
		Boolean resultado;
		if(deletar(idUsuario, tabelaUsuarios)){
			resultado = true;
		}else{
			resultado = false;
		}
		return resultado;
	}
	
////////////////MÉTODO excluir, tira Simbora/ exclui da tabelaMeusEventos///////////////	
		public Boolean excluir(Evento evento, int idUsuario){
			
			Boolean resultado=true;
			if(evento.getIdOwner()!=idUsuario){
				if(deletar(evento, idUsuario)){
					resultado = true;
				}
			}
			if(evento.getIdOwner()==idUsuario){
					deletar(evento);
					excluir(idUsuario,evento);
			}else{
					updateSimbora(evento.getId(), evento.getSimboras()-1);
				}
				
		
			return resultado;
		}
////////////////MÉTODO excluirEvento///////////////	
	public Boolean excluir(int idUsuario, Evento evento){
		Boolean resultado = false;
		if(evento.getIdOwner() == idUsuario){
			if(deletar(evento.getId(), tabelaEventos)){
				resultado = true;
			}
		}
		return resultado;
	}	
	
public void inserirMeusEstabelecimentos(Estabelecimento estabelecimento, Usuario usuario){
		
		ContentValues valores = new ContentValues();
		
		valores.put("idEstabelecimento", estabelecimento.getId());
		valores.put("idUsuario", usuario.getIdUnico());
		
		inserirNaTabela(tabelaMeusEstabelecimentos, valores);	
	}	

public void inserirMeusEstabelecimentos(Usuario usuario){
	openBd();
	String ultimoInserido="select * from tabelaEstabelecimentos Where idOwner like '"+usuario.getIdUnico()+"'";
	Cursor cursor=bancoDados.rawQuery(ultimoInserido, null);
	cursor.moveToLast();
	ContentValues valores = new ContentValues();
	
	valores.put("idEstabelecimento", cursor.getInt(cursor.getColumnIndex("_id")));
	valores.put("idUsuario", usuario.getIdUnico());
	
	inserirNaTabela(tabelaMeusEstabelecimentos, valores);	
	closeBd();
}	
public void inserirEstabelecimento(Estabelecimento estabelecimento){
	
	ContentValues valores = new ContentValues();
	
	valores.put("nome", estabelecimento.getNome());
	valores.put("endereco", estabelecimento.getEndereco());
	valores.put("numero", estabelecimento.getNumero());
	valores.put("preco", estabelecimento.getPreco());
	valores.put("data_funcionamento", estabelecimento.getData());
	valores.put("horaInicio", estabelecimento.getHoraInicio());
	valores.put("horaTermino", estabelecimento.getHoraTermino());
	valores.put("telefone", estabelecimento.getTelefone());
	valores.put("descricao", estabelecimento.getDescricao());
	valores.put("tipo", estabelecimento.getTipo());
	valores.put("idOwner", estabelecimento.getIdOwner());
	valores.put("idImagem", estabelecimento.getImage());
	valores.put("prioridade", estabelecimento.getPrioridade());
	valores.put("telefone", estabelecimento.getTelefone());
	
	inserirNaTabela(tabelaEstabelecimentos, valores);
	}
public void inserirEstabelecimento(int idOwner,String nome, String endereco, String numero, String preco, String data, String horaInicio,String horaTermino, String telefone, String descricao, String tipo, int imagem, int prioridade){
		
		ContentValues valores = new ContentValues();
		
		valores.put("nome", nome);
		valores.put("endereco", endereco);
		valores.put("numero", numero);
		valores.put("preco", preco);
		valores.put("data_funcionamento", data);
		valores.put("horaInicio", horaInicio);
		valores.put("horaTermino", horaTermino);
		valores.put("telefone", telefone);
		valores.put("descricao", descricao);
		valores.put("tipo", tipo);
		valores.put("idOwner", idOwner);
		valores.put("idImagem", imagem);
		valores.put("prioridade", prioridade);
		valores.put("simboras", 0);
		valores.put("ranking", 0);
		
		inserirNaTabela(tabelaEstabelecimentos, valores);
		}
public void inserirEvento(Evento evento){
	ContentValues valores = new ContentValues();
	
	valores.put("nome", evento.getNome());
	valores.put("endereco", evento.getEndereco());
	valores.put("numero", evento.getNumero());
	valores.put("preco", evento.getPreco());
	valores.put("data", evento.getData());
	valores.put("hora", evento.getHora());
	valores.put("telefone", evento.getTelefone());
	valores.put("descricao", evento.getDescricao());
	valores.put("tipo", evento.getTipoDeEvento());
	valores.put("idOwner", evento.getIdOwner());
	valores.put("imagem", evento.getImage());
	valores.put("prioridade", evento.getPrioridade());
	
	inserirNaTabela(tabelaEventos, valores);

}
private void inserirNaTabela(String nomeTabela,ContentValues valores ){
		try {
			openBd();
			bancoDados.insert(nomeTabela, null, valores);
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			closeBd();
		}
			
	}
	
	public void inserirUsuario(String nome, String dataDeNascimento, String email, String senha, String sexo, String eventoFavorito1, String eventoFavorito2, String eventoFavorito3){
		
		ContentValues valores = new ContentValues();
		
		valores.put("nome", nome);
		valores.put("dataNascimento", dataDeNascimento);
		valores.put("email", email);
		valores.put("senha", senha);
		valores.put("sexo", sexo);
		valores.put("eventoFavorito1", eventoFavorito1);
		valores.put("eventoFavorito2", eventoFavorito2);
		valores.put("eventoFavorito3", eventoFavorito3);
		valores.put("mascates", 150);
		valores.put("ranking", 0);
		
		
		inserirNaTabela(tabelaUsuarios, valores);	
	}
///////////////MÉTODO UPDATE USUÁRIO///////////////
	public void updateUsuario(Usuario usuario){
		try {
			openBd();
			String sqlUpdate = "UPDATE "+tabelaUsuarios+" SET senha = '"+usuario.getSenha()+"', nome = '"+usuario.getNome()+"', email = '"+usuario.getEmail()+"', eventoFavorito1 = '"+usuario.getEventoFavorito1()+"', eventoFavorito2 = '"+usuario.getEventoFavorito2()+"', eventoFavorito3 = '"+usuario.getEventoFavorito3()+"', dataNascimento = '"+usuario.getDataDeNascimento()+"', sexo = '"+usuario.getSexo()+"', mascates = '"+usuario.getMascates()+"', ranking = '"+usuario.getRanking()+"' WHERE _id LIKE '"+usuario.getIdUnico()+"'";
			bancoDados.execSQL(sqlUpdate);
		} catch (Exception e) {
			System.out.println(e);
		} finally{
			closeBd();
		}
		
		
	}
	
	public void updateVotou(int idUsuario, int idEvento){
		try {
			openBd();
			String sqlUpdate = "UPDATE "+tabelaMeusEventos+" SET votou = '"+true+"' WHERE idUsuario LIKE '"+idUsuario+"' AND idEvento Like '"+idEvento+"'";
			bancoDados.execSQL(sqlUpdate);
		} catch (Exception e) {
			System.out.println(e);
		} finally{
			closeBd();
		}
	}
	
	
	
/////MÉTODO idUsuário RETORNA O CURSOR COM AS INFORMAÇÕES DO(S) USUÁRIOS COM O EMAIL RECEBIDO COMO PARÂMETRO////////
	public Integer idUsuario(String email){
		try {
			openBd();
			String sql = "SELECT _id FROM "+tabelaUsuarios+" WHERE email LIKE '"+email+"' ";
			cursor = bancoDados.rawQuery(sql, null);
			cursor.moveToFirst();
			return cursor.getInt(cursor.getPosition());
			
		} catch (Exception erro) {
			System.out.println(erro);		
		}finally{
			bancoDados.close();
		}	
		return null;
	}
//////O MÉTODO checarEmail RETORNA (TRUE) SE O EMAIL ESTIVER NA tabelaUsuarios///////
	public Boolean checarEmail(String email){
		try {
			openBd();
			String sql = "SELECT _id FROM "+tabelaUsuarios+" WHERE email LIKE '"+email+"' ";
			Cursor cursorChecarEmail = bancoDados.rawQuery(sql, null);
			Boolean resultado;
			if(cursorChecarEmail.getCount()==0){
				resultado = false;
			}else{
				resultado = true ;
			}
			return resultado;
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			closeBd();
		}
		return null;
	}
/////////////////MÉTODO CRIADO PARA O MÉTODO editarUsuario...///////// 
	
	private Boolean checarEmailUsuario(String email){
		Boolean resultado; 
		if (checarEmail(email)){
			if(idUsuario(email) == Usuario.getId()){
				 resultado = true;
			 }else{
				 resultado = false;
			 }
			return resultado;
		}else{
			resultado = true;
			return resultado;
		}
		
	}
//O método editarUsuario retorna (0)=tudo ok;(1)=email já é de outro usuário;(2)=senha incorreta;(3)senha nova errada/senhaNova1 != senhaNova2 ////////////////// 
	public int editarUsuario(String email, String senhaNova1, String senhaNova2, String senhaAntiga, String eventoFavorito1, String eventoFavorito2, String eventoFavorito3,String sexo, String data){
		
		
		Usuario usuario = getUsuario(Usuario.getId());
		
		//Se tudo tiver ok
		int condicao =0;
		
		usuario.setEventoFavorito1(eventoFavorito1);
		usuario.setEventoFavorito2(eventoFavorito2);
		usuario.setEventoFavorito3(eventoFavorito3);
		if(!sexo.equals("")){
		usuario.setSexo(sexo);
		}
		if(!data.equals("")){
			usuario.setDataDeNascimento(data);
		}
		if(checarEmailUsuario(email) && !(email.equals(""))){
			
			usuario.setEmail(email);
					
		}else{
			//Email não cadastrado
			condicao = 3;
			
		}if(!senhaAntiga.equals("")){		
			if(usuario.getSenha().equals(senhaAntiga)){
				if(!senhaNova1.equals("")){
					if(senhaNova1.equals(senhaNova2)){
						usuario.setSenha(senhaNova1);
						if(email.equals("")){
							condicao = 2;
						}
						}else{
							//Senhas novas não conferem
							condicao = 4;
						}
				}			
			}else{
				//Errou a senha antiga
				condicao = 5;
			}
		}else{
			condicao = 1;
		}
		updateUsuario(usuario);
		return condicao;
	}
	public Boolean updatePrioridade(int idEvento, int prioridade){
		try {
			openBd();
			String sql = "UPDATE "+tabelaEventos+" SET prioridade ='"+prioridade+"' WHERE _id LIKE '"+idEvento+"'";
			bancoDados.execSQL(sql);
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			closeBd();
		}return null;
	}
	public void updateSimbora(int idEvento, int newSimbora) {
		try {
			openBd();
			String sql = "UPDATE "+tabelaEventos+" SET simboras = '"+newSimbora+"' WHERE _id LIKE '"+idEvento+"'";
			bancoDados.execSQL(sql);	
			
		} catch (Exception erro) {
			// TODO: handle exception
			System.out.println(erro);
		}finally{
			closeBd();
			}
	}
	
	public void updateCurtidasAndMorgadas(int idEvento, Integer newCurtidas, Integer newMorgadas) {
		try {
			openBd();
			if(newMorgadas != null & newCurtidas != null){
				String sql2 = "UPDATE "+tabelaEventos+" SET curtidas = '"+newCurtidas+"', morgadas = '"+newMorgadas+"' WHERE _id LIKE '"+idEvento+"'";
				bancoDados.execSQL(sql2);
			}else if(newMorgadas==null){
				String sql1 = "UPDATE "+tabelaEventos+" SET curtidas = '"+newCurtidas+"' WHERE _id LIKE '"+idEvento+"'";
				bancoDados.execSQL(sql1);	
			}else if(newCurtidas==null){
				String sql3 = "UPDATE "+tabelaEventos+" SET morgadas = '"+newMorgadas+"' WHERE _id LIKE '"+idEvento+"'";
				bancoDados.execSQL(sql3);
			}
			} catch (Exception erro) {
				// TODO: handle exception
				erro.printStackTrace();
			}finally{
				closeBd();
			}
		}
	
	private void updateSimboraMeusEventos(int idEvento, int newSimbora) {
		try {
			
			String sql = "UPDATE "+tabelaEventos+" SET simboras = '"+newSimbora+"' WHERE _id LIKE '"+idEvento+"'";
			bancoDados.execSQL(sql);	
			
		} catch (Exception erro) {
			// TODO: handle exception
			System.out.println(erro);
		}finally{
			
		}
	}
	
	public void darSimbora(int idEvento) {
		try {
			openBd();
			
			String sqlSimboras = "SELECT simboras FROM "+tabelaEventos+" WHERE _id LIKE '"+idEvento+"'";
			cursor = bancoDados.rawQuery(sqlSimboras,null);
			cursor.moveToFirst();
			
			int oldSimbora = cursor.getInt(cursor.getColumnIndex("simboras"));
			int newSimbora = oldSimbora + 1;
			
			updateSimboraMeusEventos(idEvento,newSimbora);
			
			String sqlMeusEventos="INSERT INTO "+tabelaMeusEventos+" (idUsuario, idEvento) VALUES ('"+Usuario.getId()+"','"+idEvento+"')";
			bancoDados.execSQL(sqlMeusEventos);

			System.out.println(Usuario.getId()+" Id antes");
			
			Usuario usuarioQueCriou = getUsuario(retornaEvento(idEvento).getIdOwner());
			System.out.println(usuarioQueCriou.getNome()+" Usuário que criou");
			usuarioQueCriou.setMascates(usuarioQueCriou.getMascates()+5);
			updateUsuario(usuarioQueCriou);
			System.out.println(Usuario.getId()+" depois");
			
			Usuario usuarioQueCurtiu = getUsuario(Usuario.getId());
			usuarioQueCurtiu.setMascates(usuarioQueCurtiu.getMascates()+1);
			System.out.println(usuarioQueCurtiu.getNome()+" Usuário que curtiu");
			updateUsuario(usuarioQueCurtiu);
			
			
		} catch (Exception erro) {
			// TODO: handle exception
			System.out.println(erro);
		}finally{
			closeBd();
		}
	}
	
	public void darSimbora(Estabelecimento estabelecimento,Usuario usuario) {
		try {
			
			
			estabelecimento.setSimboras(estabelecimento.getSimboras()+1);
			inserirMeusEstabelecimentos(estabelecimento, usuario);
			
			updateEstabelecimento(estabelecimento);
			System.out.println(Usuario.getId()+" Id antes");
			
			Usuario usuarioQueCriou = getUsuario(estabelecimento.getIdOwner());
			System.out.println(usuarioQueCriou.getNome()+" Usuário que criou");
			usuarioQueCriou.setMascates(usuarioQueCriou.getMascates()+5);
			updateUsuario(usuarioQueCriou);
			System.out.println(Usuario.getId()+" depois");
			
			
			usuario.setMascates(usuario.getMascates()+1);
			System.out.println(usuario.getNome()+" Usuário que curtiu");
			updateUsuario(usuario);
			
			
		} catch (Exception erro) {
			// TODO: handle exception
			System.out.println(erro);
		}
	}
	
	public void tirarSimbora(Evento evento,Usuario usuario) {
		try {
			
			
			evento.setSimboras(evento.getSimboras()-1);
			deletar(evento, usuario.getIdUnico());
			
			updateSimbora(evento.getId(), evento.getSimboras());
			
			System.out.println(Usuario.getId()+" Id antes");
			
			Usuario usuarioQueCriou = getUsuario(evento.getIdOwner());
			System.out.println(usuarioQueCriou.getNome()+" Usuário que criou");
			usuarioQueCriou.setMascates(usuarioQueCriou.getMascates()-5);
			
			updateUsuario(usuarioQueCriou);
			
			System.out.println(Usuario.getId()+" depois");
			usuario.setMascates(usuario.getMascates()-1);
			System.out.println(usuario.getNome()+" Usuário que curtiu");
			
			updateUsuario(usuario);
			
			
		} catch (Exception erro) {
			// TODO: handle exception
			System.out.println(erro);
		}
	}
	
	public void tirarSimbora(Estabelecimento estabelecimento,Usuario usuario) {
		try {
			
			
			estabelecimento.setSimboras(estabelecimento.getSimboras()-1);
			deletarMeusEstabelecimentos(estabelecimento, usuario);
			
			updateEstabelecimento(estabelecimento);
			
			System.out.println(Usuario.getId()+" Id antes");
			
			Usuario usuarioQueCriou = getUsuario(estabelecimento.getIdOwner());
			System.out.println(usuarioQueCriou.getNome()+" Usuário que criou");
			usuarioQueCriou.setMascates(usuarioQueCriou.getMascates()-5);
			updateUsuario(usuarioQueCriou);
			System.out.println(Usuario.getId()+" depois");
			
			
			usuario.setMascates(usuario.getMascates()-1);
			System.out.println(usuario.getNome()+" Usuário que curtiu");
			updateUsuario(usuario);
			
			
		} catch (Exception erro) {
			// TODO: handle exception
			System.out.println(erro);
		}
	}
	
///////////////MÉTODO getUsuario FAZ PESQUISA NO BANCO E RETORNA (OBJETO) DO TIPO (Usuario)////////////////	
	public Usuario getUsuario(int id){
		
		try {
			openBd();
			
			String sql = "SELECT * from "+tabelaUsuarios+" WHERE _id LIKE '"+id+"'";
			cursor = bancoDados.rawQuery(sql,null);
			cursor.moveToFirst();
			
			Usuario usuario = new Usuario();
			usuario.setSexo(cursor.getString(cursor.getColumnIndex("sexo")));
			usuario.setNome(cursor.getString(cursor.getColumnIndex("nome")));
			usuario.setDataDeNascimento((cursor.getString(cursor.getColumnIndex("dataNascimento"))));
			usuario.setEmail((cursor.getString(cursor.getColumnIndex("email"))));
			usuario.setEventoFavorito1((cursor.getString(cursor.getColumnIndex("eventoFavorito1"))));
			usuario.setEventoFavorito2((cursor.getString(cursor.getColumnIndex("eventoFavorito2"))));
			usuario.setEventoFavorito3((cursor.getString(cursor.getColumnIndex("eventoFavorito3"))));
			usuario.setSenha((cursor.getString(cursor.getColumnIndex("senha"))));
			usuario.setMascates(cursor.getInt(cursor.getColumnIndex("mascates")));
			usuario.setRanking(cursor.getInt(cursor.getColumnIndex("ranking")));
			usuario.setIdUnico(cursor.getInt(cursor.getColumnIndex("_id")));
			
			System.out.println(usuario.getMascates()+" Mascates");
			return usuario;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			closeBd();
		}
		
		return null;
			
	}
	
	public void setMeusEventos(int idUsuario){
		try {
			openBd();
			String sql = "SELECT * FROM "+tabelaMeusEventos+" WHERE idUsuario LIKE '"+idUsuario+"' ";
			cursor = bancoDados.rawQuery(sql, null);
			
			cursor.moveToFirst();
			for(int i=0;i<cursor.getCount();i++){			
				Evento.addMeusEventos(retornaEvento(cursor.getInt(cursor.getColumnIndex("idEvento"))));
				if(i!=cursor.getCount()-1){
					cursor.moveToNext();
				}		
			}
		} catch (Exception erro) {
			System.out.println(erro);
			
		}finally{
			closeBd();
		}	
	}
	
	public Estabelecimento retornaEstabelecimento(int idEstabelecimento){
		try {
			openBd();
			String sql = "SELECT * FROM "+tabelaEstabelecimentos+" WHERE _id LIKE '"+idEstabelecimento+"' ";
			Cursor cursor2 = bancoDados.rawQuery(sql, null);
			cursor2.moveToFirst();
			
			Estabelecimento estabelecimento = new Estabelecimento(cursor2);
			
			
			
			return estabelecimento;
			
		} catch (Exception erro) {
			System.out.println(erro);
			
		}finally{
			closeBd();
		}	
		return null;
	}
	
	public Evento retornaEvento(int idEvento){
		try {
			String sql = "SELECT * FROM "+tabelaEventos+" WHERE _id LIKE '"+idEvento+"' ";
			Cursor cursor2 = bancoDados.rawQuery(sql, null);
			cursor2.moveToFirst();
			
			Evento evento = new Evento();
			evento.setData(cursor2.getString(cursor2.getColumnIndex("data")));
			evento.setDescricao(cursor2.getString(cursor2.getColumnIndex("descricao")));
			evento.setEndereco(cursor2.getString(cursor2.getColumnIndex("endereco")));
			evento.setHora(cursor2.getString(cursor2.getColumnIndex("hora")));
			evento.setId(cursor2.getInt(cursor2.getColumnIndex("_id")));
			evento.setIdOwner(cursor2.getInt(cursor2.getColumnIndex("idOwner")));
			evento.setImage(cursor2.getInt(cursor2.getColumnIndex("idImagem")));
			evento.setNome(cursor2.getString(cursor2.getColumnIndex("nome")));
			evento.setNumero(cursor2.getString(cursor2.getColumnIndex("numero")));
			evento.setPreco(cursor2.getString(cursor2.getColumnIndex("preco")));
			evento.setSimboras(cursor2.getInt(cursor2.getColumnIndex("simboras")));
			evento.setTelefone(cursor2.getString(cursor2.getColumnIndex("telefone")));
			evento.setTipoDeEvento(cursor2.getString(cursor2.getColumnIndex("tipo")));
			evento.setPrioridade(cursor2.getInt(cursor2.getColumnIndex("prioridade")));
			evento.setRanking(cursor2.getInt(cursor2.getColumnIndex("ranking")));
			evento.setCurtidas(cursor2.getInt(cursor2.getColumnIndex("curtidas")));
			evento.setMorgadas(cursor2.getInt(cursor2.getColumnIndex("morgadas")));
			
			if(Usuario.getId()!=0){
				try{
					String sqlConsulta = "SELECT idUsuario FROM "+tabelaMeusEventos+" WHERE idEvento LIKE '"+evento.getId()+"' AND idUsuario LIKE '"+Usuario.getId()+"'";
					Cursor cursor3 = bancoDados.rawQuery(sqlConsulta, null);
					cursor3.moveToFirst();
					System.out.println(cursor3.getInt(cursor3.getColumnIndex("idUsuario"))+" ID USUÀRIO");
					System.out.println("CRIADOR DO EVENTO "+evento.getIdOwner());
					System.out.println("Nome do evento: "+evento.getNome());
					
					if((Usuario.getId()!=evento.getIdOwner())){
						evento.setCurtido(true);
					}
				}
				catch (Exception erro) {
						System.out.println(erro);
				}
			
			}
			return evento;
			
		} catch (Exception erro) {
			System.out.println(erro);
			
		}	
		return null;
	}
	private Evento setEvento(Cursor cursor2){
		Evento evento = new Evento();
		evento.setData(cursor2.getString(cursor2.getColumnIndex("data")));
		evento.setDescricao(cursor2.getString(cursor2.getColumnIndex("descricao")));
		evento.setEndereco(cursor2.getString(cursor2.getColumnIndex("endereco")));
		evento.setHora(cursor2.getString(cursor2.getColumnIndex("hora")));
		evento.setId(cursor2.getInt(cursor2.getColumnIndex("_id")));
		evento.setIdOwner(cursor2.getInt(cursor2.getColumnIndex("idOwner")));
		evento.setImage(cursor2.getInt(cursor2.getColumnIndex("idImagem")));
		evento.setNome(cursor2.getString(cursor2.getColumnIndex("nome")));
		evento.setNumero(cursor2.getString(cursor2.getColumnIndex("numero")));
		evento.setPreco(cursor2.getString(cursor2.getColumnIndex("preco")));
		evento.setSimboras(cursor2.getInt(cursor2.getColumnIndex("simboras")));
		evento.setTelefone(cursor2.getString(cursor2.getColumnIndex("telefone")));
		evento.setTipoDeEvento(cursor2.getString(cursor2.getColumnIndex("tipo")));
		evento.setPrioridade(cursor2.getInt(cursor2.getColumnIndex("prioridade")));
		return evento;
	}
	
	public void updateEstabelecimento(Estabelecimento estabelecimento) {
		try {
			openBd();
			String sql = "UPDATE "+tabelaEstabelecimentos+" SET idOwner = '"+estabelecimento.getIdOwner()+"', idImagem = '"+estabelecimento.getImage()+"'," +
					"nome = '"+estabelecimento.getNome()+"', data_funcionamento = '"+estabelecimento.getData()+"', horaInicio = '"+estabelecimento.getHoraInicio()+"'," +
					" horaTermino = '"+estabelecimento.getHoraTermino()+"', descricao = '"+estabelecimento.getDescricao()+"'," +
					" tipo = '"+estabelecimento.getTipo()+"', simboras = '"+estabelecimento.getSimboras()+"'," +
					" preco = '"+estabelecimento.getPreco()+"', numero = '"+estabelecimento.getNumero()+"', endereco = '"+estabelecimento.getEndereco()+"'," +
					" prioridade = '"+estabelecimento.getPrioridade()+"', ranking = '"+estabelecimento.getRanking()+"' WHERE _id LIKE '"+estabelecimento.getId()+"'";
			bancoDados.execSQL(sql);	
			
		} catch (Exception erro) {
			// TODO: handle exception
			System.out.println(erro);
		}finally{
			closeBd();
			}
	}
	
	///RETORNA UMA LISTA COM OS ESTABELECIMENTOS DE UM USUARIO/////////
		public ArrayList<Estabelecimento> getListaMeusEstabelecimentos(Usuario usuario){
			try { 
				openBd();
				String sql = "SELECT * FROM "+tabelaMeusEstabelecimentos+" WHERE idUsuario LIKE '"+usuario.getIdUnico()+"' ";
				Cursor cursor2 = bancoDados.rawQuery(sql, null);
				ArrayList<Estabelecimento> listaMeusEstabelecimentos = new ArrayList<Estabelecimento>();			
				
				int tamanho = cursor2.getCount();
				cursor2.moveToFirst();
				if (tamanho!=0){
					
					for(int i=0;i<tamanho;i++){			
						String sql2 = "SELECT * FROM "+tabelaEstabelecimentos+" WHERE _id LIKE '"+cursor2.getInt(cursor2.getColumnIndex("idEstabelecimento"))+"' ";
						
						Cursor cursor3 = bancoDados.rawQuery(sql2, null);
						cursor3.moveToFirst();
						Estabelecimento estabelecimento = new Estabelecimento(cursor3);
						
						listaMeusEstabelecimentos.add(estabelecimento);					
						if(i!=tamanho-1){
							cursor2.moveToNext();
						}
					
					}
				}
				
				return listaMeusEstabelecimentos;
			} catch (Exception erro) {
				erro.printStackTrace();
				
			}finally{
				closeBd();
			}return null;
			
		}
		public ArrayList<Estabelecimento> getListaEstabelecimentos(int idEstabelecimento){
			try { 
				
				String sql = "SELECT * FROM "+tabelaEstabelecimentos+" WHERE _id LIKE '"+idEstabelecimento+"'";
				Cursor cursor2 = bancoDados.rawQuery(sql, null);
				cursor2.moveToFirst();			
				ArrayList<Estabelecimento> listaEstabelecimentos = new ArrayList<Estabelecimento>();
				if (cursor2.getCount()!=0){
					for(int i=0;i<cursor2.getCount();i++){			
						Estabelecimento estabelecimento = new Estabelecimento(cursor2);
						listaEstabelecimentos.add(estabelecimento);					
						if(i!=cursor2.getCount()-1){
							cursor2.moveToNext();
						}
					
					}
				}
				return listaEstabelecimentos;
			} catch (Exception erro) {
				erro.printStackTrace();
				
			}finally{
				
			}return null;
			
		}
		//RETORNA TODOS OS ESTABELECIMENTOS
		public ArrayList<Estabelecimento> getListaEstabelecimentos(){
			try { 
				openBd();
				String sql = "SELECT * FROM "+tabelaEstabelecimentos+"";
				Cursor cursor2 = bancoDados.rawQuery(sql, null);
				cursor2.moveToFirst();			
				ArrayList<Estabelecimento> listaEstabelecimentos = new ArrayList<Estabelecimento>();
				if (cursor2.getCount()!=0){
					for(int i=0;i<cursor2.getCount();i++){			
						Estabelecimento estabelecimento = new Estabelecimento(cursor2);
						listaEstabelecimentos.add(estabelecimento);					
						if(i!=cursor2.getCount()-1){
							cursor2.moveToNext();
						}
					
					}
				}
				return listaEstabelecimentos;
			} catch (Exception erro) {
				erro.printStackTrace();
				
			}finally{
				closeBd();
			}return null;
			
		}
	
	///RETORNA UMA LISTA COM TODOS OS ESTABELECIMENTOS DE UM TIPO/////////
	public ArrayList<Estabelecimento> getListaEstabelecimentos(String tipo){
		try { 
			openBd();
			String sql = "SELECT * FROM "+tabelaEstabelecimentos+" WHERE tipo LIKE '"+tipo+"' ";
			Cursor cursor2 = bancoDados.rawQuery(sql, null);
			cursor2.moveToFirst();			
			ArrayList<Estabelecimento> listaEstabelecimentos = new ArrayList<Estabelecimento>();
			if (cursor2.getCount()!=0){
				for(int i=0;i<cursor2.getCount();i++){			
					Estabelecimento estabelecimento = new Estabelecimento(cursor2);
					listaEstabelecimentos.add(estabelecimento);					
					if(i!=cursor2.getCount()-1){
						cursor2.moveToNext();
					}
				
				}
			}
			return listaEstabelecimentos;
		} catch (Exception erro) {
			erro.printStackTrace();
			
		}finally{
			closeBd();
		}return null;
		
	}
	
	///RETORNA UMA LISTA COM TODOS OS ESTABELECIMENTOS DE UM TIPO E DATA ESPECÍFICOS/////////
		public ArrayList<Estabelecimento> getListaEstabelecimentos(String tipo, String data){
			try { 
				openBd();
				String sql = "SELECT * FROM "+tabelaEstabelecimentos+" WHERE tipo LIKE '"+tipo+"' AND data_funcionamento LIKE '"+data+"'";
				Cursor cursor2 = bancoDados.rawQuery(sql, null);
				cursor2.moveToFirst();			
				ArrayList<Estabelecimento> listaEstabelecimentos = new ArrayList<Estabelecimento>();
				if (cursor2.getCount()!=0){
					for(int i=0;i<cursor2.getCount();i++){			
						Estabelecimento estabelecimento = new Estabelecimento(cursor2);
						listaEstabelecimentos.add(estabelecimento);					
						if(i!=cursor2.getCount()-1){
							cursor2.moveToNext();
						}
					
					}
				}
				return listaEstabelecimentos;
			} catch (Exception erro) {
				erro.printStackTrace();
				
			}finally{
				closeBd();
			}return null;
			
		}
		
		public ArrayList<Evento> ListarEvento(){
			try { 
				openBd();
				String sql = "SELECT * FROM "+tabelaEventos+"";
				Cursor cursor2 = bancoDados.rawQuery(sql, null);
				cursor2.moveToFirst();			
				ArrayList<Evento> listaEventosData = new ArrayList<Evento>();
				if (cursor2.getCount()!=0){
					for(int i=0;i<cursor2.getCount();i++){			
						
						listaEventosData.add(setEvento(cursor2));
						listaEventosData.get(i).setCurtido(eventoCurtido(listaEventosData.get(i)));
						if(i!=cursor2.getCount()-1){
							cursor2.moveToNext();
						}
					
					}
				}
				return listaEventosData;
			} catch (Exception erro) {
				erro.printStackTrace();
				
			}finally{
				closeBd();
			}return null;
			
		}
		
		public ArrayList<Evento> ListarEventoPorCategoria(String tipo){
			try { 
				openBd();
				String sql = "SELECT * FROM "+tabelaEventos+" Where tipo Like '"+tipo+"'";
				Cursor cursor2 = bancoDados.rawQuery(sql, null);
				cursor2.moveToFirst();			
				ArrayList<Evento> listaEventos = new ArrayList<Evento>();
				if (cursor2.getCount()!=0){
					for(int i=0;i<cursor2.getCount();i++){			
						
						listaEventos.add(setEvento(cursor2));
						listaEventos.get(i).setCurtido(eventoCurtido(listaEventos.get(i)));
						if(i!=cursor2.getCount()-1){
							cursor2.moveToNext();
						}
					
					}
				}
				return listaEventos;
			} catch (Exception erro) {
				erro.printStackTrace();
				
			}finally{
				closeBd();
			}return null;
			
		}
	
	public ArrayList<Evento> ListarEventoPorData(String data){
		try { 
			openBd();
			String sql = "SELECT * FROM "+tabelaEventos+" WHERE data LIKE '"+data+"' ";
			Cursor cursor2 = bancoDados.rawQuery(sql, null);
			cursor2.moveToFirst();			
			ArrayList<Evento> listaEventosData = new ArrayList<Evento>();
			if (cursor2.getCount()!=0){
				for(int i=0;i<cursor2.getCount();i++){			
					
					listaEventosData.add(setEvento(cursor2));
					listaEventosData.get(i).setCurtido(eventoCurtido(listaEventosData.get(i)));
					System.out.println(listaEventosData.get(i).getNome()+" Eventos do listar DAta");
					if(i!=cursor2.getCount()-1){
						cursor2.moveToNext();
					}
				
				}
			}
			return listaEventosData;
		} catch (Exception erro) {
			erro.printStackTrace();
			
		}finally{
			closeBd();
		}return null;
		
	}
	
	public ArrayList<Evento> ListarEventoPorData(String data,String tipo){
		try { 
			openBd();
			String sql = "SELECT * FROM "+tabelaEventos+" WHERE data LIKE '"+data+"' AND tipo LIKE '"+tipo+"'";
			Cursor cursor2 = bancoDados.rawQuery(sql, null);
			cursor2.moveToFirst();			
			ArrayList<Evento> listaEventosData = new ArrayList<Evento>();
			if (cursor2.getCount()!=0){
				for(int i=0;i<cursor2.getCount();i++){			
					
					listaEventosData.add(setEvento(cursor2));
					listaEventosData.get(i).setCurtido(eventoCurtido(listaEventosData.get(i)));
					System.out.println(listaEventosData.get(i).getNome()+" Eventos do listar DAta");
					if(i!=cursor2.getCount()-1){
						cursor2.moveToNext();
					}
				
				}
			}
			return listaEventosData;
		} catch (Exception erro) {
			erro.printStackTrace();
			
		}finally{
			closeBd();
		}return null;
		
	}
	
	public ArrayList<Evento> ListarEventoPorDataHora(String data, String hora,String tipo){
		try { 
			openBd();
			String sql = "SELECT * FROM "+tabelaEventos+" WHERE data LIKE '"+data+"' AND tipo LIKE '"+tipo+"' AND hora LIKE '"+hora+"'";
			Cursor cursor2 = bancoDados.rawQuery(sql, null);
			cursor2.moveToFirst();			
			ArrayList<Evento> listaEventosData = new ArrayList<Evento>();
			if (cursor2.getCount()!=0){
				for(int i=0;i<cursor2.getCount();i++){			
					
					listaEventosData.add(setEvento(cursor2));
					listaEventosData.get(i).setCurtido(eventoCurtido(listaEventosData.get(i)));
					System.out.println(listaEventosData.get(i).getNome()+" Eventos do listar DAta");
					if(i!=cursor2.getCount()-1){
						cursor2.moveToNext();
					}
				
				}
			}
			return listaEventosData;
		} catch (Exception erro) {
			erro.printStackTrace();
			
		}finally{
			closeBd();
		}return null;
		
	}
	
	public boolean eventoCurtido(Evento evento){
		
		if(Usuario.getId()!=0){
			try {
				String sql = "SELECT idUsuario FROM "+tabelaMeusEventos+" WHERE idEvento LIKE '"+evento.getId()+"' AND idUsuario LIKE '"+Usuario.getId()+"'";
				Cursor cursor3 = bancoDados.rawQuery(sql, null);
				cursor3.moveToFirst();
				System.out.println(evento.getNome()+" Nome evento evento curtido");
				System.out.println(cursor3.getCount());
				System.out.println(cursor3.getInt(cursor3.getColumnIndex("idUsuario"))+" ID USUÀRIO");
				System.out.println("CRIADOR DO EVENTO "+evento.getIdOwner());
				System.out.println("Nome do evento: "+evento.getNome());
				if((Usuario.getId()!=evento.getIdOwner())){
					return true;
				}
				else{
					return false;
				}
				
			} catch (Exception erro) {
				System.out.println(erro);
				return false;
			}
				// retorna 0 caso o email não seja encontrado ou algum erro no banco.
		}
		else{
			return false;
		}
	}
	
public boolean curtido(Estabelecimento estabelecimento,Usuario usuario){
			try {
				openBd();
				String sql = "SELECT * FROM "+tabelaMeusEstabelecimentos+" WHERE idEstabelecimento LIKE '"+estabelecimento.getId()+"' AND idUsuario LIKE '"+usuario.getIdUnico()+"'";
				Cursor cursor3 = bancoDados.rawQuery(sql, null);
				cursor3.moveToFirst();

				if(cursor3.getCount()!=0){
					return true;
				}
				else{
					return false;
				}
				
			} catch (Exception erro) {
				System.out.println(erro);
				return false;
			}finally{
				closeBd();
			}
	}

public boolean curtido(Evento evento,Usuario usuario){
	try {
		openBd();
		String sql = "SELECT * FROM "+tabelaMeusEventos+" WHERE idEvento LIKE '"+evento.getId()+"' AND idUsuario LIKE '"+usuario.getIdUnico()+"'";
		Cursor cursor3 = bancoDados.rawQuery(sql, null);
		cursor3.moveToFirst();

		if(cursor3.getCount()!=0){
			return true;
		}
		else{
			return false;
		}
		
	} catch (Exception erro) {
		System.out.println(erro);
		return false;
	}finally{
		closeBd();
	}
}

}
