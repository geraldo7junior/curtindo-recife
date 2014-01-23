package bd;

import java.util.ArrayList;

import dominio.Evento;
import dominio.Usuario;
import android.R.bool;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Banco{
	
	public  Banco(Context context){
		this.context = context;
		
	}
	
	private final String tabelaUsuarios = "tabelaUsuarios";
	private final String tabelaEventos ="tabelaEventos";
	private final String tabelaMeusEventos ="tabelaMeusEventos";
	private final String nomeBanco = "CurtindoRecifeDB";
	private final int versaoBD = 1;
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
				String sql = "CREATE TABLE IF NOT EXISTS "+tabelaUsuarios+" (_id INTEGER PRIMARY KEY, nome TEXT, dataNascimento TEXT, email TEXT, senha TEXT, sexo TEXT, eventoFavorito1 TEXT, eventoFavorito2 TEXT, eventoFavorito3 TEXT, mascates INTEGER)";
				db.execSQL(sql);
				//TABELA DE EVENTOS (tabelaEventos)
				String sqlEvento = "CREATE TABLE IF NOT EXISTS "+tabelaEventos+" (_id INTEGER PRIMARY KEY, nome TEXT, endereco TEXT, numero TEXT, preco TEXT,data TEXT, hora TEXT, telefone TEXT, descricao TEXT, tipo TEXT, idOwner INTEGER, simboras INTEGER, idImagem INTEGER, prioridade INTEGER, ranking INTEGER)";
				db.execSQL(sqlEvento);
				//TABELA DOS EVENTOS CRIADOS E QUE O USUÁRIO DEU SIMBORA (tabelMeusEventos)
				String sqlMeusEventos = "CREATE TABLE IF NOT EXISTS "+tabelaMeusEventos+" (_id INTEGER PRIMARY KEY, idUsuario INTEGER, idEvento INTEGER)";
				db.execSQL(sqlMeusEventos);
				
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			
				String sql ="DROP TABLE IF EXISTS "+tabelaUsuarios;
				db.execSQL(sql);
				
				String sql2 = "DROP TABLE IF EXISTS "+ tabelaEventos;
				db.execSQL(sql2);
				
				String sql3 = "DROP TABLE IF EXISTS "+ tabelaMeusEventos;
				db.execSQL(sql3);
			
				onCreate(db);
	
		}
		
		
	}
	public Banco openBd(){
		bdHelper= new BDhelper(context);
		bancoDados = bdHelper.getWritableDatabase();
		System.out.println(this+" "+"------------Open Bd--------");
		return this;
	}
	public void closeBd(){
		bancoDados.close();
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
	
public void inserirEvento(int idOwner, String nome, String endereco, String numero, String preco, String data, String hora, String telefone, String descricao, String tipo, int imagem, int prioridade){
		
		ContentValues valores = new ContentValues();
		
		valores.put("nome", nome);
		valores.put("endereco", endereco);
		valores.put("numero", numero);
		valores.put("preco", preco);
		valores.put("data", data);
		valores.put("hora", hora);
		valores.put("telefone", telefone);
		valores.put("descricao", descricao);
		valores.put("tipo", tipo);
		valores.put("idOwner", idOwner);
		valores.put("imagem", imagem);
		valores.put("prioridade", prioridade);
		

		
		bancoDados.insert(tabelaEventos, null, valores);	
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
		valores.put("mascates", 100);
		
		bancoDados.insert(tabelaUsuarios, null, valores);	
	}
///////////////MÉTODO UPDATE USUÁRIO///////////////
	public void updateUsuario(Usuario usuario){
		try {
			openBd();
			String sqlUpdate = "UPDATE "+tabelaUsuarios+" SET senha = '"+usuario.getSenha()+"', nome = '"+usuario.getNome()+"', email = '"+usuario.getEmail()+"', eventoFavorito1 = '"+usuario.getEventoFavorito1()+"', eventoFavorito2 = '"+usuario.getEventoFavorito2()+"', eventoFavorito3 = '"+usuario.getEventoFavorito3()+"', dataNascimento = '"+usuario.getDataDeNascimento()+"', sexo = '"+usuario.getSexo()+"', mascates = '"+usuario.getMascates()+"', ranking = '"+usuario.getRanking()+"' WHERE _id LIKE '"+Usuario.getId()+"'";
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
			closeBd();
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
	public int editarUsuario(String email, String senhaNova1, String senhaNova2, String senhaAntiga, String eventoFavorito1, String eventoFavorito2, String eventoFavorito3){
		
		
		Usuario usuario = getUsuario(Usuario.getId());
		
		//Se tudo tiver ok
		int condicao =0;
		
		usuario.setEventoFavorito1(eventoFavorito1);
		usuario.setEventoFavorito2(eventoFavorito2);
		usuario.setEventoFavorito3(eventoFavorito3);
		
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
	
	public void darSimbora(int id) {
		try {
			openBd();
			
			String sqlSimboras = "SELECT simboras FROM "+tabelaEventos+" WHERE _id LIKE '"+id+"'";
			cursor = bancoDados.rawQuery(sqlSimboras,null);
			cursor.moveToFirst();
			
			int oldSimbora = cursor.getInt(cursor.getColumnIndex("simboras"));
			int newSimbora = oldSimbora + 1;
			
			updateSimboraMeusEventos(id,newSimbora);
			
			String sqlMeusEventos="INSERT INTO "+tabelaMeusEventos+" (idUsuario, idEvento) VALUES ('"+Usuario.getId()+"','"+id+"')";
			bancoDados.execSQL(sqlMeusEventos);
			Usuario usuario = getUsuario(Usuario.getId());
			usuario.setMascates(usuario.getMascates()+1);
		} catch (Exception erro) {
			// TODO: handle exception
			System.out.println(erro);
		}finally{
			closeBd();
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
			
			System.out.println(usuario.getMascates()+" Mascates");
			return usuario;
			
		} catch (Exception e) {
			System.out.println(e);
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
	public boolean eventoCurtido(Evento evento){
		
		if(Usuario.getId()!=0){
			try {
				openBd();
				String sql = "SELECT idUsuario FROM "+tabelaMeusEventos+" WHERE idEvento LIKE '"+evento.getId()+"' AND idUsuario LIKE '"+Usuario.getId()+"'";
				Cursor cursor3 = bancoDados.rawQuery(sql, null);
				cursor3.moveToFirst();
				System.out.println(cursor3.getInt(cursor.getColumnIndex("idUsuario"))+" ID USUÀRIO");
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
				// retorna 0 caso o email não seja encontrado ou algum erro no banco.
			}finally{
				closeBd();
			}	
		}
		else{
			return false;
		}
	}
	/*public void getTabelaUsuarios() {
		String [] columns = {"nome", "dataNascimento", "email", "senha", "sexo", "eventoFavorito1", "eventoFavorito2", "eventoFavorito3"};
		Cursor cursor = bancoDados.query(nomeTabela1, columns, null, null, null, null, null, null);
		
		ArrayList<String> result = new ArrayList<String>();
		
		for (cursor.moveToFirst(); !cursor.isAfterLast();cursor.moveToNext()){
			
		}
	}*/
}
