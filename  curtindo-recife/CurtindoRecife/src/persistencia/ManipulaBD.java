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
	
	public Cursor eventosPorData(String data){
		// Retorna cursor contendo os eventos de uma data em específico
		try {
			BancoDados = openOrCreateDatabase(NomeBanco, MODE_WORLD_READABLE, null);
			String sql = "SELECT * FROM tabelaUsuarios WHERE data LIKE '"+data+"'";
			cursor = BancoDados.rawQuery(sql, null);
			cursor.moveToFirst();
			return cursor;
			
		} catch (Exception erro) {
			System.out.println(erro);
			return null;
			
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
			// retorna 0 caso o email não seja encontrado ou algum erro no banco.
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
			// retorna 0 caso o email não seja encontrado ou algum erro no banco.
		}finally{
			BancoDados.close();
		}	
	}
public Cursor retornaUsuario(Integer id){
		
		try {
			BancoDados = openOrCreateDatabase(NomeBanco, MODE_WORLD_READABLE, null);
			String sql = "SELECT * FROM tabelaUsuarios WHERE _id LIKE '"+id+"' ";
			cursor = BancoDados.rawQuery(sql, null);
			cursor.moveToFirst();
			return cursor;
			
		} catch (Exception erro) {
			System.out.println(erro);
			return null;
		} finally {
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
		//Cadastra Usuário
		//o formato da data é (YYYY-MM-DD)
		try {
			BancoDados = openOrCreateDatabase(NomeBanco, MODE_WORLD_READABLE, null);
			String sql = "INSERT INTO tabelaUsuarios (nome, dataNascimento, email, senha, sexo, eventoFavorito1, eventoFavorito2, eventoFavorito3) VALUES ('"+nome+"','"+dataDeNascimento+"','"+email+"','"+senha+"','"+sexo+"','"+eventoFavorito1+"','"+eventoFavorito2+"',"+eventoFavorito3+")";
			BancoDados.execSQL(sql);
		} catch (Exception erro) {
			// TODO: handle exception
		}finally{
			BancoDados.close();
		}
	}
	
	@SuppressWarnings("deprecation")
	public void cadastrar(int idOwner, String nome, String endereco, int numero, String preco, String data, String hora, String telefone, String descricao, String tipo){
		// Cadastra evento
		
		try {
			BancoDados = openOrCreateDatabase(NomeBanco, MODE_WORLD_READABLE, null);
			String sql = "INSERT INTO tabelaEventos (nome, endereco, numero, preco, data, hora, telefone, descricao, tipo, idOwner) VALUES ('"+nome+"','"+endereco+"','"+numero+"','"+preco+"','"+data+"','"+hora+"','"+telefone+"','"+descricao+"',"+tipo+"','"+idOwner+"')";
			BancoDados.execSQL(sql);
		} catch (Exception erro) {
			System.out.println(erro);
		}finally{
			BancoDados.close();
		}
	}
	
}
