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
	
	public Integer idUsuario(String email){
		try {
			
			BancoDados = openOrCreateDatabase(NomeBanco, MODE_WORLD_READABLE, null);
			String sql = "SELECT _id FROM tabelaUsuario WHERE email LIKE "+email+" ";
			cursor = BancoDados.rawQuery(sql, null);
			return cursor.getInt(1);
			
		} catch (Exception erro) {
			System.out.println(erro);
			return null;
			// retorna 0 caso o email não seja encontrado ou algum erro no banco.
		}finally{
			BancoDados.close();
		}
		
		
	}
	public String senhaUsuario(Integer id){
		try {
			BancoDados = openOrCreateDatabase(NomeBanco, MODE_WORLD_READABLE, null);
			String sql = "SELECT senha FROM tabelaUsuario WHERE _id LIKE "+id+" ";
			cursor = BancoDados.rawQuery(sql, null);
			return cursor.getString(1);
			
		} catch (Exception erro) {
			System.out.println(erro);
			return null;
		} finally {
			BancoDados.close();
		}
		
		
		
	}
	
	@SuppressWarnings("deprecation")
	public void Cadastrar(String nome, Date dataDeNascimento, String email, String senha, String sexo, String eventoFavorito1, String eventoFavorito2, String eventoFavorito3){
		//Cadastra Usuário
		//o formato da data é (YYYY-MM-DD)
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
	public void Cadastrar(String nome, String endereco, Date data, Time hora, String descricao, String tipo){
		// Cadastra evento
		//o formato da data é (YYYY/MM/DD)
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
	@SuppressWarnings("deprecation")
	public boolean checarBD(){
		try {
			BancoDados = openOrCreateDatabase(NomeBanco, MODE_WORLD_READABLE,null);
			String sql = "SELECT * FROM tabelaUsuarios";
			cursor = BancoDados.rawQuery(sql, null);
			if (cursor.getCount() != 0){
				return true;
			}else{
				return false;
			}
		} catch (Exception erro) {
			System.out.println(erro);
			return false;
		}finally{
			System.out.println(checarBD());
			BancoDados.close();
			
		}
	}
}
