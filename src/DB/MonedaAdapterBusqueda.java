package DB;




import java.util.ArrayList;
import java.util.List;

import com.vengapps.divisas.R;
import com.vengapps.divisas.R.id;
import com.vengapps.divisas.R.layout;


import Beans.BeansUsuario;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Locale;

public class MonedaAdapterBusqueda<monedas> extends ArrayAdapter<BeansUsuario> {

	private Context context;
	private List<BeansUsuario> monedaslist = null;
	private ArrayList<BeansUsuario> monedasarr;

	public MonedaAdapterBusqueda(Context context, int viewResourceId, ArrayList<BeansUsuario> monedas) {
		super(context, viewResourceId, monedas);
		this.monedaslist = monedas;
		this.context = context;
		this.monedasarr = monedas;
		//this.monedasarr = new ArrayList<BeansUsuario>();
       // this.monedasarr.addAll(monedaslist);
		
	}
	
	
	

	



	static class ViewHolder{
		public ImageView avatar;
		public TextView name;
		public TextView symbol;
		public TextView price;
		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		/* 
		 * Android Adapter Good Practices:
		 * http://www.piwai.info/android-adapter-good-practices/
		 */
		if (convertView == null) {
			
			convertView = LayoutInflater.from(context).inflate(R.layout.itemlista, parent, false);
			
			ViewHolder viewHolder = new ViewHolder();
			//viewHolder.avatar = (ImageView) convertView.findViewById(R.id.avatar);
			viewHolder.name = (TextView) convertView.findViewById(R.id.nameMon);
			viewHolder.symbol = (TextView) convertView.findViewById(R.id.symbol);
			viewHolder.price = (TextView) convertView.findViewById(R.id.price);
			convertView.setTag(viewHolder);
		}
		
		ViewHolder holder = (ViewHolder) convertView.getTag();
		//Log.e("Valor para texto",monedasarr.get(position).getSymbol());
		holder.name.setText(monedasarr.get(position).getName());
		holder.symbol.setText(monedasarr.get(position).getSymbol());
		holder.price.setText(monedasarr.get(position).getPrice());
	   
		
		return convertView;
	}
	
	
	 // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        monedasarr.clear();
        if (charText.length() == 0) {
        	monedasarr.addAll(monedaslist);
        } 
        else 
        {
           for (BeansUsuario wp : monedasarr)
        	//for(int z=0; z<monedaslist.size();z++)
            {
                if ( wp.getSymbol().toLowerCase(Locale.getDefault()).contains(charText))
        		//if( monedaslist.get(z).getSymbol().toLowerCase(Locale.getDefault()).contains(charText))
                {
                	//monedaslist.addAll(monedaslist);
                	monedaslist.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

	
	
	
}
