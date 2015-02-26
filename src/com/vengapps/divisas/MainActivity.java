package com.vengapps.divisas;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import Beans.BeansUsuario;
import DB.DBOperations;
import DB.MonedaAdapter;
import DB.MonedaHelper;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings.Secure;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;



import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;
import com.actionbarsherlock.view.SubMenu;
import com.actionbarsherlock.widget.ShareActionProvider;
import com.google.android.vending.licensing.AESObfuscator;
import com.google.android.vending.licensing.ServerManagedPolicy;
import com.google.ads.*;






public class MainActivity extends SherlockActivity implements OnItemClickListener {

	 private static final String CLAVE_PUBLICA_LICENCIA = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAg3P0MhtnTUBcwvyhyztEI0VC5foSO3aMhvPO2+geByQ8JxO85OI+6fDE+kFW5lIvLEbnv6ICrEypnfCC/13EEZ6yjDsDWZ/1+H5fAtg9Mbz+Pt4lqJmRGuSwvRte/M7N3xU63J3RF+EmVC1lAZtPxzTXeF43mrgUN3U2joGb2m42YZvRE3SmkgVC/rSFUwTROuPnKQ9t2i1RrmSYS3qjxPPxaQ0i7+4ed2wqLEdEBStcf1Hn2YPITiqot5qhVOUxE5CO4MQYXyVHjQ93GlMoDuMfpOyblSjW3wlJEUG3ESRT64kbJQ6x6PMpcUjo6Psxi+LosbY7hVJJC9YV/8byCwIDAQAB";
	 private AdView adView;
	    // Genera 20 bytes aleatorios, y reemplazalos por los siguientes.

	    private static final byte[] SALT = new byte[] {-46, 65, 30,-128,-103,
	                 -57, 74,-64, 51, 88,-95,-45, 77,-117,-36,-113,-11, 32,-64, 89};
	
	
	
	ArrayList<BeansUsuario> lista = new ArrayList<BeansUsuario>();
	BeansUsuario usuario;
	Context context;
	boolean isConnected,Isnew;
	
	public boolean isIsnew() {
		return Isnew;
	}

	public void setIsnew(boolean isnew) {
		Isnew = isnew;
	}

	private DBOperations dbAdapter;
    private Cursor cursor;
    Boolean bActualiza;
    TextView MonName,MonPrice;
    EditText Monto;
    ImageView image;
	private ProgressDialog dialogo;
	private String shareTitle1,shareUrl1;
	ActualizarMonedas Act = new ActualizarMonedas();
	MonedaHelper admin;
	MonedaAdapter adapter;
	ListView Listmon;
	 String[] arrayItemsNames = {"Dirham Emiratos Árabes Unidos","Afgani afgano","Lek albanés","Dram armenio","Florín antillano neerlandés",
			 "Kwanza angoleño","Peso argentino","Dólar australiano","Florín arubeño","Manat azerbaiyano","Marco convertible de Bosnia-Herzegovina",
			 "Dólar de Barbados", "Taka de Bangladés","Lev búlgaro","Dinar bahreiní","Franco burundés", "Dólar de Bermuda","Dólar de Brunéi",
			 "Boliviano", "Mvdol boliviano (código de fondos)", "Real brasileño","Dólar bahameño","Ngultrum de Bután","Pula de Botsuana",
			 "Rublo bielorruso","Dólar de Belice","Dólar canadiense","Franco congoleño, o congolés","Franco suizo","Unidades de fomento chilenas (código de fondos)",
			 "Peso chileno","Yuan chino","Peso colombiano","Unidad de valor real colombiana (añadida al COP)","Colón costarricense",
			 "Dinar serbio (Reemplazado por RSD el 25 de octubre de 2006)", "Peso cubano","Peso cubano convertible","Escudo caboverdiano",
			 "Koruna checa","Franco yibutiano","Corona danesa","Peso dominicano","Dinar argelino","Libra egipcia","Nakfa eritreo","Birr etíope",
			 "Euro","Dólar fiyiano","Libra malvinense","Libra esterlina (libra de Gran Bretaña)","Lari georgiano","Cedi ghanés","Libra de Gibraltar",
			 "Dalasi gambiano","Franco guineano","Quetzal guatemalteco","Dólar guyanés","Dólar de Hong Kong","Lempira hondureño","Kuna croata",
			 "Gourde haitiano","Forint húngaro","Rupiah indonesia", "Nuevo shéquel israelí", "Rupia india", "Dinar iraquí","Rial iraní",
			 "Króna islandesa","Dólar jamaicano","Dinar jordano","Yen japonés","Chelín keniata","Som kirguís (de Kirguistán)","Riel camboyano",
			 "Franco comoriano (de Comoras)","Won norcoreano","Won surcoreano","Dinar kuwaití", "Dólar caimano (de Islas Caimán)",
			 "Tenge kazajo", "Kip lao", "Libra libanesa","Rupia de Sri Lanka","Dólar liberiano","Loti lesotense", "Litas lituano",
			 "Lat letón","Dinar libio","Dirham marroquí","Leu moldavo","Ariary malgache","Denar macedonio","Kyat birmano", "Tughrik mongol",
			 "Pataca de Macao","Ouguiya mauritana","Rupia mauricia","Rufiyaa maldiva","Kwacha malauí","Peso mexicano","Unidad de Inversión MX",
			 "Ringgit malayo","Metical mozambiqueño","Dólar namibio","Naira nigeriana","Córdoba nicaragüense","Corona noruega","Rupia nepalesa",
			 "Dólar neozelandés","Rial omaní","Balboa panameña","Nuevo sol peruano","Kina de Papúa Nueva Guinea","Peso filipino","Rupia pakistaní",
			 "zloty polaco","Guaraní paraguayo","Rial qatarí","Leu rumano","Rublo ruso","Franco ruandés","Riyal saudí","Dólar de las Islas Salomón",
			 "Rupia de Seychelles","Dinar sudanés","Corona sueca","Dólar de Singapur","Libra de Santa Helena","Leone de Sierra Leona","Chelín somalí",
			 "Dólar surinamés","Dobra de Santo Tomé y Príncipe","Libra siria","Lilangeni suazi","Baht tailandés","Somoni tayik (de Tayikistán)",
			 "Manat turcomano","Dinar tunecino","Pa'anga tongano","Lira turca", "Dólar de Trinidad y Tobago","Dólar taiwanés","Chelín tanzano",
			 "Grivna ucraniana","Chelín ugandés","Dólar estadounidense","Dólar estadounidense (Siguiente día) (código de fondos)","Dólar estadounidense (Mismo día) (código de fondos)",
			 "Peso uruguayo","Som uzbeko","Bolívar fuerte venezolano","Dong vietnamita","Vatu vanuatense","Tala samoana","Franco CFA de África C.",
			 "Onza de plata","Onza de oro",		 "European Composite Unit (EURCO) (unidad del mercado de bonos)","European Monetary Unit (E.M.U.-6) (unidad del mercado de bonos)",
			 "European Unit of Account 9 (E.U.A.-9) (unidad del mercado de bonos)","European Unit of Account 17 (E.U.A.-17) (unidad del mercado de bonos)",
			 "Dólar del Caribe Oriental", "Derechos Esp. (FMI)", "Franco de oro (Special settlement currency)","Franco UIC (Special settlement currency)", "Franco CFA de África Occidental",
			 "Onza de paladio","Franco CFP","Onza de platino","Reservado para pruebas", "Sin divisa", "Rial yemení (de Yemen)", "Rand sudafricano",
			 "Kwacha zambiano","Dólar zimbabuense","Libra irlandesa","Colón salvadoreño","Dinar serbio","COPPER HIGHGRADE","Yuan Chino Extracontinental"};
	 
	 String[] arrayItemsCod = {"AED","AFN","ALL","AMD","ANG","AOA","ARS","AUD", "AWG","AZN","BAM","BBD","BDT","BGN","BHD","BIF","BMD",
			 "BND","BOB","BOV","BRL","BSD","BTN","BWP","BYR","BZD","CAD","CDF","CHF","CLF","CLP","CNY","COP","COU","CRC","CSD", "CUP",
			 "CUC","CVE","CZK", "DJF", "DKK", "DOP", "DZD", "EGP", "ERN","ETB", "EUR", "FJD", "FKP", "GBP", "GEL", "GHS", "GIP","GMD",
			 "GNF", "GTQ","GYD","HKD", "HNL", "HRK", "HTG", "HUF","IDR","ILS","INR","IQD","IRR","ISK","JMD","JOD", "JPY", "KES","KGS",
			 "KHR","KMF", "KPW","KRW", "KWD", "KYD", "KZT", "LAK", "LBP", "LKR", "LRD", "LSL", "LTL", "LVL", "LYD", "MAD", "MDL", "MGA",
			 "MKD", "MMK", "MNT", "MOP", "MRO", "MUR", "MVR", "MWK", "MXN", "MXV", "MYR", "MZN", "NAD", "NGN", "NIO", "NOK", "NPR", "NZD",
			 "OMR", "PAB","PEN","PGK","PHP","PKR","PLN","PYG","QAR","RON", "RUB", "RWF", "SAR", "SBD", "SCR", "SDG", "SEK", "SGD", "SHP",
			 "SLL", "SOS","SRD","STD", "SYP","SZL", "THB", "TJS","TMT", "TND", "TOP", "TRY","TTD", "TWD", "TZS", "UAH", "UGX", "USD","USN",
			 "USS","UYU", "UZS","VEF","VND","VUV", "WST","XAF", "XAG", "XAU","XBA", "XBB", "XBC","XBD", "XCD", "XDR","XFO", "XFU", "XOF",
			 "XPD", "XPF","XPT", "XTS","XXX","YER","ZAR","ZMW","ZWL","IEP","SVC","RSD","XCP","CNH"};
	
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
		boolean bsActualiza;
	 
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// back up de base de datos 
	/*	try {
			BD_backup();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		//// uso de clave publica de play store
		String idDispositivo = Secure.getString(getContentResolver(),                                                                    Secure.ANDROID_ID);

        ServerManagedPolicy politica = new ServerManagedPolicy(this,
                new AESObfuscator(SALT, getPackageName(), idDispositivo));

        //comprobarLicencia = new LicenseChecker(this, politica, CLAVE_PUBLICA_LICENCIA);
		
		
		
		
		
			// Creacion de base de datos
		   admin = new MonedaHelper(this);
		   try {
			   admin.createDataBase();
			   admin.open();
	        } catch (IOException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
		
			 shareTitle1 = "Yo verifico el tipo de cambio de cualquier moneda…!! con  Coin Change app.";
		     shareUrl1 = "https://play.google.com/store/apps/details?id=com.vengapps.divisas";
		     Listmon = (ListView)findViewById(R.id.ListMon);
		     MonName = (TextView)findViewById(R.id.Monname);
		     MonPrice= (TextView)findViewById(R.id.Monprice);
		     image = (ImageView)findViewById(R.id.image);		
		     Monto = (EditText)findViewById(R.id.txt_change);
		     Monto.addTextChangedListener(textWatcher);
		     dbAdapter = new DBOperations(this);
		     dbAdapter.setConf(false);
		     //LinearLayout layout = (LinearLayout)findViewById(R.id.activity_main);
		     
		    ConsMonPrincipal();
		  	new TweetsSearchTask().execute();
		
		  	Listmon.setOnItemClickListener(this);
		  	adapter = new MonedaAdapter(this, 0, dbAdapter.getStatusUpdates());
		  	
		  	//banners de publicidad
		  	 AdView adView = (AdView)this.findViewById(R.id.ad);
		     
		  	adView = new AdView(this, AdSize.BANNER, "ca-app-pub-7795597851552086/4399033858");
		  	 adView.loadAd(new AdRequest());
	}
	
	
	 @Override
	  public void onDestroy() {
	    if (adView != null) {
	      adView.destroy();
	    }
	    super.onDestroy();
	  }
	
	   private void updateListView(ArrayList<BeansUsuario> BeansUsuario){
		   
		   	String fMonto = Monto.getText().toString();
       		String fPrice = MonPrice.getText().toString();
       	if(fMonto.equals("")){
       		fMonto = null;}
       	
		     adapter=new MonedaAdapter(this, R.layout.itemlista, BeansUsuario);
		     adapter.setdMonto(fMonto);
		     adapter.setDtipo_cambio(fPrice);		     
	    	 Listmon.setAdapter(adapter);
	 	}
	     
	
	  public void Acturalizar(){
		  
		  
		
		 // SQLiteDatabase bd = admin.getWritableDatabase();
	     //  bd.delete("tdivisas",null,null);
	     //   bd.close();
		  if(isOnline()){
			  new asyncMoneda().execute();
		  }else{
				Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.nohayred), Toast.LENGTH_SHORT);
		        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		        toast.show();
		  }
		
		  
	  }
	  
	  	
	
	 public Boolean invocarWS(){
			Boolean bandera = true;
			Integer ic=0,dato=0;
			
			
			
			String YAHOO_FINANCE_URL;
			Log.d("Srv", "Get stock");
			YAHOO_FINANCE_URL = "http://finance.yahoo.com/webservice/v1/symbols/allcurrencies/quote.ws";//+("#sym#", stock.getSymbol());
			String url = YAHOO_FINANCE_URL;//.replaceAll("#sym#", stock.getSymbol());
			try {
			    HttpURLConnection con = (HttpURLConnection) ( new URL(url)).openConnection();
			    con.setRequestMethod("GET");
			    con.connect();
			    InputStream is = con.getInputStream();
			 
			    // Start parsing XML
			    XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
			    parser.setInput(is, null);
			    int event = parser.getEventType();
			    String tagName = null,tagtext=null;
			    String currentTag = null;
			    //Stock stockResult = new Stock();
			    parser.nextTag();
		    	parser.nextTag();
			    
			    while (event != XmlPullParser.END_DOCUMENT) {
			        
			    	tagName = parser.getName();
			        tagtext = parser.getText();
			       
			       
			        
			        if(tagtext != null ){
			        
			        	if ( "\n".equalsIgnoreCase(tagtext)|| "resource-list".equalsIgnoreCase(tagtext)){
			        				
			        		}
			        	else{
			        		
			        			//Log.d("Srv", "Tex:" + tagtext);
			        			
			        			switch (dato) {
								case 0:
									Act.setId(String.valueOf(ic));
			        				ic+=1;
			        				dato+=1;
			        				//Act.setName(tagtext);
									break;
								case 1:	
									
									tagtext = (String.valueOf(round(Float.valueOf(tagtext),2)));
									Act.setPrice(tagtext);
									dato+=1;
									break;
								case 2:	
									Act.setSymbol(tagtext.substring(0,3));
									 
									Act.setSn_habilita("0");
									Act.setUbic("0");
									Act.setName(Obtener_nombre_moneda(tagtext.substring(0,3)));
									
									if("GTQ".equals(tagtext.substring(0,3))){
												Act.setSn_habilita("-1");
												Act.setUbic("-1");
									}
									if("USD".equals(tagtext.substring(0,3))){
													Act.setSn_habilita("-1");
									}
									if("EUR".equals(tagtext.substring(0,3))){
										Act.setSn_habilita("-1");
										}
									if("JPY".equals(tagtext.substring(0,3))){
										Act.setSn_habilita("-1");
										}
									if("GBP".equals(tagtext.substring(0,3))){
										Act.setSn_habilita("-1");
										}
									if("CHF".equals(tagtext.substring(0,3))){
										Act.setSn_habilita("-1");
										}
									if("AUD".equals(tagtext.substring(0,3))){
										Act.setSn_habilita("-1");
										}
									if("CAD".equals(tagtext.substring(0,3))){
										Act.setSn_habilita("-1");
										}
									if("SEK".equals(tagtext.substring(0,3))){
										Act.setSn_habilita("-1");
										}
									if("HKD".equals(tagtext.substring(0,3))){
										Act.setSn_habilita("-1");
										}
									if("NOK".equals(tagtext.substring(0,3))){
										Act.setSn_habilita("-1");
										}
									if("NZD".equals(tagtext.substring(0,3))){
										Act.setSn_habilita("-1");
										}
									if("MXN".equals(tagtext.substring(0,3))){
										Act.setSn_habilita("-1");
										}
									dato+=1;
									break;
								case 3:	
									Act.setTs(tagtext);
									dato+=1;
									break;
								case 4:	
									Act.setType(tagtext);
									dato+=1;
									break;
								case 5:	
									Act.setUtctime(tagtext);
									dato+=1;
									break;
								case 6:	
									Act.setVolume(tagtext);
									dato=0;
									//Act.setUbic("0");
									
								//Log.e("Valores",Act.getId()+ " " +  Act.getName() + " " + Act.getPrice()+ " " + Act.getSymbol() + " " + Act.getTs()+ " " + Act.getType()+ " " + Act.getUtctime()+ " " + Act.getVolume());
									
								   
								    	// alta(Act.getId(), Act.getName(),Act.getPrice(),Act.getSymbol(),Act.getTs(),Act.getType(),Act.getUtctime(), Act.getVolume(),Act.getSn_habilita(),Act.getUbic());
								    	
								   
								    	 modifica(Act.getId(), Act.getName(),Act.getPrice(),Act.getSymbol(),Act.getTs(),Act.getType(),Act.getUtctime(), Act.getVolume(),Act.getSn_habilita(),Act.getUbic());
								   
									break;
								default:
									break;
								}				        			
			        		}			        
			        }			        
			        event = parser.next();			 
			    }			 
			} catch (Exception e) {
			    e.printStackTrace();
			}			
			return  bandera;
		}
		
		
	 
	 
	 
	
	 
	 public void alta(String id, String name , String price ,String symbol ,String ts ,String type ,String utctime ,String volume,String sn_habilita,String Ubic ) {
	      
		 
	        SQLiteDatabase bd = admin.getWritableDatabase();	      
	      //id integer primary key, name text, price text, symbol text, ts text,type text,utctime text,volume text 
	        ContentValues registro = new ContentValues();
	        
	        registro.put("_id", id);
	        registro.put("name", name);
	        registro.put("price", price);
	        registro.put("symbol", symbol);
	        registro.put("ts", ts);
	        registro.put("type", type);
	        registro.put("utctime", utctime);
	        registro.put("volume", volume);
	        registro.put("sn_habilita", sn_habilita);
	        registro.put("ubic", Ubic);
	        bd.insert("tdivisas", null, registro);	                   
	      
	        ContentValues registro1 = new ContentValues();
	        if (id=="0"){
	        	 registro1.put("_id", id);
	 	         registro1.put("name", name);
	 	         registro1.put("price", price);
	        }
	        if (id=="1"){
	        	
	        }

	        bd.close();
	       
	    }
	 
	 public void modifica(String id, String name , String price ,String symbol ,String ts ,String type ,String utctime ,String volume,String sn_habilita,String Ubic ) {
	      
		 
	        SQLiteDatabase bd = admin.getWritableDatabase();	      
	      //id integer primary key, name text, price text, symbol text, ts text,type text,utctime text,volume text 
	        ContentValues registro = new ContentValues();
	       // price = "0";
	       // registro.put("_id", id);
	        registro.put("name", name);
	        registro.put("price", price);
	        registro.put("symbol", symbol);
	        registro.put("ts", ts);
	        registro.put("type", type);
	        registro.put("utctime", utctime);
	        registro.put("volume", volume);
	       // registro.put("sn_habilita", sn_habilita);
	       // registro.put("ubic", Ubic);
	        bd.update("tdivisas", registro,"symbol='" + symbol + "'",null);	                   
	        bd.close();
	       
	    }
	 
	 
	///clase para sincronizar las tareas si dura mucho tiempo

			class asyncMoneda extends AsyncTask<String,String,String>{

				
				@Override
				protected void onPreExecute() {
					// TODO Auto-generated method stub
					//super.onPreExecute();
					dialogo = new ProgressDialog(MainActivity.this);
					dialogo.setMessage("Cargando Datos...");
					dialogo.setIndeterminate(false);
					dialogo.setCancelable(false);
					dialogo.show();
					//Log.e("onPreExecute ", "onPreExecute");
				}
				
				
				@Override
				protected String doInBackground(String... params) {
					// TODO Auto-generated method stub
					//return null;
				
					
					//ConnectivityManager connMgr = (ConnectivityManager) 
                    //        context.getSystemService(Context.CONNECTIVITY_SERVICE);
					//NetworkInfo netInfo = connMgr.getActiveNetworkInfo();
					
					
				//	boolean isConnected = netInfo != null
	             //           && netInfo.getState() == NetworkInfo.State.CONNECTED;
                    //&& netInfo.getType() == ConnectivityManager.TYPE_WIFI
				
				//try{	
					//if (isConnected) {
						if (invocarWS()){
							
							return "OK";
						}else{
							
							return "Err";
						}
					//}
				//else{
					//	return "Err";
					//}
				//}catch(Exception e) {
					
					//Toast.makeText(MainActivity.this, getResources().getString(R.string.nohayred),
						//			Toast.LENGTH_SHORT).show();
					//return "Err";
					
				//}
				}
				
				@Override
				protected void onPostExecute(String result) {
					// TODO Auto-generated method stub
					//super.onPostExecute(result);
					
					dialogo.dismiss();
					
					if(result.equals("OK")){
						//aqui se cargan los datos al gridd	
						new TweetsSearchTask().execute();	
						ConsMonPrincipal();
					//	txt_venta.setText("Venta: " + sVenta);
					//	txt_compra.setText("Compra: " + Scompra);  
						
						
						}
					}
		
		
			}	////Asycttask		

			
	//////////menu
			
			protected void setTitle(String title){
				  getSupportActionBar().setTitle(title);
				}
				  
				protected void setSubTitle(String subtitle){
				  getSupportActionBar().setSubtitle(subtitle);
				}
				

				
				protected void setShare(String shareTitle, String shareUrl) {
				  this.shareTitle1 = shareTitle;
				  this.shareUrl1 = shareUrl;
				  actionProvider.setShareIntent(shareIntent(getResources().getString(R.string.shareUrl1),getResources().getString(R.string.shareTitle1)));// this.shareTitle1));
				}
				 
				ShareActionProvider actionProvider;
				  
				protected SubMenu subMenu;
				 
			
				public boolean onCreateOptionsMenu(Menu menu) {
				        getSupportActionBar().setDisplayHomeAsUpEnabled(true);					       
				        getSupportMenuInflater().inflate(R.menu.share_action_provider, menu);

				      //wg  MenuItem actionItem = menu.findItem(R.id.menu_item_share_action_provider_action_bar);
				        
				      //wg  actionProvider = (ShareActionProvider) actionItem.getActionProvider();
				      //wg  actionProvider.setShareHistoryFileName(ShareActionProvider.DEFAULT_SHARE_HISTORY_FILE_NAME);
				        
				        //actionProvider.setShareIntent(setIntent(Uri.parse("android.resource://" + getPackageName() + "/drawable/" + R.drawable.logo)));
				      //wg  actionProvider.setShareIntent(shareIntent(shareUrl1, shareTitle1));
				        
				       
				        subMenu = menu.addSubMenu("Opciones");
				        /* subMenu.add("actualizar")
				         .setOnMenuItemClickListener(new OnMenuItemClickListener() {    
				    public boolean onMenuItemClick1(MenuItem item) {
				    	/// llamando la actualizacion de monedas...
				    	
				    	
				    	
				     return false;
				    }

					@Override
					public boolean onMenuItemClick(
							com.actionbarsherlock.view.MenuItem item) {
						// TODO Auto-generated method stub
						
						
				    	
						
						return false;
					}
				   });*/
				          subMenu.add("About")
				      .setOnMenuItemClickListener(new OnMenuItemClickListener() {   
				    	  	public boolean onMenuItemClick(MenuItem item) {
				    	  		 Uri uri = Uri.parse("http://wilmangarcia.esy.es/index.html");
				    	  		 Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				    	  		 startActivity(intent);
				    	  		 
				    	  			return false;
				    	  		}
				   });

				        subMenu.getItem().setIcon(R.drawable.abs__ic_menu_moreoverflow_holo_light);
				        subMenu.getItem().setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);


				        return super.onCreateOptionsMenu(menu);
				        
				
				        
				}
				  
				private Intent setIntent(Uri parse) {
					// TODO Auto-generated method stub
					return null;
				}

				public void addSubMenu(String title, OnMenuItemClickListener l){
				   subMenu.add(title)
				     .setOnMenuItemClickListener(l);
				}
				      
				@Override
				public boolean onOptionsItemSelected(MenuItem item) {
				   switch (item.getItemId()) {
				        case android.R.id.home: 
				            return true;
				        case R.id.menuMoneAct:   
				        		Acturalizar();
				            return true;
				        case R.id.menuMonedas:
				        	   ConfigMOndedas();
				        	   return true;
				        case R.id.menuMonedasCOnf:
				        	   ConfigApp();
				        	   return true;
				        case R.id.share:
				        	   Intent share = new Intent();
				        	   share.setAction(Intent.ACTION_SEND);
				        	   String msg= getResources().getString(R.string.shareTitle1);
				        	   String msg2= getResources().getString(R.string.shareUrl1);
				        	   msg = msg +" "+ msg2;
				        	   share.putExtra(Intent.EXTRA_TEXT, msg);
				        	   Uri msgimage= Uri.parse("android.resource://" + getPackageName() + "/drawable/" + R.drawable.logo);
				        	   share.putExtra(Intent.EXTRA_STREAM, msgimage);
				        	   share.setType("image/jpeg");
				        	   startActivity(Intent.createChooser(share, "Compartir"));
				        	   return true;	   
				        default:
				            return super.onOptionsItemSelected(item);
				        }  
				    }

			
				private Intent shareIntent(String linkShare, String titleShare){
				 final Intent intent = new Intent(Intent.ACTION_SEND);
				 intent.setType("text/plain");
				 intent.putExtra(Intent.EXTRA_SUBJECT, titleShare);
				 intent.putExtra(Intent.EXTRA_TEXT, linkShare);
				 return intent;
				}
			
	///////// menu 	fin 
				
				
				
				private class TweetsSearchTask extends AsyncTask<Object, Void, ArrayList<BeansUsuario>>{

					private ProgressDialog progressDialog;

					@Override
					protected void onPreExecute(){
						super.onPreExecute();
					//	progressDialog = new ProgressDialog(MainActivity.this);
					//	progressDialog.setMessage(getResources().getString(R.string.actualizar));
					//	progressDialog.show();
					}
					
				
					@Override
					protected ArrayList<BeansUsuario> doInBackground(Object... params) {
						  
						    // updateListView();
						return dbAdapter.getStatusUpdates();
					}

					
			
					
					protected void onPostExecute(ArrayList<BeansUsuario> BeansUsuarios){
						//progressDialog.dismiss();

						
							updateListView(BeansUsuarios);
							//Toast.makeText(MainActivity.this, getResources().getString(R.string.actualizar),
								//	Toast.LENGTH_SHORT).show();
						
					}
				}
				
				public  String Obtener_nombre_moneda(String sSymbol){
					
						for(int i=0; i<arrayItemsCod.length;i++){
							
							if(arrayItemsCod[i].equals(sSymbol)){
								
								return arrayItemsNames[i];
							}
						}				
					
						return "No Name";
				}
				
				public void ConfigMOndedas(){
					bActualiza = true;
					Intent iConf = new Intent (this,ConfigurationMon.class);
					iConf.putExtra("Actualiza", bActualiza);
					startActivity(iConf);
					
				}
				public void ConfigApp(){
					
					Intent iConf = new Intent (this,ConfigApp.class);
					startActivity(iConf);
					
				}

				static class ViewHolder{
					public ImageView avatar;
					public TextView name;
					public TextView symbol;
					public TextView price;
					
				}
				
				@Override
				public void onItemClick(AdapterView<?> parent, View v,int position, long id) {
					
					String sPrice = ((TextView)v.findViewById(R.id.price)).getText().toString();
					String snameMon = ((TextView)v.findViewById(R.id.nameMon)).getText().toString();
					String sSimbol = ((TextView)v.findViewById(R.id.symbol)).getText().toString();
					//ImageView image1 = (ImageView)v.findViewById(R.id.foto);
					 MonName.setText(snameMon);
					 MonPrice.setText(sPrice);
					 //image.setId(image1.getId());
					 image.setImageResource((banderas[getItemFlag(sSimbol)]));
					// Log.e("Id de image",String.valueOf(image1.getId()));
					 MonPrincipal(sSimbol);
				}
				
				public void MonPrincipal(String sSymbol){
					
					 SQLiteDatabase bd = admin.getWritableDatabase();
			       	 ContentValues registro = new ContentValues();
			       	registro.put("ubic", "0");
			       	 bd.update("tdivisas", registro, null, null);
			       	 
			       	 registro.put("ubic", "-1");
			       	 bd.update("tdivisas", registro, "symbol='" + sSymbol+"'", null);
				     bd.close();
				     ConsMonPrincipal();
				     new TweetsSearchTask().execute();
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


				@Override
				protected void onResume() {
					// TODO Auto-generated method stub
					super.onResume();
				 	new TweetsSearchTask().execute();
				 	
				}
				
				public void ConsMonPrincipal(){
					
					 SQLiteDatabase bd = admin.getWritableDatabase();
			       	 ContentValues registro = new ContentValues();
			         Cursor fila = bd.rawQuery("select name,price,symbol from tdivisas where ubic = '-1'" , null);
			         if (fila.moveToFirst()) {
			        	 MonName.setText(fila.getString(0));
			        	 MonPrice.setText(fila.getString(1));
			        	 image.setImageResource((banderas[getItemFlag(fila.getString(2))]));
			             
			         }else
			        	 Toast.makeText(this, "No Moneda por Default",
			                     Toast.LENGTH_SHORT).show();
			         
			        	 
			       	 
				     bd.close();
				   
				}
				
				
				private TextWatcher textWatcher = new TextWatcher() {
					 
			        @Override
			        public void afterTextChanged(Editable s) {
			        	
			        	
			           // adapter.setdMonto(fMonto);
			            //adapter.setDtipo_cambio(fPrice);
			            new TweetsSearchTask().execute();
			        }
			 
			        @Override
			        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			                int arg3) {
			            // TODO Auto-generated method stub
			 
			        }
			 
			        @Override
			        public void onTextChanged(CharSequence arg0, int arg1, int arg2,
			                int arg3) {
			            // TODO Auto-generated method stub
			 
			        }
			 
			    };
			    
			    public static float round(float d, int decimalPlace) {
					   BigDecimal bd = new BigDecimal(Float.toString(d));
					   bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
					   return bd.floatValue();
					}

			    public boolean isOnline() {
			    	ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

			    	NetworkInfo netInfo = cm.getActiveNetworkInfo();

			    	if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			    	return true;
			    	}

			    	return false;
			    	}

			   
			    public  void BD_backup() throws IOException {
			    	String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
			    	String DATABASE_NAME = "DBdivisas.db";
			    	final String inFileName = "/data/data/com.vengapps.divisas/databases/"+DATABASE_NAME;
			    	File dbFile = new File(inFileName);
			    	FileInputStream fis = null;

			    	fis = new FileInputStream(dbFile);

		
			    	 File ruta_sd = Environment.getExternalStorageDirectory();
			    	 
			    	String directorio = ruta_sd.getAbsolutePath();
			    	File d = new File(directorio);
			    	if (!d.exists()) {
			    	d.mkdir();
			    	}
			    	String outFileName = directorio + "/"+ DATABASE_NAME;// + "_"+timeStamp;

			    	OutputStream output = new FileOutputStream(outFileName);

			    	byte[] buffer = new byte[1024];
			    	int length;
			    	while ((length = fis.read(buffer)) > 0) {
			    	output.write(buffer, 0, length);
			    	}

			    	output.flush();
			    	output.close();
			    	fis.close();

			    	}		
				
}
