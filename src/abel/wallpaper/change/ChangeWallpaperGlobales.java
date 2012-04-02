package abel.wallpaper.change;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ChangeWallpaperGlobales<E> {

	private static final String server = "http://dl.dropbox.com/u/3776369/changeWallpaper/201204/";
	private String URI_SD = "/sdcard/changeWallpaper/201204";

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
			// Amaneciendo
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
			// De dia
			time = 1;
			break;

		case 19:
		case 20:
		case 21:
		case 22:
			// Anocheciendo
			time = 2;
			break;

		default:
			// De noche
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
		if (!directory.exists()) {
			// No existe el directorio
			directory.mkdir();
		}
	}

	/*
	 * Cambiará el fondo de pantalla en función de la seccion del día
	 */
	@SuppressWarnings("unchecked")
	public ArrayList cambioWallpaper(Integer section) {
		// Creamos ArrayList para devolverlo
		ArrayList<E> bmRuta = new ArrayList<E>(2);
		

		// Primero debe comprobar que paquete ha seleccionado el usuario
		String nombreFichero = "prueba_".concat(String.valueOf(section)).concat(".png");
		String ruta = URI_SD.concat("/").concat(nombreFichero);
		//Añadimos la direccion al array, para guardarla en la sdcar
		bmRuta.add((E) ruta);

		// Con el paquete y la sección se cambia el fondo
		Bitmap bm = BitmapFactory.decodeFile(ruta);

		if (bm == null) {
			// Si la imagen no esta en la sdcard, la descargamos a la sdcard
			bm = getImageFromServer(nombreFichero);
		}
		else
		{
			//Indicamos que ya existe en la sdcard
			bmRuta.set(0, (E) "");
		}
		bmRuta.add((E) bm);
		

		return bmRuta;

	}

	/*
	 * Comprueba si la imagen está en la sd, sino la descarga del servidor
	 */
	private Bitmap getImageFromServer(String nombre) {
		Bitmap bm = null;
		String urlServer = server.concat(nombre);
		try {
			URL imageUrl = new URL(urlServer);
			HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
			conn.connect();
			bm = BitmapFactory.decodeStream(conn.getInputStream());

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return bm;

	}
	
	/*
	 * Listar directorios descargados
	 */
	public String[] listFile()
	{
		String uri_sdCard = "/sdcard/changeWallpaper";
		File mfile=new File(uri_sdCard);
		File[] list=mfile.listFiles();
		int tamList = mfile.listFiles().length;
		String[] strList = new String[tamList];
		
		for(int i=0;i<tamList;i++)
		{
		    if(list[i].isDirectory())
		    {
		    	strList[i] = list[i].getName();
		    }
		}
		
		return strList;
	}
	
	/*
	 * Carga las imágenes del directorio
	 */
	public Integer[] loadImages(String dir)
	{
		String uri_sdCard = "/sdcard/changeWallpaper".concat("/").concat(dir);
		File mfile=new File(uri_sdCard);
		File[] list=mfile.listFiles();
		int tamList = mfile.listFiles().length;
		Integer[] intList = new Integer[tamList];
		
		for(int i=0;i<tamList;i++)
		{
		    if(list[i].isDirectory())
		    {
		    	//intList[i] = list[i].getPath().
		    }
		}
	}

}
