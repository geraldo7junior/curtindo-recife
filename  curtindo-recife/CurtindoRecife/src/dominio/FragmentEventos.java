package dominio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collection;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager.Request;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.sax.TextElementListener;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.WebView.FindListener;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import bd.Banco;
import com.br.curtindorecife.R;
import com.br.curtindorecife.TelaCadastroEvento;
import com.br.curtindorecife.TelaPrincipal;
import com.br.curtindorecife.TelaFacebook.WallPostDialogListener.WallPostRequestListener;
import com.facebook.HttpMethod;
import com.facebook.RequestAsyncTask;
import com.facebook.Session;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;
import com.facebook.android.Facebook.DialogListener;

@SuppressWarnings("deprecation")
public class FragmentEventos extends Fragment implements OnClickListener {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		Button btnSimbora, btnMapa;
		TextView txtNomeEvento;
		TextView txtEndereco;
		TextView txtData;
		TextView txtHora;
		TextView txtNumero;
		TextView txtDescricao;
		ImageView imgEvento;
		TextView txtPreco;
		
		private Boolean ehEvento;
		public String nomeEvento;
		public String endereco;
		public String numero;
		public String data;
		public String hora;
		public String telefone;
		public String descricao;
		public String preco;
		public int imagem;
		public int id;
		public int idOwner;
		public boolean curtido;
		public String tipo;
		public int curtidas;
		public int morgadas;
		DialogInterface.OnClickListener dialogClick;
		public static final String ARG_SECTION_NUMBER = "section_number";
		
		private static Facebook facebook;
		private static SharedPreferences prefs;
		private static final String APP_ID = "670005079718273";
		private AsyncFacebookRunner mAsyncRunner;
		private static final String ACCESS_EXPIRES = "access_expires";
		private static final String ACCESS_TOKEN = "access_token";
		

		private Bitmap image;

		String[] salvarimg = new String[0];

		String corpo;

		private int cont = 0;
		
		Estabelecimento estabelecimento;
		public Boolean getEhEvento() {
			return ehEvento;
		}

		public void setEhEvento(Boolean ehEvento) {
			this.ehEvento = ehEvento;
		}

		public FragmentEventos() {
			// TODO Auto-generated constructor stub
		}
		
		public FragmentEventos(Evento evento){
			this.nomeEvento= evento.getNome();
			this.endereco=evento.getEndereco();
			this.data=evento.getData();
			this.numero=evento.getNumero();
			this.hora=evento.getHora();
			this.telefone=evento.getTelefone();
			this.descricao=evento.getDescricao();
			this.preco=evento.getPreco();
			this.id = evento.getId();
			this.idOwner=evento.getIdOwner();
			this.curtido=evento.isCurtido();
			this.tipo=evento.getTipoDeEvento();
			System.out.println(evento.getCurtidas()+" Curtidas");
			this.curtidas=evento.getCurtidas();
			System.out.println(evento.getMorgadas()+" Morgadas");
			this.morgadas=evento.getMorgadas();
			setEhEvento(true);
		}
		public FragmentEventos(Estabelecimento estabelecimento){
			this.estabelecimento=estabelecimento;
			this.nomeEvento= estabelecimento.getNome();
			this.endereco=estabelecimento.getEndereco();
			this.data=estabelecimento.getData();
			this.numero=estabelecimento.getNumero();
			this.hora=estabelecimento.getHoraInicio();
			this.telefone=estabelecimento.getTelefone();
			this.descricao=estabelecimento.getDescricao();
			this.preco=estabelecimento.getPreco();
			this.id = estabelecimento.getId();
			this.idOwner=estabelecimento.getIdOwner();
			this.tipo=estabelecimento.getTipo();
			setEhEvento(false);
		}
		@SuppressWarnings("deprecation")
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(
					R.layout.fragment_tela_eventos_dummy, container, false);
			facebook = new Facebook(APP_ID);
			mAsyncRunner = new AsyncFacebookRunner(facebook);
			getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
				
			//_Relacionamento
			if(!ehEvento){
			if (Usuario.getId()!=0){
					Banco banco = new Banco(getActivity());
					Usuario usuario = banco.getUsuario(Usuario.getId());
					this.curtido = banco.curtido(estabelecimento, usuario);
				}
			}
			
			btnSimbora = (Button) rootView.findViewById(R.id.btnSimbora);
			btnSimbora.setOnClickListener(this);
			
		
			 btnMapa = (Button) rootView.findViewById(R.id.btnMapa);
			 btnMapa.setOnClickListener(this);
			txtNomeEvento = (TextView) rootView.findViewById(R.id.txtTituloEvento);
			txtDescricao = (TextView) rootView.findViewById(R.id.txtCategoria);
			txtEndereco = (TextView) rootView.findViewById(R.id.txtEndereco);
			txtHora = (TextView) rootView.findViewById(R.id.txtHora);
			txtPreco = (TextView) rootView.findViewById(R.id.txtPreco);
			imgEvento = (ImageView)rootView.findViewById(R.id.imgEvento);
			txtData = (TextView) rootView.findViewById(R.id.txtCadastroData);
			

			txtEndereco.setText(this.endereco+", "+this.numero);
			txtNomeEvento.setText(this.nomeEvento);
			if(ehEvento){
				imgEvento.setBackgroundResource(Evento.associeImagem(this.tipo));
			}
			else{
				imgEvento.setBackgroundResource(Estabelecimento.associeImagem(this.tipo));
				
			}
			txtData.setText(this.data);
			txtHora.setText(this.hora);
			txtPreco.setText(this.preco);
			txtDescricao.setText(this.descricao);
			
			
			
			if(this.idOwner==Usuario.getId()){
				btnSimbora.setText("Evento Criado");
				btnSimbora.setBackgroundColor(Color.BLUE);
				btnSimbora.setEnabled(false);
			}
			if(this.curtido==true & !Evento.getAtual().equals("Rolando Agora")){
				btnSimbora.setText("Desistir");
				btnSimbora.setBackgroundColor(Color.BLUE);
				btnSimbora.setEnabled(false);
				
			}else if(this.curtido==true & Evento.getAtual().equals("Rolando Agora")){
				btnSimbora.setText("Curtindo");
				btnSimbora.setBackgroundColor(Color.MAGENTA);
				btnSimbora.setEnabled(true);
				btnMapa.setBackgroundColor(Color.LTGRAY);
				//btnMapa.setBackgroundResource(R.drawable.morgado);
				btnMapa.setText("Morgado");
				btnMapa.setEnabled(true);
			}
			else{
				if(Evento.getAtual().equals("Rolando Agora")){
					btnSimbora.setText("");
					btnSimbora.setBackgroundColor(Color.TRANSPARENT);
					btnSimbora.setEnabled(false);
					
				}
			}
			return rootView;
		}
		
		public void chamarFacebook(){
			//FACEBOOK
			
			prefs = getActivity().getPreferences(getActivity().MODE_PRIVATE);
			// Carrega a accessToken pra saber se o usuário
			// já se autenticou.
			loadAccessToken();
			
			if(!facebook.isSessionValid()){
				
				facebook.authorize(getActivity(), new String[] {"publish_stream","read_stream", "offline_access"}, new MyLoginDialogListener(){

					@Override
					public void onComplete(Bundle values) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onFacebookError(FacebookError e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onError(DialogError e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void onCancel() {
						// TODO Auto-generated method stub
						
					}
					
				});
				
				
			}
		}
		
		public void onActivityResult(  
			    int requestCode, int resultCode, Intent data) {  
			    super.onActivityResult(  
			      requestCode, resultCode, data);  
			    // A API do Facebook exige essa chamada para   
			    // concluir o processo de login.  
			    facebook.authorizeCallback(  
			      requestCode, resultCode, data);  
			  }  
		
		@Override
		public void onClick(final View v) {
			dialogClick = new android.content.DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if(which==dialog.BUTTON_POSITIVE){
						//facebook.dialog(getActivity(), "stream.publish", new DialogPublishFacebook());
						chamarFacebook();
						facebook.dialog(getActivity(), "stream.publish", new WallPostDialogListener());
						//updateStatusClick(v, txtNomeEvento.getText().toString());
					}
					
					
				}
			};
			
			if(v.getId()==R.id.btnSimbora && !btnSimbora.getText().equals("Curtindo") ){
				
					if(Usuario.getId()==0){
						
						AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	
						// 2. Chain together various setter methods to set the dialog characteristics
						builder.setMessage("Para dar Simbora é preciso estar logado.").setPositiveButton("OK", dialogClick);
	
						// 3. Get the AlertDialog from create()
						AlertDialog dialog = builder.create();
						dialog.show();
					}else{
						
							if(getEhEvento()){
								Banco banco = new Banco(getActivity());
								banco.darSimbora(this.id);
								AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			
								// 2. Chain together various setter methods to set the dialog characteristics
								builder.setMessage("Simbora realizado com sucesso. Compartilhar?").setTitle("Sucesso!").setPositiveButton("Sim", dialogClick).setNegativeButton("Não", dialogClick);
			
								// 3. Get the AlertDialog from create()
								AlertDialog dialog = builder.create();
								dialog.show();
							}else{
								Banco banco = new Banco(getActivity());
								Estabelecimento estabelecimento = banco.retornaEstabelecimento(this.id);
								Usuario usuario = banco.getUsuario(Usuario.getId());
								banco.darSimbora(estabelecimento, usuario);
								
								AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
								builder.setMessage("Simbora realizado com sucesso.").setTitle("Sucesso!").setPositiveButton("OK", dialogClick);
								AlertDialog dialog = builder.create();
								dialog.show();
								}
							
						}
				
			}if(v.getId() == R.id.btnSimbora && btnSimbora.getText().equals("Curtindo")){
				Banco banco = new Banco(getActivity());
				banco.updateCurtidasAndMorgadas(id, curtidas+1, null);
				banco.updateVotou(Usuario.getId(), id);
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setMessage("Curtida realizada com sucesso.").setTitle("Sucesso!").setPositiveButton("OK", dialogClick);
				AlertDialog dialog = builder.create();
				dialog.show();
				Intent intent = new Intent(getActivity(), TelaPrincipal.class);
				startActivity(intent);
				
			}if(v.getId() == R.id.btnMapa && btnMapa.getText().equals("Morgado") ){
				Banco banco = new Banco(getActivity());
				banco.updateCurtidasAndMorgadas(id, null, morgadas+1);
				AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
				builder.setMessage("Evento Morgado.").setTitle("Morgado").setPositiveButton("OK", dialogClick);
				AlertDialog dialog = builder.create();
				dialog.show();
				Intent intent = new Intent(getActivity(), TelaPrincipal.class);
				startActivity(intent);
			}
			
			
			
			
		}
		
		private void loadAccessToken() {
			String access_token = prefs.getString(ACCESS_TOKEN, null);
			long expires = prefs.getLong(ACCESS_EXPIRES, 0);
			if (access_token != null) {
				facebook.setAccessToken(access_token);
			}
			if (expires != 0) {
				facebook.setAccessExpires(expires);
			}
		}
		
		public void updateStatusClick(View v, String nomeEvento){  
		    updateStatus(nomeEvento);   
		  }  
		  @SuppressWarnings("deprecation")
		private RequestListener requestListener =   
		    new RequestListener() {  
		      public void onMalformedURLException(  
		        MalformedURLException e, Object state) {  
		        showToast("URL mal formada");  
		      }  
		      public void onIOException(  
		        IOException e, Object state) {  
		        showToast("Problema de comunicação");  
		      }  
		      public void onFileNotFoundException(  
		        FileNotFoundException e, Object state) {  
		        showToast("Recurso não existe");  
		      }  
		      public void onFacebookError(  
		        FacebookError e, Object state) {  
		        showToast("Erro no Facebook: "+   
		          e.getLocalizedMessage());  
		      }  
		      public void onComplete(  
		        String response, Object state) {  
		        showToast("Status atualizado");  
		      }
			
		    };  
		  
		  // Método que efetivamente atualiza o status  
		  @SuppressWarnings("deprecation")
		private void updateStatus(String status) {  
		    AsyncFacebookRunner runner =   
		      new AsyncFacebookRunner(facebook);  
		    
		    Bundle params = new Bundle(); 
		    params.putString("message", status);  
		    runner.request("me/feed", params, "POST",   
		      requestListener, null);  
		  }  
		   
		  private void showToast(final String s){  
		    final Context ctx = getActivity();  
		    getActivity().runOnUiThread(new Runnable() {  
		      public void run() {  
		        Toast.makeText(ctx, s,   
		          Toast.LENGTH_SHORT).show();  
		      }  
		    });  
		  }  
		  
		  public static void saveAccessToken() {  
		    SharedPreferences.Editor editor = prefs.edit();  
		    editor.putString(  
		      ACCESS_TOKEN, FragmentEventos.facebook.getAccessToken());  
		    editor.putLong(  
		      ACCESS_EXPIRES, facebook.getAccessExpires());  
		    editor.commit();  
		  } 
		  
		  public class WallPostDialogListener implements
		    com.facebook.android.Facebook.DialogListener {

		/**
		 * Called when the dialog has completed successfully
		 */
		public void onComplete(Bundle values) {
		    final String postId = values.getString("post_id");
		    if (postId != null) {
		        Log.d("FB Sample App", "Dialog Success! post_id=" + postId);
		        mAsyncRunner.request(postId, new WallPostRequestListener());
		    } else {
		        Log.d("FB Sample App", "No wall post made");
		    }
		    
		    Intent intent=new Intent(getActivity(), TelaPrincipal.class);
			startActivity(intent);
		}

		@Override
		public void onCancel() {
		    // No special processing if dialog has been canceled
		}

		@Override
		public void onError(DialogError e) {
		    // No special processing if dialog has been canceled
		}

		@Override
		public void onFacebookError(FacebookError e) {
		    // No special processing if dialog has been canceled
		}

		public class WallPostRequestListener implements
		com.facebook.android.AsyncFacebookRunner.RequestListener {

		/**
		* Called when the wall post request has completed
		*/
		public void onComplete(final String response) {
		Log.d("Facebook-Example", "Got response: " + response);
		}

		public void onFacebookError(FacebookError e) {
		// Ignore Facebook errors
		}

		public void onFileNotFoundException(FileNotFoundException e) {
		// Ignore File not found errors
		}

		public void onIOException(IOException e) {
		// Ignore IO Facebook errors
		}

		@Override
		public void onComplete(String response, Object state) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onIOException(IOException e, Object state) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onFileNotFoundException(FileNotFoundException e, Object state) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onMalformedURLException(MalformedURLException e, Object state) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onFacebookError(FacebookError e, Object state) {
			// TODO Auto-generated method stub
			
		}


		}


	}

class MyLoginDialogListener implements com.facebook.android
.Facebook.DialogListener {

public void onComplete(Bundle values) {} // here enable logout
public void onFacebookError(FacebookError error) {}
public void onError(DialogError error) {}
public void onCancel() {}
}
	

class DialogPublishFacebook implements com.facebook.android.Facebook.DialogListener {

	    public void onComplete(Bundle values) {

	        final String postId = values.getString("post_id");

	        if (postId != null) {

	            // "Wall post made..."

	        } else {
	            // "No wall post made..."
	        }

	    }

	    public void onFacebookError(FacebookError e) {}
	    public void onError(DialogError e) {}
	    public void onCancel() {}

	}
}
