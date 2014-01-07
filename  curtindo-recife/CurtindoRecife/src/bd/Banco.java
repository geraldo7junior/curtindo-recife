package bd;

import java.sql.Date;
import java.sql.Time;
import android.database.Cursor;
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;

public class Banco extends Activity{
	
	String NomeBanco = "CurtindoRecifeDB";
	SQLiteDatabase BancoDados = null;
	
	@SuppressWarnings("deprecation")
	public void CriarBanco() {
		try {
		BancoDados = openOrCreateDatabase(NomeBanco, MODE_WORLD_READABLE, null);
		String sql = "CREATE TABLE IF NOT EXIST tabelaUsuarios (_id INTEGER, nome TEXT, dataNascimento DATE, email TEXT, senha TEXT, sexo INTEGER)";
		BancoDados.execSQL(sql);
		String sqlEvento = "CREATE TABLE IF NOT EXIST tabelaEventos (_id INTEGER, nome TEXT, endereco TEXT, data DATE, hora TIME, descricao TEXT)";
		BancoDados.execSQL(sqlEvento);
		} catch (Exception erro) {
			// TODO: handle exception
		}finally{
			BancoDados.close();
		}
	}
	@SuppressWarnings("deprecation")
	public void Cadastrar(String nome, Date dataNascimento, String email, String senha, Integer sexo){
		//o formato da data � (YYYY-MM-DD)
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
	public void Cadastrar(String nome, String endereco, Date data, Time hora, String descricao){
		//o formato da data � (YYYY/MM/DD)
		try {
			BancoDados = openOrCreateDatabase(NomeBanco, MODE_WORLD_READABLE, null);
			String sql = "INSERT INTO tabelaEventos (nome, endereco, data, hora, descricao) VALUE ('"+nome+"','"+endereco+"','"+data+"','"+hora+"','"+descricao+"')";
			BancoDados.execSQL(sql);
		} catch (Exception erro) {
			// TODO: handle exception
		}finally{
			BancoDados.close();
		}
	}
}
