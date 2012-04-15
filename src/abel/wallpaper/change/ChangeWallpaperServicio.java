package abel.wallpaper.change;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import abel.wallpaper.global.WallpaperDataConfig;
import abel.wallpaper.global.WallpaperDataName;
import abel.wallpaper.global.WallpaperGlobales;
import abel.wallpaper.global.WallpaperXml;
import android.app.Service;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;

public class ChangeWallpaperServicio extends Service {

	private static final int SEGUNDOS = 15000;
	private static final String TAG = "ChangeWallpaper";
	private WallpaperManager miGestorDeFondos;

	private static Timer timer;

	// Patrón singleton para evitar que se inicien varios timers
	public static Timer getInstance() {
		if (timer == null) {
			Log.d(TAG, "Servicio creado");
			new Timer();
		} else {
			Log.d(TAG, "Servicio existente");
		}
		return timer;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		if (timer == null)
			timer = new Timer();
	}

	@Override
	public void onStart(final Intent intent, final int startId) {
		super.onStart(intent, startId);

		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				Log.i(TAG, "Comprobamos el cambio del wallpaper");
				try {
					//Comprobamos la hora para realizar el cambio
					WallpaperGlobales<WallpaperDataName> cwg = new WallpaperGlobales<WallpaperDataName>();
					
					// Comprobamos si tiene activado el cambiador automático
					WallpaperDataConfig wdc = new WallpaperXml().getDataConfig();
					String activado = wdc.getActivado();
					
					if (activado.equals("S")&& cwg.checkTime()) {
						//Tiene activa el indicador de cambio automático
						
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
						String directorio = wdc.getPaquete();
						ArrayList<WallpaperDataName> bmRuta = cwg.cambioWallpaper(sectionDay, directorio);

						// Está activado el cambiador automático
						Bitmap bm = bmRuta.get(0).getImagen();
						miGestorDeFondos.setBitmap(bm);

						// Almacenamos el fondo de pantalla en la sdCard
						setAllBitMapSdCard(bmRuta);
					}
				} catch (Exception e) {
					// Recogemos todas las excepciones
					Log.d(TAG, e.getMessage());
					this.cancel();
				}

			}
		}, 0, SEGUNDOS);
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
