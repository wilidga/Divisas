package DB;

import java.util.ArrayList;
import java.util.Locale;

import Beans.BeansUsuario;
import DB.MonedaAdapter.ViewHolder;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.vengapps.divisas.R;

public class MonedasAdapter2 extends ArrayAdapter<BeansUsuario> {
	MonedaHelper admin;

	private Context context;
	private ArrayList<BeansUsuario> monedas;
	int[] banderas={
			R.drawable.aed,
			R.drawable.afn,
			R.drawable.all,
			R.drawable.amd,
			R.drawable.ang,
			R.drawable.aoa,
			R.drawable.ars,
			R.drawable.aud,
			R.drawable.awg,
			R.drawable.bam,
			R.drawable.bbd,
			R.drawable.bdt,
			R.drawable.bgn,
			R.drawable.bhd,
			R.drawable.bif,
			R.drawable.bmd,
			R.drawable.bnd,
			R.drawable.bob,
			R.drawable.brl,
			R.drawable.bsd,
			R.drawable.btn,
			R.drawable.bwp,
			R.drawable.byr,
			R.drawable.bzd,
			R.drawable.cad,
			R.drawable.cdf,
			R.drawable.cef,
			R.drawable.chf,
			R.drawable.clp,
			R.drawable.cnh,
			R.drawable.cny,
			R.drawable.config,
			R.drawable.cop,
			R.drawable.crc,
			R.drawable.cup,
			R.drawable.cve,
			R.drawable.czk,
			R.drawable.djf,
			R.drawable.dkk,
			R.drawable.dop,
			R.drawable.dzd,
			R.drawable.egp,
			R.drawable.ern,
			R.drawable.etb,
			R.drawable.eur,
			R.drawable.fjd,
			R.drawable.fkp,
			R.drawable.gbp,
			R.drawable.gel,
			R.drawable.ghs,
			R.drawable.gip,
			R.drawable.gmd,
			R.drawable.gnf,
			R.drawable.gtq,
			R.drawable.gyd,
			R.drawable.hkd,
			R.drawable.hnl,
			R.drawable.hrk,
			R.drawable.htg,
			R.drawable.huf,
			R.drawable.ic_action_search,
			R.drawable.ic_launcher,
			R.drawable.idr,
			R.drawable.iep,
			R.drawable.ils,
			R.drawable.inr,
			R.drawable.iqd,
			R.drawable.irr,
			R.drawable.isk,
			R.drawable.jmd,
			R.drawable.jod,
			R.drawable.jpy,
			R.drawable.kes,
			R.drawable.kgs,
			R.drawable.khr,
			R.drawable.kmf,
			R.drawable.kpw,
			R.drawable.krw,
			R.drawable.kwd,
			R.drawable.kyd,
			R.drawable.kzt,
			R.drawable.lak,
			R.drawable.lbp,
			R.drawable.lkr,
			R.drawable.lrd,
			R.drawable.lsl,
			R.drawable.ltl,
			R.drawable.lvl,
			R.drawable.lyd,
			R.drawable.mad,
			R.drawable.mdl,
			R.drawable.mga,
			R.drawable.mkd,
			R.drawable.mnt,
			R.drawable.mop,
			R.drawable.mro,
			R.drawable.mur,
			R.drawable.mvr,
			R.drawable.mwk,
			R.drawable.mxn,
			R.drawable.mxv,
			R.drawable.myr,
			R.drawable.mzn,
			R.drawable.nad,
			R.drawable.ngn,
			R.drawable.nio,
			R.drawable.nok,
			R.drawable.npr,
			R.drawable.nzd,
			R.drawable.omr,
			R.drawable.pab,
			R.drawable.pen,
			R.drawable.pgk,
			R.drawable.php,
			R.drawable.pkr,
			R.drawable.pln,
			R.drawable.pyg,
			R.drawable.qar,
			R.drawable.refresh,
			R.drawable.ron,
			R.drawable.rsd,
			R.drawable.rub,
			R.drawable.rwf,
			R.drawable.sar,
			R.drawable.sbd,
			R.drawable.scr,
			R.drawable.sdg,
			R.drawable.sek,
			R.drawable.settings,
			R.drawable.sgd,
			R.drawable.shp,
			R.drawable.sll,
			R.drawable.sos,
			R.drawable.srd,
			R.drawable.std,
			R.drawable.svc,
			R.drawable.syp,
			R.drawable.szl,
			R.drawable.thb,
			R.drawable.tjs,
			R.drawable.tmt,
			R.drawable.tnd,
			R.drawable.top,
			R.drawable.try1,
			R.drawable.ttd,
			R.drawable.twd,
			R.drawable.tzs,
			R.drawable.uah,
			R.drawable.ugx,
			R.drawable.usd,
			R.drawable.uyu,
			R.drawable.uzs,
			R.drawable.vef,
			R.drawable.vnd,
			R.drawable.vuv,
			R.drawable.wst,
			R.drawable.xaf,
			R.drawable.xag,
			R.drawable.xau,
			R.drawable.xcd,
			R.drawable.xcp,
			R.drawable.xdr,
			R.drawable.xof,
			R.drawable.xpd,
			R.drawable.xpf,
			R.drawable.yer,
			R.drawable.zar,
			R.drawable.zmw,
			R.drawable.zwl
	};
	
	String[] flags= {"aed",
			"afn",
			"all",
			"amd",
			"ang",
			"aoa",
			"ars",
			"aud",
			"awg",
			"bam",
			"bbd",
			"bdt",
			"bgn",
			"bhd",
			"bif",
			"bmd",
			"bnd",
			"bob",
			"brl",
			"bsd",
			"btn",
			"bwp",
			"byr",
			"bzd",
			"cad",
			"cdf",
			"cef",
			"chf",
			"clp",
			"cnh",
			"cny",
			"config",
			"cop",
			"crc",
			"cup",
			"cve",
			"czk",
			"djf",
			"dkk",
			"dop",
			"dzd",
			"egp",
			"ern",
			"etb",
			"eur",
			"fjd",
			"fkp",
			"gbp",
			"gel",
			"ghs",
			"gip",
			"gmd",
			"gnf",
			"gtq",
			"gyd",
			"hkd",
			"hnl",
			"hrk",
			"htg",
			"huf",
			"ic_action_search",
			"ic_launcher",
			"idr",
			"iep",
			"ils",
			"inr",
			"iqd",
			"irr",
			"isk",
			"jmd",
			"jod",
			"jpy",
			"kes",
			"kgs",
			"khr",
			"kmf",
			"kpw",
			"krw",
			"kwd",
			"kyd",
			"kzt",
			"lak",
			"lbp",
			"lkr",
			"lrd",
			"lsl",
			"ltl",
			"lvl",
			"lyd",
			"mad",
			"mdl",
			"mga",
			"mkd",
			"mnt",
			"mop",
			"mro",
			"mur",
			"mvr",
			"mwk",
			"mxn",
			"mxv",
			"myr",
			"mzn",
			"nad",
			"ngn",
			"nio",
			"nok",
			"npr",
			"nzd",
			"omr",
			"pab",
			"pen",
			"pgk",
			"php",
			"pkr",
			"pln",
			"pyg",
			"qar",
			"refresh",
			"ron",
			"rsd",
			"rub",
			"rwf",
			"sar",
			"sbd",
			"scr",
			"sdg",
			"sek",
			"settings",
			"sgd",
			"shp",
			"sll",
			"sos",
			"srd",
			"std",
			"svc",
			"syp",
			"szl",
			"thb",
			"tjs",
			"tmt",
			"tnd",
			"top",
			"try1",
			"ttd",
			"twd",
			"tzs",
			"uah",
			"ugx",
			"usd",
			"uyu",
			"uzs",
			"vef",
			"vnd",
			"vuv",
			"wst",
			"xaf",
			"xag",
			"xau",
			"xcd",
			"xcp",
			"xdr",
			"xof",
			"xpd",
			"xpf",
			"yer",
			"zar",
			"zmw",
			"zwl"};
	

	public MonedasAdapter2(Context context, int viewResourceId, ArrayList<BeansUsuario> monedas) {
		super(context, viewResourceId, monedas);
		this.context = context;
		this.monedas = monedas;
	}
	
	
	
	
	static class ViewHolder{
		public ImageView avatar;
		public TextView name;
		public TextView symbol;
		public TextView price;
		public ToggleButton toggle;
		
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent){
		/* 
		 * Android Adapter Good Practices:
		 * http://www.piwai.info/android-adapter-good-practices/
		 */
		if (convertView == null) {
			
			convertView = LayoutInflater.from(context).inflate(R.layout.itemlista2, parent, false);
			
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.avatar = (ImageView) convertView.findViewById(R.id.foto);
			viewHolder.name = (TextView) convertView.findViewById(R.id.nameMon);
			viewHolder.symbol = (TextView) convertView.findViewById(R.id.symbol);
			viewHolder.toggle=(ToggleButton)convertView.findViewById(R.id.tgluno);
			//viewHolder.price = (TextView) convertView.findViewById(R.id.price);
			convertView.setTag(viewHolder);
		}
		
		final ViewHolder holder = (ViewHolder) convertView.getTag();
		//Log.e("Valor para texto",monedas.get(position).getSymbol());
		holder.avatar.setImageResource(banderas[getItemFlag(monedas.get(position).getSymbol())]);
		holder.name.setText(monedas.get(position).getName());
		holder.symbol.setText(monedas.get(position).getSymbol());
		if (monedas.get(position).getSn_habilita().equals("-1")){
			holder.toggle.setChecked(true);
			
		}else{
			
			holder.toggle.setChecked(false);
		}
			
		//Log.e("Valor para texto",String.valueOf(position));	
		//Log.e("Valor para texto",String.valueOf(position));
		
		//holder.price.setText(monedas.get(position).getPrice());
		
		
		//holder.toggle.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		holder.toggle.setOnClickListener(new OnClickListener(){
		        public void onClick(View v) {
		       	 admin = new MonedaHelper(context);
		       	 SQLiteDatabase bd = admin.getWritableDatabase();
		       	 ContentValues registro = new ContentValues();
		            if (holder.toggle.isChecked()){		
		            	
		            	//Log.e("valor","true");
		            	//Log.e("position",String.valueOf(position));
		                //Log.e("getSymbol",monedas.get(position).getSymbol());
		            	registro.put("sn_habilita", "-1");
		            }else{
		            	//Log.e("valor","false");
		            	//Log.e("position",String.valueOf(position));
		                //Log.e("getSymbol",monedas.get(position).getSymbol());
		            	registro.put("sn_habilita", "0");
		            	
		            }
		            bd.update("tdivisas", registro, "symbol='" + monedas.get(position).getSymbol()+"'", null);
				    bd.close();
				     
		            	
		            }

				
		        
		    });
		          

		return convertView;
	}
	
	
	
	
	
	public int getItemFlag(String sSymbol){
		//int i;	
		for(int j=0; j<flags.length;j++){
			
			
			if(flags[j].equals(sSymbol.toLowerCase())){
				
				return j;
			}			
		}
		return  1;
	}
	
	
	
	
}

