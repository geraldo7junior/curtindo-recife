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
		String sql = "CREATE TABLE IF NOT EXISTS tabelaUsuarios (_id INTEGER PRIMARY KEY, nome TEXT, dataNascimento TEXT, email TEXT, senha TEXT, sexo TEXT, eventoFavorito1 TEXT, eventoFavorito2 TEXT, eventoFavorito3 TEXT)";
		BancoDados.execSQL(sql);
		String sqlEvento = "CREATE TABLE IF NOT EXISTS tabelaEventos (_id INTEGER PRIMARY KEY, nome TEXT, endereco TEXT, numero INTEGER, preco TEXT,data TEXT, hora TEXT, telefone TEXT, descricao TEXT, tipo TEXT, idOwner INTEGER)";
		BancoDados.execSQL(sqlEvento);
		String sqlMeusEventos = "CREATE TABLE IF NOT EXISTS tabelaMeusEventos (_id INTEGER PRIMARY KEY, idUsuario INTEGER, idEvento INTEGER)";
		BancoDados.execSQL(sqlMeusEventos);
		} catch (Exception erro) {
			// TODO: handle exception
		}finally{
			BancoDados.close();
		}
	}
	
}
