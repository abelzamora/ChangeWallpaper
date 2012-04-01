package abel.wallpaper.change;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ChangeWallpaperGlobales{

	private WallpaperManager miGestorDeFondos;
	
	private static final String URI_SD = "/sdcard/changeWallpaper";

	/*
	 * Se encargara de mostrar las notificaciones por pantalla
	 */
	public void showNotifications(Context context) {

	}

	/*
	 * Comprueba la hora del teléfono móvil
	 */
	public Integer checkHour() {
		Calendar c = Calendar.getInstance();

		int hora = c.getTime().getHours();

		// Hay 4 cambios de wallpaper
		// Amaneciendo - 0
		// De dia - 1
		// Anocheciendo - 2
		// De noche - 3

		Integer[] timingDay = { 0, 1, 2, 3 };
		int time;
		switch (hora) {
			
			case 6:
			case 7:
			case 8:
			case 9:
				//Amaneciendo
				time = 0;
				break;
			
			case 10:
			case 11:
			case 12:
			case 13:
			case 14:
			case 15:
			case 16:
			case 17:
			case 18:
				//De dia
				time = 1;
				break;
			
			case 19:
			case 20:
			case 21:
			case 22:
				//Anocheciendo
				time = 2;
				break;
				
			default:
				//De noche
				time = 3;
				break;
		}
		
		return timingDay[time];

	}

	/*
	 * Comprobar si existe el directorio, si no existe lo creamos
	 */
	public void checkDirectory() {
		File directory = new File(URI_SD);
		if(!directory.exists())
		{
			//No existe el directorio
			directory.mkdir();
		}
	}
	
	/*
	 * Cambiará el fondo de pantalla en función de la seccion del día
	 */
	public Bitmap cambioWallpaper(Integer section)
	{
		//Primero debe comprobar que paquete ha seleccionado el usuario
		String ruta = URI_SD+"/prueba_"+String.valueOf(section)+".png";
		
		//Con el paquete y la sección se cambia el fondo
		Bitmap bm = BitmapFactory.decodeFile(ruta);
		
		return bm;
		
	}

}
