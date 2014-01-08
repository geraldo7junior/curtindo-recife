package persistencia;

import android.app.Activity;
import java.sql.Date;
import java.sql.Time;
import android.database.sqlite.SQLiteDatabase;

public class ManipulaBD extends Activity{
	
	String NomeBanco = "CurtindoRecifeDB";
	SQLiteDatabase BancoDados = null;
	
	@SuppressWarnings("deprecation")
	public void Cadastrar(String nome, Date dataNascimento, String email, String senha, Integer sexo){
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
	public void Cadastrar(String nome, String endereco, Date data, Time hora, String descricao, Integer tipo){
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
}
