package abel.wallpaper.global;

import java.io.IOException;


public class WallpaperXml {

	// Leemos el xml
	public WallpaperDataConfig getDataConfig() {
		WallpaperParserXml wpx = new WallpaperParserXml();

		return wpx.readXml();
	}

	// Escribimos el xml
	public void writeXml(String activado, String paquete, String firstTime) throws IllegalArgumentException, IllegalStateException, IOException {
		WallpaperParserXml wpx = new WallpaperParserXml();
		
		WallpaperDataConfig dataConfig = new WallpaperDataConfig(activado, paquete, firstTime); 
		
		wpx.writeXml(dataConfig);
	}

}
