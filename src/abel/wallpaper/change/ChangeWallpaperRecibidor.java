package abel.wallpaper.change;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ChangeWallpaperRecibidor extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent servicio = new Intent();
        servicio.setAction("abel.wallpaper.change.ChangeWallpaperServicio");
        context.startService(servicio);
    }
}