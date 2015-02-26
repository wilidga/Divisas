package DB;



import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.vengapps.divisas.MainActivity;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;


public class MonedaHelper extends SQLiteOpenHelper {
	
	private SQLiteDatabase myDataBase;	 
	private final Context myContext;
	private static String DB_PATH = "/data/data/com.vengapps.divisas/databases/";
    public static final String DB_NAME = "DBdivisas.db";
    public static final int DB_VERSION = 7;
        
    public static final String TABLE = "tdivisas";
    public static final String C_ID = BaseColumns._ID;
    public static final String C_NAME = "name";
    public static final String C_PRICE = "price";
    public static final String C_SYMBOL = "symbol";
    public static final String C_TS = "ts";
    public static final String C_TYPE = "type";
    public static final String C_UTCTIME = "utctime";
    public static final String C_VOLUME = "volume";
    public static final String C_sn_habilita = "sn_habilita";
    public static final String C_ubic = "ubic";
    
    
    public static final int C_ID_INDEX = 0;
    public static final int C_NAME_INDEX = 1;
    public static final int C_PRICE_INDEX = 2;
    public static final int C_SYMBOL_INDEX = 3;
    public static final int C_TS_INDEX = 4;
    public static final int C_TYPE_INDEX = 5;
    public static final int C_UTCTIME_INDEX = 6;
    public static final int C_VOLUME_INDEX = 7;
    public static final int C_HABILITA_INDEX = 8;
    public static final int C_UBIC_INDEX = 9;
    MainActivity actNew = new MainActivity();
    
    /*public MonedaHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
        
    }*/
	
    public MonedaHelper(Context context) {    	 
    	super(context, DB_NAME, null, DB_VERSION);
    	this.myContext = context;
    	 
    	}
    
    
    /**
    * Crea una base de datos vacía en el sistema y la reescribe con nuestro fichero de base de datos.
    * */
    public void createDataBase() throws IOException{
     
    boolean dbExist = checkDataBase();
     
    if(dbExist){
    //la base de datos existe y no hacemos nada.
    }else{
    //Llamando a este método se crea la base de datos vacía en la ruta por defecto del sistema
    //de nuestra aplicación por lo que podremos sobreescribirla con nuestra base de datos.
    this.getReadableDatabase();
     
    try {
     
    copyDataBase();
     
    } catch (IOException e) {
    throw new Error("Error copiando Base de Datos");
    }
    }
     
    }
     
    /**
    * Comprueba si la base de datos existe para evitar copiar siempre el fichero cada vez que se abra la aplicación.
    * @return true si existe, false si no existe
    */
    private boolean checkDataBase(){
     
    SQLiteDatabase checkDB = null;
     
    try{
     
    String myPath = DB_PATH + DB_NAME;
    checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
     
    }catch(SQLiteException e){
     
    //si llegamos aqui es porque la base de datos no existe todavía.
     
    }
    if(checkDB != null){
     
    checkDB.close();
     
    }
    return checkDB != null ? true : false;
    }
     
    /**
    * Copia nuestra base de datos desde la carpeta assets a la recién creada
    * base de datos en la carpeta de sistema, desde dónde podremos acceder a ella.
    * Esto se hace con bytestream.
    * */
    private void copyDataBase() throws IOException{
     
    //Abrimos el fichero de base de datos como entrada
    InputStream myInput = myContext.getAssets().open(DB_NAME);
     
    //Ruta a la base de datos vacía recién creada
    String outFileName = DB_PATH + DB_NAME;
     
    //Abrimos la base de datos vacía como salida
    OutputStream myOutput = new FileOutputStream(outFileName);
     
    //Transferimos los bytes desde el fichero de entrada al de salida
    byte[] buffer = new byte[1024];
    int length;
    while ((length = myInput.read(buffer))>0){
    myOutput.write(buffer, 0, length);
    }
     
    //Liberamos los streams
    myOutput.flush();
    myOutput.close();
    myInput.close();
     
    }
     
    public void open() throws SQLException{
     
    //Abre la base de datos
    try {
    createDataBase();
    } catch (IOException e) {
    throw new Error("Ha sido imposible crear la Base de Datos");
    }
     
    String myPath = DB_PATH + DB_NAME;
    myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
     
    }
     
    @Override
    public synchronized void close() {
    if(myDataBase != null)
    myDataBase.close();
    super.close();
    }
     
    @Override
    public void onCreate(SQLiteDatabase db) {    	
     
    }
     
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    	 // Typically do ALTER TABLE statements, but... we're just in development, so:
      //  db.execSQL("drop table if exists " + TABLE); // drops the old database
       // db.execSQL("drop table if exists rate_wifi"); // drops the old database
        //onCreate(db); // run onCreate to get the new database
    }	
   
    
    
    
    // Called only once, first time the DB is created
  /*  @Override
    public void onCreate(SQLiteDatabase db) {
        String  sql = "create table " + TABLE + " (" + C_ID + " int primary key, " 
               + C_NAME + " text, " + C_PRICE + " text, " +  C_SYMBOL + " text, " + C_TS + " text, " +
        		C_TYPE + " text,"+ C_UTCTIME + " text,"+ C_VOLUME + " text," + C_sn_habilita + " text,"+ C_ubic + " text)";
        Log.e("Nombre de la tabla",sql);
        db.execSQL(sql);
          sql = "create table rate_wifi ( id int primary key,  txt_desc text ,sn_habilita text)";
        
        db.execSQL(sql);        
        Log.e("Nombre de la tabla",sql);
    }
	
    // Called whenever newVersion != oldVersion
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Typically do ALTER TABLE statements, but... we're just in development, so:
        db.execSQL("drop table if exists " + TABLE); // drops the old database
        
        onCreate(db); // run onCreate to get the new database
    }
*/
    
    
    

}

