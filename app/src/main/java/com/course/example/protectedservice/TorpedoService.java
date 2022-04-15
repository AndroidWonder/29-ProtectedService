
package com.course.example.protectedservice;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

public class TorpedoService extends Service {
	
	MediaPlayer mp;
	Thread t;
	Intent serviceIntent;
	final String tag = "Permission";
	
	@Override
	public void onCreate() {
		super.onCreate();
        mp = MediaPlayer.create(this, R.raw.photon);
        Log.e(tag, "Start Service");
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        serviceIntent = intent;
   	    t = new Thread(background);
   	    t.start();
   	    return Service.START_STICKY;
    }
	
	@Override
	public void onDestroy() {
		super.onDestroy();
        mp.release();
	}

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    
    Runnable background = new Runnable() {
    	
    	public void run(){
    			
    	Log.e(tag, "Start thread");
    	
		try{
    			for(int i=0; i<3; i++){  
    				Log.v(tag, "Loop");   								
    				mp.seekTo(0);  
    				mp.start();
    				Thread.sleep(5000);
    			}
    			
    			//wait until player is finished before stopping service
    			while (mp.isPlaying()) {};   
    			Log.e(tag, "Stop Service");
    			stopService(serviceIntent);
    			
    		} catch(Exception e) {Log.e(tag, "Blowup");}
    	}
    }; 

}
