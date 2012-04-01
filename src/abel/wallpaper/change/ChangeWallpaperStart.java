package abel.wallpaper.change;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ChangeWallpaperStart extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {

		// Accion a realizar al inicir el móvil
		// aqui llama a la clase que comienza la app
		Intent i = new Intent(context, ChangeWallpaperDaemon.class);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(i);
	}

}