package bd;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Banco{
	
	public  Banco(Context context){
		this.context = context;
	}
	
	private final String nomeTabela1 = "tabelaUsuarios";
	private final String nomeTabela2 ="tabelaEventos";
	private final String nomeTabela3 ="tabelaMeusEventos";
	private final String nomeBanco = "CurtindoRecifeDB";
	private final int versaoBD = 1;
	private final Context context;
	private BDhelper bdHelper;
	private SQLiteDatabase bancoDados;
	
	
	class BDhelper extends SQLiteOpenHelper{

		public BDhelper(Context context) {
			super(context, nomeBanco, null, versaoBD);
			// TODO Auto-generated constructor stb
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
				//TABELA DE USUÁRIOS (tabelaUsuarios)
				String sql = "CREATE TABLE "+nomeTabela1+" (_id INTEGER PRIMARY KEY, nome TEXT, dataNascimento TEXT, email TEXT, senha TEXT, sexo TEXT, eventoFavorito1 TEXT, eventoFavorito2 TEXT, eventoFavorito3 TEXT)";
				db.execSQL(sql);
				//TABELA DE EVENTOS (tabelaEventos)
				String sqlEvento = "CREATE TABLE "+nomeTabela2+" (_id INTEGER PRIMARY KEY, nome TEXT, endereco TEXT, numero TEXT, preco TEXT,data TEXT, hora TEXT, telefone TEXT, descricao TEXT, tipo TEXT, idOwner INTEGER, simboras INTEGER, idImagem INTEGER)";
				db.execSQL(sqlEvento);
				//TABELA DOS EVENTOS CRIADOS E QUE O USUÁRIO DEU SIMBORA (tabelMeusEventos)
				String sqlMeusEventos = "CREATE TABLE "+nomeTabela3+" (_id INTEGER PRIMARY KEY, idUsuario INTEGER, idEvento INTEGER)";
				db.execSQL(sqlMeusEventos);
				
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			
				String sql ="DROP TABLE IF EXISTS "+nomeTabela1;
				db.execSQL(sql);
				
				String sql2 = "DROP TABLE IF EXISTS "+ nomeTabela2;
				db.execSQL(sql2);
				
				String sql3 = "DROP TABLE IF EXISTS "+ nomeTabela3;
				db.execSQL(sql3);
			
				onCreate(db);
	
		}
		
		public BDhelper openBd(){
			bdHelper= new BDhelper(context);
			bancoDados = bdHelper.getWritableDatabase();
			return this;
		}
		public void closeBd(){
			bancoDados.close();
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
			
			bancoDados.insert(nomeTabela1, null, valores);	
		}
		
	
		/*public void getTabelaUsuarios() {
			String [] columns = {"nome", "dataNascimento", "email", "senha", "sexo", "eventoFavorito1", "eventoFavorito2", "eventoFavorito3"};
			Cursor cursor = bancoDados.query(nomeTabela1, columns, null, null, null, null, null, null);
			
			ArrayList<String> result = new ArrayList<String>();
			
			for (cursor.moveToFirst(); !cursor.isAfterLast();cursor.moveToNext()){
				
			}
		}*/
		
	}
}
