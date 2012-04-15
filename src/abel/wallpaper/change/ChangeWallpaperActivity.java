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
import android.util.Log;

public class ChangeWallpaperActivity<E> extends Activity {
	private WallpaperManager miGestorDeFondos;
	private static final String TAG = "ChangeWallpaper";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);

		Thread welcomeThread = new Thread() {
			public void run() {

				try {
					synchronized (this) {
						// Clase que contiene todos los parámetros globales
						WallpaperGlobales<WallpaperDataName> cwg = new WallpaperGlobales<WallpaperDataName>();

						ArrayList<WallpaperDataName> alBm = cwg.checkFirstTime();
						if (alBm != null) {
							setAllBitMapSdCard(alBm);
							// Obtenemos mas fondos en la primera instalación
							setAllBitMapSdCard(cwg.getMasFondos());

							// Comprobamos la hora
							Integer sectionDay = cwg.checkHour();

							// Cambiamos el fondo de pantalla
							miGestorDeFondos = WallpaperManager.getInstance(getApplicationContext());
							@SuppressWarnings("unchecked")
							// Hay que comprobar que directorio ha seleccionado
							// por
							// defecto
							WallpaperDataConfig wdc = new WallpaperXml().getDataConfig();
							String directorio = wdc.getPaquete();
							ArrayList<WallpaperDataName> bmRuta = cwg.cambioWallpaper(sectionDay, directorio);
							// Comprobamos si tiene activado el cambiador
							// automático
							String activado = wdc.getActivado();
							if (activado.equals("S")) {
								// Está activado el cambiador automático
								Bitmap bm = bmRuta.get(0).getImagen();
								bm.prepareToDraw();
								bm.setDensity(BIND_AUTO_CREATE);
								miGestorDeFondos.setBitmap(bm);
							}
							// Almacenamos el fondo de pantalla en la sdCard
							setAllBitMapSdCard(bmRuta);
						}
					}

				} catch (Exception e) {
					// Recogemos todas las excepciones
					Log.d(TAG, e.getMessage());
				} finally {
					finish();
					// Por ultimo, comenzamos con la apliacion
					// Arrancamos el timer que comprueba cada 15 minutos si tiene que
					// cambiar el wallpaper
					Intent servicio = new Intent();
					servicio.setAction("abel.wallpaper.change.ChangeWallpaperServicio");
					startService(servicio);

					startActivity(new Intent(ChangeWallpaperActivity.this, WallpaperMenu.class));
					stop();
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