package dominio;

import java.util.ArrayList;

import com.br.curtindorecife.TelaCadastroEvento;
import com.br.curtindorecife.TelaPrincipal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

public class Mensagem extends AlertDialog implements android.content.DialogInterface.OnClickListener{
	private Context telaAtual;
	private Class<?> telaQueVai;
	private String mensagen;
	private String tituloMensagem;
	public static int dias;
	public static boolean clickData=false;
	public static ArrayList<Evento>listaData=new ArrayList<Evento>();
	android.content.DialogInterface.OnClickListener dialogClick;
	
	
	private Context getTelaAtual() {
		return telaAtual;
	}

	private void setTelaAtual(Context telaAtual) {
		this.telaAtual = telaAtual;
	}

	private Class<?> getTelaQueVai() {
		return telaQueVai;
	}

	private void setTelaQueVai(Class<?> telaQueVai) {
		this.telaQueVai = telaQueVai;
	}

	private String getMensagen() {
		return mensagen;
	}

	private void setMensagen(String mensagen) {
		this.mensagen = mensagen;
	}

	private String getTituloMensagem() {
		return tituloMensagem;
	}

	private void setTituloMensagem(String tituloMensagem) {
		this.tituloMensagem = tituloMensagem;
	}

	protected Mensagem(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(getTelaAtual(), getTelaQueVai());
		getTelaAtual().startActivity(intent);
	}
	private void mensagemDeAlerta(String mensagem, String tituloMensagem, Context telaAtual){
		AlertDialog.Builder builder = new AlertDialog.Builder(telaAtual);
		builder.setMessage(mensagem).setTitle(tituloMensagem).setPositiveButton("OK", dialogClick);
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	public void criarMenssagem(){
		mensagemDeAlerta(getMensagen(), getTituloMensagem(), getTelaAtual());
	}
	
	
}
