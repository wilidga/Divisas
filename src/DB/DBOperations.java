package DB;

import java.util.ArrayList;

import Beans.BeansUsuario;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBOperations {
	boolean conf,bFilter;
	String sWhere;
	

	public String getsWhere() {
		return sWhere;
	}

	public void setsWhere(String sWhere) {
		this.sWhere = sWhere;
	}

	public boolean isConf() {
		return conf;
	}

	public boolean isbFilter() {
		return bFilter;
	}

	public void setbFilter(boolean bFilter) {
		this.bFilter = bFilter;
	}

	public void setConf(boolean conf) {
		this.conf = conf;
	}

	private static final String TAG = DBOperations.class.getSimpleName();
	private MonedaHelper dbHelper;
	
	public DBOperations(Context context){
		dbHelper = new MonedaHelper(context);
	}
	
	public void insertOrIgnore(ContentValues values){
		Log.d(TAG, "insertOrIgnore on: " + values);
		SQLiteDatabase dataBase = dbHelper.getWritableDatabase();
		try{
			dataBase.insertWithOnConflict(MonedaHelper.TABLE, null, values, SQLiteDatabase.CONFLICT_IGNORE);
		}finally{
			dataBase.close();
		}
	}
	
	public ArrayList<BeansUsuario> getStatusUpdates(){
		String ssql1,ssql2,ssql3,ssql4;
		ArrayList<BeansUsuario> monedas = new ArrayList<BeansUsuario>();
		ssql1 =" SELECT * FROM tdivisas order by name";
		ssql2 =" SELECT * FROM tdivisas WHERE sn_habilita='-1' and ubic = '0' ";
		ssql4 = " SELECT * FROM tdivisas WHERE name like '%" + this.getsWhere() + "%'";
		if (this.isConf()){
			ssql3 = ssql1;
		}else{
			ssql3 = ssql2;
		}
		if(this.isbFilter()){
			ssql3 = ssql4;
		}
		
		SQLiteDatabase dataBase = dbHelper.getReadableDatabase();
	//	Cursor cursor = dataBase.query(MonedaHelper.TABLE, null, "sn_habilita = '-1'", null, null, null, null);
		Cursor cursor = dataBase.rawQuery(ssql3, null);		
		if(cursor.moveToFirst()){
			while (cursor.isAfterLast() == false) {
				BeansUsuario moneda = new BeansUsuario();			
				moneda.setId(String.valueOf(cursor.getInt(MonedaHelper.C_ID_INDEX)));
				moneda.setName(cursor.getString(MonedaHelper.C_NAME_INDEX));
				moneda.setPrice(cursor.getString(MonedaHelper.C_PRICE_INDEX));
				moneda.setSymbol(cursor.getString(MonedaHelper.C_SYMBOL_INDEX));
				moneda.setTs(cursor.getString(MonedaHelper.C_TS_INDEX));
				moneda.setType(cursor.getString(MonedaHelper.C_TYPE_INDEX));
				moneda.setUtctime(cursor.getString(MonedaHelper.C_UTCTIME_INDEX));
				moneda.setVolume(cursor.getString(MonedaHelper.C_VOLUME_INDEX));
			    moneda.setSn_habilita(cursor.getString(MonedaHelper.C_HABILITA_INDEX));
			    moneda.setUbic(cursor.getString(MonedaHelper.C_UBIC_INDEX));
			    
				monedas.add(moneda);
				cursor.moveToNext();

			}
		}
		
		return monedas;
		
	}
	

}
