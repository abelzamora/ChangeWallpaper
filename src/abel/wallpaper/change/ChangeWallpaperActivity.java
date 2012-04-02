package abel.wallpaper.change;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.ArrayList;

import abel.wallpaper.menu.WallpaperMenu;
import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

public class ChangeWallpaperActivity<E> extends Activity {

	private static final int CODIGO_URI = 0;
	private static final int CODIGO_BITMAP = 1;
	private WallpaperManager miGestorDeFondos;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

		/** set time to splash out */
		final int welcomeScreenDisplay = 3000;
		/** create a thread to show splash up to splash time */
		Thread welcomeThread = new Thread() {

			int wait = 0;

			@Override
			public void run() {
				try {
					super.run();
					/**
					 * use while to get the splash time. Use sleep() to increase
					 * the wait variable for every 100L.
					 */
					/*
					 * while (wait < welcomeScreenDisplay) { sleep(100); wait +=
					 * 100; }
					 */
				} catch (Exception e) {
					System.out.println("EXc=" + e);
				} finally {
					/**
					 * Called after splash times up. Do some action after splash
					 * times up. Here we moved to another main activity class
					 */
					try {
						ChangeWallpaperGlobales<E> cwg = new ChangeWallpaperGlobales<E>();
						// Comprobamos que se ha creado el directorio con el
						// fichero de configuración

						cwg.checkDirectory();

						// Comprobamos la hora
						Integer sectionDay = cwg.checkHour();

						// Cambiamos el fondo de pantalla

						miGestorDeFondos = WallpaperManager.getInstance(getApplicationContext());
						@SuppressWarnings("unchecked")
						ArrayList<E> bmRuta = cwg.cambioWallpaper(sectionDay);
						Bitmap bm = (Bitmap) bmRuta.get(CODIGO_BITMAP);
						miGestorDeFondos.setBitmap(bm);
						setBitMapSdCard(bmRuta);
						startActivity(new Intent(ChangeWallpaperActivity.this, WallpaperMenu.class));
						finish();
					} catch (Exception e) {
						// TODO Auto-generated catch block
					}
				}
			}
		};
		welcomeThread.start();

	}

	/*
	 * Necesitamos un Activity, tenemos que guardar el bitmap en la sd
	 */
	private <E> void setBitMapSdCard(ArrayList<E> bmRuta) {
		Bitmap bm = (Bitmap) bmRuta.get(CODIGO_BITMAP);
		String ruta = (String) bmRuta.get(CODIGO_URI);
		try {
			if (ruta.length()>0) {
				OutputStream out = this.getContentResolver().openOutputStream(Uri.fromFile(new File(ruta)));
				bm.compress(Bitmap.CompressFormat.PNG, 100, out);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}