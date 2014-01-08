package bd;

import java.sql.Date;
import java.sql.Time;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;

public class Banco extends Activity{
	
	String NomeBanco = "CurtindoRecifeDB";
	SQLiteDatabase BancoDados = null;
	
	@SuppressWarnings("deprecation")
	public void CriarBanco() {
		try {
		BancoDados = openOrCreateDatabase(NomeBanco, MODE_WORLD_READABLE, null);
		String sql = "CREATE TABLE IF NOT EXIST tabelaUsuarios (_id INTEGER, nome TEXT, dataNascimento DATE, email TEXT, senha TEXT, sexo TEXT, eventoFavorito1 TEXT, eventoFavorito2 TEXT, eventoFavorito3 TEXT)";
		BancoDados.execSQL(sql);
		String sqlEvento = "CREATE TABLE IF NOT EXIST tabelaEventos (_id INTEGER, nome TEXT, endereco TEXT, data DATE, hora TIME, descricao TEXT, tipo TEXT)";
		BancoDados.execSQL(sqlEvento);
		} catch (Exception erro) {
			// TODO: handle exception
		}finally{
			BancoDados.close();
		}
	}
	
}
