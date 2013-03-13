package com.rushfusion.appstore.bate;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;

public class MainActivity extends Activity {

	private AppController mController ;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(mController ==null) {
        	mController = new AppController(this) ;
        	mController.start() ;
        }
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	// TODO Auto-generated method stub
    	if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {  
            showDialog();  
            return true;  
        }  
    	return super.onKeyDown(keyCode, event);  
    }

	private void showDialog() {
		AlertDialog.Builder builder = new Builder(this);  
        builder.setMessage("Are you sure you want to exit?");  
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {  
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
                dialog.dismiss();  
                android.os.Process.killProcess(android.os.Process.myPid());  
            }  
        });  
        builder.setNegativeButton("no", new DialogInterface.OnClickListener() {  
            @Override  
            public void onClick(DialogInterface dialog, int which) {  
                dialog.dismiss();  
            }  
        });  
        builder.create().show();  
	}
}
