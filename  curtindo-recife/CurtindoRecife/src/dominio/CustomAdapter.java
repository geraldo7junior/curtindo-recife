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
 
public class CustomAdapter extends ArrayAdapter<Evento> {
 
    /*
     * Used to instantiate layout XML file into its corresponding View objects
     */
    private final LayoutInflater inflater;
 
    /*
     * each list item layout ID
     */
    private final int resourceId;
 
    public CustomAdapter(Context context, int resource, List<Evento> objects) {
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
        TextView data = (TextView) convertView.findViewById(R.id.tv2);
        TextView hora = (TextView) convertView.findViewById(R.id.tv3);
        TextView tipo= (TextView) convertView.findViewById(R.id.tvTipoEvento);
        TextView simboras=(TextView) convertView.findViewById(R.id.tvSimboras);
        
        
        //get the Evento from position
        Evento evento = getItem(position);
 
        //fill the view objects according values from Evento object
        nome.setText(evento.getNome());
        data.setText(evento.getData());
        hora.setText(evento.getHora());
        image.setBackgroundResource(Evento.associeImagem(evento.getTipoDeEvento()));
        if(evento.getIdOwner()==Usuario.getId()){
        	tipo.setText("Evento Criado");
        }
        else{
        	if(evento.isCurtido()){
        		tipo.setText("Evento Curtido");
        	}
        }
        simboras.setText(evento.getSimboras()+" Simboras");     
        
        return convertView;
    }
 
}
