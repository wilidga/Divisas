package com.vengapps.divisas;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.Toast;


import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener;

public class ConfigApp extends SherlockFragmentActivity implements DialogoFrag.DialogListener {
	DialogoFrag f = new DialogoFrag();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confapp);
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
 
		// First Menu Button
		/*menu.add("Help")
				.setOnMenuItemClickListener(this.HelpButtonClickListener)
				.setIcon(R.drawable.help_button) // Set the menu icon
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
 		*/
		// Second Menu Button
		menu.add("Like")
				.setOnMenuItemClickListener(this.LikeButtonClickListener)
				.setIcon(R.drawable.like_button) // Set the menu icon
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
 
		// Third Menu Button
		menu.add("Exit")
				.setOnMenuItemClickListener(this.ExitButtonClickListener)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
 
		return super.onCreateOptionsMenu(menu);
	}
 
	/*// Capture first menu button click
	OnMenuItemClickListener HelpButtonClickListener = new OnMenuItemClickListener() {
 
		public boolean onMenuItemClick(MenuItem item) {
 
			// Create a simple toast message
			Toast.makeText(ConfigApp.this, "Help Button", Toast.LENGTH_SHORT)
					.show();
 
			// Do something else
			return false;
		}
	};*/
 
	// Capture second menu button click
	OnMenuItemClickListener LikeButtonClickListener = new OnMenuItemClickListener() {
 
		public boolean onMenuItemClick(MenuItem item) {
			// Create a simple toast message
			Toast.makeText(ConfigApp.this, "Like Button", Toast.LENGTH_SHORT)
					.show();
 
			DialogoFrag f = new DialogoFrag();
			f.show(getSupportFragmentManager(),"like");
			f.setStittle(R.string.msg_facebook);
			f.setBexit(false);
			return false;
		}
	};
 
	// Capture third menu button click
	OnMenuItemClickListener ExitButtonClickListener = new OnMenuItemClickListener() {
 
		public boolean onMenuItemClick(MenuItem item) {
			
				f.show(getSupportFragmentManager(),"like");
				f.setStittle(R.string.msg_exit);
				f.setBexit(true);
			return false;
			
			
		}
	};

	@Override
	public void onDialogPositiveClick(DialogFragment dialog) {
		// TODO Auto-generated method stub
		
		if(f.isBexit()){
						startActivity(new Intent(getBaseContext(), ConfigApp.class)
			            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
							finish();
					
		}else{
			 Uri uri = Uri.parse("https://www.facebook.com/vengapps");
	  		 Intent intent = new Intent(Intent.ACTION_VIEW, uri);
	  		 startActivity(intent);
		}
	}


	@Override
	public void onDialogNegativeClick(DialogFragment dialog) {
		// TODO Auto-generated method stub
		
	}

}

class DialogoFrag extends DialogFragment{
	boolean Bexit;
	int itittle;
	
	public boolean isBexit() {
		return Bexit;
	}
	public void setBexit(boolean bexit) {
		Bexit = bexit;
	}
	public int getStittle() {
		return itittle;
	}
	public void setStittle(int Itittle) {
		itittle = Itittle;
	}
	public interface DialogListener{
		public void onDialogPositiveClick(DialogFragment dialog);
		public void onDialogNegativeClick(DialogFragment dialog);
	}
	DialogListener listener;
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			builder.setTitle(this.getStittle())
			//.setSingleChoiceItems(R.array.dialog_options,-1,null)
			.setNegativeButton(R.string.msg_no, new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
				
			})
			.setPositiveButton(R.string.msg_yes,new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					listener.onDialogPositiveClick(DialogoFrag.this);
				}
			});
			return builder.create();
			
			
	}
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		try{
			listener = (DialogListener)activity;
		}
		catch(ClassCastException e){}
	}
	
	
}


