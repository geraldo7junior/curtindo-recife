package persistencia;

import dominio.Usuario;
import android.app.Activity;
import android.database.Cursor;
import java.sql.Date;
import java.sql.Time;
import android.database.sqlite.SQLiteDatabase;

public class ManipulaBD extends Activity{
	
	String NomeBanco = "CurtindoRecifeDB";
	SQLiteDatabase BancoDados = null;
	Cursor cursor;
	
	public Cursor eventosPorData(Date data){
		// Retorna cursor contendo os eventos de uma data em espec�fico
		try {
			BancoDados = openOrCreateDatabase(NomeBanco, MODE_WORLD_READABLE, null);
			String sql = "SELECT * FROM tabelaUsuarios WHERE data LIKE '"+data+"'";
			cursor = BancoDados.rawQuery(sql, null);
			cursor.moveToFirst();
			return cursor;
			
		} catch (Exception erro) {
			System.out.println(erro);
			return null;
			// retorna 0 caso o email n�o seja encontrado ou algum erro no banco.
		}finally{
			BancoDados.close();
		}	
		
	}
	
	public Cursor eventosPorTipo(String tipo){
		// Retorna um objeto do tipo cursor com o conjunto de IDs dos eventos de um mesmo tipo
		try {
			BancoDados = openOrCreateDatabase(NomeBanco, MODE_WORLD_READABLE, null);
			String sql = "SELECT * FROM tabelaEventos WHERE tipo LIKE '"+tipo+"'";
			cursor = BancoDados.rawQuery(sql, null);
			cursor.moveToFirst();
			return cursor;
			
		} catch (Exception erro) {
			System.out.println(erro);
			return null;
			// retorna 0 caso o email n�o seja encontrado ou algum erro no banco.
		}finally{
			BancoDados.close();
		}	
		
	}
	

	
	public Integer idUsuario(String email){
		
		try {
			BancoDados = openOrCreateDatabase(NomeBanco, MODE_WORLD_READABLE, null);
			String sql = "SELECT _id FROM tabelaUsuarios WHERE email LIKE '"+email+"' ";
			cursor = BancoDados.rawQuery(sql, null);
			cursor.moveToFirst();
			return cursor.getInt(cursor.getPosition());
			
		} catch (Exception erro) {
			System.out.println(erro);
			return null;
			// retorna 0 caso o email n�o seja encontrado ou algum erro no banco.
		}finally{
			BancoDados.close();
		}	
	}
	
	public String senhaUsuario(Integer id){
		
		try {
			BancoDados = openOrCreateDatabase(NomeBanco, MODE_WORLD_READABLE, null);
			String sql = "SELECT senha FROM tabelaUsuarios WHERE _id LIKE '"+id+"' ";
			cursor = BancoDados.rawQuery(sql, null);
			cursor.moveToFirst();
			return cursor.getString(cursor.getPosition());
			
		} catch (Exception erro) {
			System.out.println(erro);
			return null;
		} finally {
			BancoDados.close();
		}	
	}
	public String nomeUsuario(Integer id){
		
		try {
			BancoDados = openOrCreateDatabase(NomeBanco, MODE_WORLD_READABLE, null);
			String sql = "SELECT nome FROM tabelaUsuarios WHERE _id LIKE '"+id+"' ";
			cursor = BancoDados.rawQuery(sql, null);
			cursor.moveToFirst();
			return cursor.getString(cursor.getPosition());
			
		} catch (Exception erro) {
			System.out.println(erro);
			return null;
		} finally {
			BancoDados.close();
		}	
	}
	
	@SuppressWarnings("deprecation")
	public void cadastrar(String nome, Date dataDeNascimento, String email, String senha, String sexo, String eventoFavorito1, String eventoFavorito2, String eventoFavorito3){
		//Cadastra Usu�rio
		//o formato da data � (YYYY-MM-DD)
		try {
			BancoDados = openOrCreateDatabase(NomeBanco, MODE_WORLD_READABLE, null);
			String sql = "INSERT INTO tabelaUsuarios (nome, dataNascimento, email, senha, sexo, eventoFavorito1, eventoFavorito2, eventoFavorito3) VALUE ('"+nome+"','"+dataDeNascimento+"','"+email+"','"+senha+"','"+sexo+"','"+eventoFavorito1+"','"+eventoFavorito2+"',"+eventoFavorito3+")";
			BancoDados.execSQL(sql);
		} catch (Exception erro) {
			// TODO: handle exception
		}finally{
			BancoDados.close();
		}
	}
	
	@SuppressWarnings("deprecation")
	public void cadastrar(String nome, String endereco, Date data, Time hora, String descricao, String tipo){
		// Cadastra evento
		//o formato da data � (YYYY/MM/DD)
		try {
			BancoDados = openOrCreateDatabase(NomeBanco, MODE_WORLD_READABLE, null);
			String sql = "INSERT INTO tabelaEventos (nome, endereco, data, hora, descricao, tipo) VALUE ('"+nome+"','"+endereco+"','"+data+"','"+hora+"','"+descricao+"',"+tipo+"')";
			BancoDados.execSQL(sql);
		} catch (Exception erro) {
			System.out.println(erro);
		}finally{
			BancoDados.close();
		}
	}
	
}
