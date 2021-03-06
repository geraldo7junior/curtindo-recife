package dominio;

import java.util.List;

import com.br.curtindorecife.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
 
public class CustomAdapterEstabelecimento extends ArrayAdapter<Estabelecimento> {
 
    /*
     * Used to instantiate layout XML file into its corresponding View objects
     */
    private final LayoutInflater inflater;
 
    /*
     * each list item layout ID
     */
    private final int resourceId;
 
    public CustomAdapterEstabelecimento(Context context, int resource, List<Estabelecimento> objects) {
        super(context, resource, objects);
        this.inflater = LayoutInflater.from(context);
        this.resourceId = resource;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	
       
 
        // get a new View no matter recycling or ViewHolder FIXME
        convertView = inflater.inflate(resourceId, parent, false);
 
        //get all object from view
        ImageView image = (ImageView) convertView.findViewById(R.id.image);
        TextView nome = (TextView) convertView.findViewById(R.id.tv1);
        TextView endereco = (TextView) convertView.findViewById(R.id.tv2);
        TextView telefone = (TextView) convertView.findViewById(R.id.tv3);
        TextView tipo= (TextView) convertView.findViewById(R.id.tvTipoEvento);
        TextView simboras=(TextView) convertView.findViewById(R.id.tvSimboras);
        
        
        //get the Estabelecimento from position
        Estabelecimento estabelecimento = getItem(position);
 
        //fill the view objects according values from Estabelecimento object
        nome.setText(estabelecimento.getNome());
        endereco.setText(estabelecimento.getEndereco());
        telefone.setText(estabelecimento.getTelefone());
        image.setBackgroundResource(Estabelecimento.associeImagem(estabelecimento.getTipo()));
        if(estabelecimento.getIdOwner()==Usuario.getId()){
        	tipo.setText("Evento Criado");
        }
        /*else{
        	if(estabelecimento.isCurtido()){
        		tipo.setText("Evento Curtido");
        	}
        }*/
        
        simboras.setText(estabelecimento.getSimboras()+"  Simboras");    
        
        return convertView;
    }
 
}
