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
	
	@SuppressWarnings("deprecation")
	public void Cadastrar(String nome, Date dataNascimento, String email, String senha, Integer sexo){
		//Cadastra Usuário
		//o formato da data é (YYYY-MM-DD)
		try {
			BancoDados = openOrCreateDatabase(NomeBanco, MODE_WORLD_READABLE, null);
			String sql = "INSERT INTO tabelaUsuarios (nome, dataNascimento, email, senha, sexo) VALUE ('"+nome+"','"+dataNascimento+"','"+email+"','"+senha+"','"+sexo+"')";
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
			// TODO: handle exception
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
			// TODO: handle exception
			return false;
		}finally{
			BancoDados.close();
			
		}
	}
}
