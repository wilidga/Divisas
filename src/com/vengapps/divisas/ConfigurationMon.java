package com.vengapps.divisas;

import java.util.ArrayList;
import java.util.Locale;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnActionExpandListener;
import com.actionbarsherlock.view.SubMenu;
import com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;
import com.actionbarsherlock.widget.ShareActionProvider;





import Beans.BeansUsuario;
import DB.DBOperations;
import DB.MonedaAdapter;
import DB.MonedaHelper;
import DB.MonedasAdapter2;
import android.app.ProgressDialog;

import android.content.Context;
import android.content.Intent;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;


import android.view.inputmethod.InputMethodManager;


import android.widget.EditText;
import android.widget.ListView;
import com.google.ads.*;
public class ConfigurationMon extends SherlockActivity   {
	ArrayList<BeansUsuario> arraylist = new ArrayList<BeansUsuario>();
	private DBOperations dbAdapter;
	ListView listViewconf;
	MonedaAdapter adapter;
	Boolean bActualiza;
	EditText editsearch;
	MonedaHelper admin;
	 private AdView adView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_conf);	
		
		
			listViewconf = (ListView)findViewById(R.id.Listconf);
			dbAdapter = new DBOperations(this);		
			dbAdapter.setConf(true);
			dbAdapter.setbFilter(false);
		 	
			Bundle bundle = getIntent().getExtras();
			adapter = new MonedaAdapter(this, 0, dbAdapter.getStatusUpdates());
			
		 if (bundle.getBoolean("Actualiza"));{
			 actualizaMon(); 
		 }
		 
		//banners de publicidad
	  	 AdView adView = (AdView)this.findViewById(R.id.ad);
	     
	  	adView = new AdView(this, AdSize.BANNER, "ca-app-pub-7795597851552086/4399033858");
	  	 adView.loadAd(new AdRequest());
		
	}
	
	
	
	public void actualizaMon(){
		new TweetsSearchTask().execute();
		
	}
	

	private void updateListView(ArrayList<BeansUsuario> BeansUsuario){
	    	 listViewconf.setAdapter(new MonedasAdapter2(this, R.layout.itemlista2, BeansUsuario));
	    	 
	    	 
	    	 
	 	}
	
	private class TweetsSearchTask extends AsyncTask<Object, Void, ArrayList<BeansUsuario>>{

		private ProgressDialog progressDialog;
		

		@Override
		protected void onPreExecute(){
			super.onPreExecute();
			//progressDialog = new ProgressDialog(Configuracion.this);
			//progressDialog.setMessage(getResources().getString(R.string.actualizar));
			//progressDialog.show();
		}
		
	
		@Override
		protected ArrayList<BeansUsuario> doInBackground(Object... params) {
			  
			    // updateListView();
			return dbAdapter.getStatusUpdates();
		}
		
		protected void onPostExecute(ArrayList<BeansUsuario> BeansUsuarios){
			//progressDialog.dismiss();

			
				updateListView(BeansUsuarios);
				//Toast.makeText(Configuracion.this, getResources().getString(R.string.actualizar),
					//	Toast.LENGTH_SHORT).show();
			
		}
	}

	
	
	
	//////////menu
	
	protected void setTitle(String title){
		  getSupportActionBar().setTitle(title);
		}
		  
		protected void setSubTitle(String subtitle){
		  getSupportActionBar().setSubtitle(subtitle);
		}
		  
		
	
		 
		ShareActionProvider actionProvider;
		  
		protected SubMenu subMenu;
		 
	
		public boolean onCreateOptionsMenu(Menu menu) {
		        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		        
		        getSupportMenuInflater().inflate(R.menu.menubusqueda, menu);
		        editsearch = (EditText) menu.findItem(R.id.menu_search).getActionView();
		        editsearch.addTextChangedListener(textWatcher);
		        MenuItem menuSearch = menu.findItem(R.id.menu_search);
		        
		        menuSearch.setOnActionExpandListener(new OnActionExpandListener() {
		        	 
		            // Menu Action Collapse
		          
		            public boolean onMenuItemActionCollapse(MenuItem item) {
		                // Empty EditText to remove text filtering
		                editsearch.setText("");
		                editsearch.clearFocus();
		                return true;
		            }// Menu Action Expand
		
		            public boolean onMenuItemActionExpand(MenuItem item) {
		                // Focus on EditText
		                editsearch.requestFocus();
		 
		                // Force the keyboard to show on EditText focus
		                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
		                return true;
		            }
		        });
		        
		        
		        subMenu = menu.addSubMenu("Opciones");
		      
		          subMenu.add("About")
		      .setOnMenuItemClickListener(new OnMenuItemClickListener() {   
		    	  	public boolean onMenuItemClick(MenuItem item) {
		    	  		 Uri uri = Uri.parse("http://wilmangarcia.esy.es/index.html");
		    	  		 Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		    	  		 startActivity(intent);
		    	  		 
		    	  			return false;
		    	  		}

					public boolean onMenuItemClick1(
							com.actionbarsherlock.view.MenuItem item) {
					
						return false;
					}
		   });

		        subMenu.getItem().setIcon(R.drawable.abs__ic_menu_moreoverflow_holo_light);
		        subMenu.getItem().setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);


		        return super.onCreateOptionsMenu(menu);
		        
		
		        
		}
		  
		public void addSubMenu(String title, OnMenuItemClickListener l){
		   subMenu.add(title)
		     .setOnMenuItemClickListener(l);
		}
		      
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
		   switch (item.getItemId()) {
		        case android.R.id.home:            
		        	NavUtils.navigateUpTo(this,new Intent(this, MainActivity.class));
		            return true;
		        case R.id.menuMoneAct:   
		        		
		            return true;
		        case R.id.menuMonedas:
		        	  
		        	   return true;
		        case R.id.menuMonedasCOnf:
		        	  
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
	
		
		
		private TextWatcher textWatcher = new TextWatcher() {
			 
	        @Override
	        public void afterTextChanged(Editable s) {
	            // TODO Auto-generated method stub
	            String text = editsearch.getText().toString()
	                    .toLowerCase(Locale.getDefault());
	            dbAdapter.setbFilter(true);
	            dbAdapter.setsWhere(text);
	           // adapter.filter(text);
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


	
	


		
		
	
}
