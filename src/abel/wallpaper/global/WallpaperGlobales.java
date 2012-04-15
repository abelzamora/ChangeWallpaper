package abel.wallpaper.global;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class WallpaperGlobales<E> {

	private static final String NOMBRE_FICHERO = "puesta";
	private static final int NOCHE = 3;
	private static final int ANOCHECIENDO = 2;
	private static final int DIA = 1;
	private static final int AMANECIENDO = 0;

	private static final String BARRA_DIRECTORIO = "/";
	private static final String PNG = ".png";
	private static final String SERVER = "http://dl.dropbox.com/u/3776369/changeWallpaper/";
	private String URI_SD = "/sdcard/changeWallpaper/".concat(NOMBRE_FICHERO);
	private static final String URI = "/sdcard/changeWallpaper";

	/*
	 * Se encargara de mostrar las notificaciones por pantalla
	 */
	public void showNotifications(Context context) {

	}

	/*
	 * Comprueba si es la hora para realizar el cambio
	 */
	public boolean checkTime() {
		Calendar c = Calendar.getInstance();
		int hora = c.getTime().getHours();

		switch (hora) {
			case 6:
			case 10:
			case 19:
				return true;
			default:
				return false;
		}
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

		Integer[] timingDay = { AMANECIENDO, DIA, ANOCHECIENDO, NOCHE };
		int time;
		switch (hora) {

		case 6:
		case 7:
		case 8:
		case 9:
			// Amaneciendo
			time = AMANECIENDO;
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
			time = DIA;
			break;

		case 19:
		case 20:
		case 21:
		case 22:
			// Anocheciendo
			time = ANOCHECIENDO;
			break;

		default:
			// De noche
			time = NOCHE;
			break;
		}

		return timingDay[time];

	}

	/*
	 * Comprobamos si es la primera vez que se inicia el programa
	 */
	public ArrayList<WallpaperDataName> checkFirstTime() throws IllegalArgumentException, IllegalStateException, IOException {
		// Primero comprobamos que el directorio exista
		// WallpaperXml wpx = new WallpaperXml();
		// wpx.writeXml("S", NOMBRE_FICHERO, "S");
		if (!checkDirectory(URI_SD)) {
			// El directorio no existía, creamos el xml
			WallpaperXml wpx = new WallpaperXml();
			wpx.writeXml("S", NOMBRE_FICHERO, "N");
			return allImages(NOMBRE_FICHERO, NOMBRE_FICHERO);
		} else {
			return null;
		}
	}

	/*
	 * Creamos otro paquete en la primera descarga...
	 */
	public ArrayList<WallpaperDataName> getMasFondos() throws IOException {
		if (!checkDirectory("/sdcard/changeWallpaper/puentes")) {
			return allImages("puentes", "puentes");
		} else {
			return null;
		}
	}

	/*
	 * Comprobar si existe el directorio, si no existe lo creamos
	 */
	private boolean checkDirectory(String uri) {
		File directory = new File(uri);
		// directory.delete();
		if (!directory.exists()) {
			// No existe el directorio princial
			directory.mkdir();
			return false;
		} else {
			// Directorio existe
			return true;
		}
	}

	/*
	 * Descargamos todas las imagenes del directorio
	 */
	private ArrayList<WallpaperDataName> allImages(String directorio, String nombreFicheros) throws IOException {
		ArrayList<WallpaperDataName> allImages = new ArrayList<WallpaperDataName>(4);
		WallpaperDataName wdn = null;

		for (int i = 0; i < 4; i++) {
			wdn = new WallpaperDataName();
			wdn.setUri(URI.concat("/").concat(directorio));
			wdn.setName(nombreFicheros.concat("_").concat(String.valueOf(i)).concat(PNG));
			wdn.setImagen(getImageFromServer(directorio, wdn.getName()));

			if (wdn.getImagen() != null)
				allImages.add(wdn);

		}

		return allImages;

	}

	/*
	 * Cambiará el fondo de pantalla en función de la seccion del día
	 */
	@SuppressWarnings("unchecked")
	public ArrayList<WallpaperDataName> cambioWallpaper(Integer sectionDay, String directorio) throws IOException {
		// Creamos ArrayList para devolverlo
		ArrayList<WallpaperDataName> bmRuta = new ArrayList<WallpaperDataName>(2);
		WallpaperDataName wdn = new WallpaperDataName();

		// Primero debe comprobar que paquete ha seleccionado el usuario
		String nombreFichero = NOMBRE_FICHERO.concat("_").concat(String.valueOf(sectionDay)).concat(PNG);
		String ruta = URI_SD.concat(BARRA_DIRECTORIO).concat(nombreFichero);
		// Añadimos la direccion al array, para guardarla en la sdcar
		wdn.setName(nombreFichero);
		wdn.setUri(URI_SD);

		// Con el paquete y la sección se cambia el fondo
		Bitmap bm = BitmapFactory.decodeFile(ruta);

		if (bm == null) {
			// Si la imagen no esta en la sdcard, la descargamos a la sdcard
			bm = getImageFromServer(directorio, nombreFichero);

		} else {
			// Indicamos que ya existe en la sdcard
			wdn.setUri("");
		}
		wdn.setImagen(bm);
		//
		bmRuta.add(wdn);

		return bmRuta;

	}

	/*
	 * Comprueba si la imagen está en la sd, sino la descarga del servidor
	 */
	private Bitmap getImageFromServer(String directorio, String nombre) {

		try {
			Bitmap bm = null;
			String urlServer = SERVER.concat(directorio).concat("/").concat(nombre);
			URL imageUrl = new URL(urlServer);
			HttpURLConnection conn = (HttpURLConnection) imageUrl.openConnection();
			conn.connect();

			bm = BitmapFactory.decodeStream(conn.getInputStream());

			return bm;
		} catch (IOException e) {
			// No existe la imagen en el servidor, devolvemos null
			return null;
		}

	}

	/*
	 * Listar directorios descargados
	 */
	public String[] listFile() {

		File mfile = new File(URI);
		File[] list = mfile.listFiles();
		int tamList = list.length;
		ArrayList<String> aux = new ArrayList<String>(tamList);

		for (int i = 0; i < tamList; i++) {
			if (list[i].isDirectory()) {
				aux.add(list[i].getName());
			}
		}

		String[] strList = new String[aux.size()];
		Iterator<String> ite = aux.iterator();
		int j = 0;
		while (ite.hasNext()) {
			strList[j] = ite.next().toString();
			j++;
		}

		return strList;
	}

	/*
	 * Carga las imágenes del directorio
	 */
	public List<String> readSDCard(String dir) {
		List<String> tFileList = new ArrayList<String>();

		// It have to be matched with the directory in SDCard
		String uri_dir = URI.concat(BARRA_DIRECTORIO).concat(dir);
		File f = new File(uri_dir);

		File[] files = f.listFiles();
		File file = null;
		String nameFile = "";

		for (int i = 0; i < files.length; i++) {
			file = files[i];
			// Obtenemos todas las imágenes de la carpeta
			nameFile = file.getName();
			if (nameFile.endsWith(PNG))
				tFileList.add(file.getPath());
		}

		return tFileList;
	}

}
