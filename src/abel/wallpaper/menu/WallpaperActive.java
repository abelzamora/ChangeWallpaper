package abel.wallpaper.menu;

import java.io.IOException;

import abel.wallpaper.change.R;
import abel.wallpaper.global.WallpaperDataConfig;
import abel.wallpaper.global.WallpaperXml;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class WallpaperActive extends Activity {
	private int ESTADO = -1;
	private static final String TAG = "ChangeWallpaper";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_item);

		final CharSequence[] items = { "Activar", "Desactivar" };
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Activar/Desactivar");
		// Obtenemos la configuración
		WallpaperDataConfig wdc = new WallpaperXml().getDataConfig();
		String activado = wdc.getActivado();

		if (activado.equals("S")) {
			// Esta activado
			ESTADO = 0;
		} else {
			// Desactivado
			ESTADO = 1;
		}

		builder.setSingleChoiceItems(items, ESTADO, new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialogInterface, int item) {

				// Obtenemos la clase para escribir en el xml
				WallpaperXml wpx = new WallpaperXml();
				WallpaperDataConfig wdc = wpx.getDataConfig();

				try {

					if (item == 0) {
						// Activamos
						wpx.writeXml("S", wdc.getPaquete(), wdc.getFirsTime());
					} else {
						// Desactivamos
						wpx.writeXml("N", wdc.getPaquete(), wdc.getFirsTime());
					}
				} catch (IllegalArgumentException e) {
					// Recogemos todas las excepciones
					Log.d(TAG, e.getMessage());
				} catch (IllegalStateException e) {
					// Recogemos todas las excepciones
					Log.d(TAG, e.getMessage());
				} catch (IOException e) {
					// Recogemos todas las excepciones
					Log.d(TAG, e.getMessage());
				}

				Toast.makeText(getApplicationContext(), items[item], Toast.LENGTH_SHORT).show();
				startActivity(new Intent(WallpaperActive.this, WallpaperMenu.class));
				dialogInterface.dismiss();
			}
		});
		builder.create().show();
	}

	public void onBackPressed() {
		startActivity(new Intent(WallpaperActive.this, WallpaperMenu.class));
		return;
	}

}
