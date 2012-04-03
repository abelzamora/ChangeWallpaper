package abel.wallpaper.change;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import abel.wallpaper.global.WallpaperDataConfig;
import abel.wallpaper.global.WallpaperDataName;
import abel.wallpaper.global.WallpaperGlobales;
import abel.wallpaper.global.WallpaperXml;
import abel.wallpaper.menu.WallpaperMenu;
import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

public class ChangeWallpaperActivity<E> extends Activity {

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
						WallpaperGlobales<WallpaperDataName> cwg = new WallpaperGlobales<WallpaperDataName>();
						// Comprobamos que se ha creado el directorio con el
						// fichero de configuración

						ArrayList<WallpaperDataName> alBm = cwg.checkFirstTime();
						if (alBm != null) {
							setAllBitMapSdCard(alBm);
							// Obtenemos mas fondos en la primera instalación
							setAllBitMapSdCard(cwg.getMasFondos());
						}

						// Comprobamos la hora
						Integer sectionDay = cwg.checkHour();

						// Cambiamos el fondo de pantalla
						miGestorDeFondos = WallpaperManager.getInstance(getApplicationContext());
						@SuppressWarnings("unchecked")
						// Hay que comprobar que directorio ha seleccionado por
						// defecto
						WallpaperDataConfig wdc = new WallpaperXml().getDataConfig();
						String directorio = wdc.getPaquete();
						ArrayList<WallpaperDataName> bmRuta = cwg.cambioWallpaper(sectionDay, directorio);
						// Comprobamos si tiene activado el cambiador automático
						String activado = wdc.getActivado();
						if (activado.equals("S")) {
							//Está activado el cambiador automático
							Bitmap bm = bmRuta.get(0).getImagen();
							miGestorDeFondos.setBitmap(bm);
						}
						// Almacenamos el fondo de pantalla en la sdCard
						setAllBitMapSdCard(bmRuta);
						startActivity(new Intent(ChangeWallpaperActivity.this, WallpaperMenu.class));
						finish();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.getMessage();
						this.stop();
					}
				}
			}
		};
		welcomeThread.start();

	}

	/*
	 * La primeva vez necesitamos guardar todas las imagens en la sdcard
	 */
	private void setAllBitMapSdCard(ArrayList<WallpaperDataName> alBm) throws FileNotFoundException {

		OutputStream out = null;
		WallpaperDataName wdn;
		String ruta;
		Bitmap bm;
		String uri;

		Iterator<WallpaperDataName> itr = alBm.iterator();
		while (itr.hasNext()) {
			wdn = (WallpaperDataName) itr.next();
			uri = wdn.getUri();
			if (uri.length() > 0) {
				// La imagen no está almacenada en la sdcard
				bm = wdn.getImagen();
				ruta = wdn.getUri().concat("/").concat(wdn.getName());
				out = this.getContentResolver().openOutputStream(Uri.fromFile(new File(ruta)));
				bm.compress(Bitmap.CompressFormat.PNG, 100, out);
			}

		}
	}

}