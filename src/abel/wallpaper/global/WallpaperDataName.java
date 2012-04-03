package abel.wallpaper.global;

import android.graphics.Bitmap;

public class WallpaperDataName {

	private String name;
	private Bitmap imagen;
	private String uri;
	
	
	public WallpaperDataName(){}
	
	public WallpaperDataName(String name, Bitmap imagen) {
		super();
		this.name = name;
		this.imagen = imagen;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Bitmap getImagen() {
		return imagen;
	}
	public void setImagen(Bitmap imagen) {
		this.imagen = imagen;
	}
	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
	
	
}
