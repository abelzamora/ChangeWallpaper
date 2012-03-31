package abel.wallpaper.change;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class ChangeWallpaperDaemon extends Service{
 
    private static final String TAG = "EjemploServicioBoot";
 
    private Timer timer;
 
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
 
    @Override
    public void onCreate(){
        super.onCreate();
        Log.d(TAG, "Servicio creado");
        timer = new Timer();
    }
 
    @Override
    public void onStart(final Intent intent, final int startId){
        super.onStart(intent, startId);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Log.i(TAG, "Se ejecuta la tarea");
            }
        }, 0, 15000);
    }
}

