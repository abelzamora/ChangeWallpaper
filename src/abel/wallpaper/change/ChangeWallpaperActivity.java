package abel.wallpaper.change;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ChangeWallpaperActivity extends BroadcastReceiver {
    /** Called when the activity is first created. */
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent servicio = new Intent();
        servicio.setAction("foo.bar.Servicio");
        context.startService(servicio);
    }
}